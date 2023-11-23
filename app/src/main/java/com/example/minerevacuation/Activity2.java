package com.example.minerevacuation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

import com.chaquo.python.Python;
import com.chaquo.python.PyObject;
import com.chaquo.python.android.AndroidPlatform;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        PyObject pyObject1 = py.getModule("testing_out");
        PyObject result = pyObject1.callAttr("calculate_values");

        TextView tvMeanScore = findViewById(R.id.tvMeanCVScore);
        TextView tvStdScore = findViewById(R.id.tvCVScoreStdDev);

        tvMeanScore.setText(result.asList().get(0).toString());
        tvStdScore.setText(result.asList().get(1).toString());


        Button backButton = findViewById(R.id.buttonBack1);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity2.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}