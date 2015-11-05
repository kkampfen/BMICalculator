package com.bananaroo.bmicalculator;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    TextView historyLabel;
    ListView historyListView;
    Button returnButton;
    private static final String FILENAME = "bmiFile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        View backgroundImage = findViewById(R.id.background);
        Drawable background = backgroundImage.getBackground();
        background.setAlpha(220);

        historyLabel = (TextView) findViewById(R.id.historyLabel);
        historyListView = (ListView) findViewById(R.id.historyList);
        returnButton = (Button) findViewById(R.id.returnButton);

        showHistory();
    }

    private void showHistory() {
        ArrayAdapter<String> myAdapter;
        List<String> historyList = new ArrayList<String>();

        try {
            InputStream instream = openFileInput(FILENAME);
            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line = null;

                while ((line = buffreader.readLine()) != null) {
                   historyList.add(line);
                }
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
            historyLabel.setText("No history file found");
            historyListView.setVisibility(View.INVISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.reverse(historyList);
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, historyList);
        historyListView.setAdapter(myAdapter);
    }

    public void returnToMain(View view) {
        Intent returnToMain = new Intent(HistoryActivity.this, MainActivity.class);
        returnToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(returnToMain);
        finish();
    }
}
