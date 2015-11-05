package com.bananaroo.bmicalculator;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText height, weight;
    TextView heightLabel, weightLabel, resultsLabel, savedLabel;

    Button calcButton, saveButton, historyButton;

    private static final String FILENAME = "bmiFile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View backgroundImage = findViewById(R.id.background);
        Drawable background = backgroundImage.getBackground();
        background.setAlpha(220);

        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);

        heightLabel = (TextView) findViewById(R.id.heightLabel);
        weightLabel = (TextView) findViewById(R.id.weightLabel);
        resultsLabel = (TextView) findViewById(R.id.resultsLabel);

        calcButton = (Button) findViewById(R.id.calcButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        savedLabel = (TextView) findViewById(R.id.savedLabel);
        savedLabel.setVisibility(View.INVISIBLE);
        historyButton = (Button) findViewById(R.id.historyButton);
    }

    public String calculateBMI(View view) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        float weightInput, heightInput, bmi;

        heightInput = Float.valueOf(String.valueOf(height.getText()));
        weightInput = Float.valueOf(String.valueOf(weight.getText()));

        bmi = (weightInput * 703) / (heightInput * heightInput);
        String bmiString = String.format("%.02f", bmi);
        resultsLabel.setText("Your BMI is " + bmiString);

        savedLabel.setVisibility(view.INVISIBLE);
        saveButton.setVisibility(view.VISIBLE);

        return bmiString;
    }

    public void save(View view) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        String formattedDate = df.format(c.getTime());
        String output = formattedDate + "     " + String.valueOf(weight.getText()) + " lbs     " + calculateBMI(view);

        try {
            OutputStreamWriter writer = new OutputStreamWriter(openFileOutput(FILENAME, Context.MODE_APPEND));
            writer.write(output);
            writer.write('\n');
            writer.close();
        } catch (IOException e) {
            Log.e("bananaroo.BMICalculator", "Write to file failed");
        }

        saveButton.setVisibility(view.INVISIBLE);
        savedLabel.setVisibility(view.VISIBLE);
    }

    public void showHistory(View view) {
        Intent showHistory = new Intent(MainActivity.this, HistoryActivity.class);
        showHistory.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(showHistory);
    }
}
