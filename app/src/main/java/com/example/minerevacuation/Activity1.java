package com.example.minerevacuation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Context;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Activity1 extends AppCompatActivity {

    private TableLayout tableLayoutCSV;
    private List<String[]> csvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        tableLayoutCSV = findViewById(R.id.tableLayoutCSV);
        csvData = readCSV(this);

        populateTable(csvData);

        Button backButton = findViewById(R.id.buttonBack1);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity1.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void populateTable(List<String[]> data){
        for (String[] row : data){
            TableRow tableRow = new TableRow(this);
            for (String cell : row){
                TextView textView = new TextView(this);
                textView.setText(cell);
                textView.setPadding(5, 5, 5, 5);
                tableRow.addView(textView);
            }
            tableLayoutCSV.addView(tableRow);
        }
    }

    private List<String[]> readCSV(Context context){

        List<String[]> resultList = new ArrayList<>();
        try{
            InputStream is = context.getAssets().open("sample.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            int rowCount = 0;
            while((line = reader.readLine()) != null && rowCount<6){
                String[] row = line.split(",");
                resultList.add(row);
                rowCount++;
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return resultList;
    }

}
