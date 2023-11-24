package com.example.minerevacuation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.chaquo.python.Python;
import com.chaquo.python.PyObject;
import com.chaquo.python.PyException;
import com.chaquo.python.android.AndroidPlatform;

import java.nio.channels.InterruptedByTimeoutException;


public class Activity3 extends AppCompatActivity {

    private EditText inputA, inputB;
    private TextView resultView;

    protected void onCreate(Bundle savedInstance){

        super.onCreate(savedInstance);
        setContentView(R.layout.activity_3);

        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        inputA = findViewById(R.id.inputA);
        inputB = findViewById(R.id.inputB);
        resultView = findViewById(R.id.resultView);
        Button calculateButton = findViewById(R.id.calculateButton);
        Button backButton = findViewById(R.id.backButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int a = Integer.parseInt(inputA.getText().toString());
                    int b = Integer.parseInt(inputB.getText().toString());

                    PyObject pyObject = Python.getInstance().getModule("add").callAttr("add",a,b);
                    int result = pyObject.toInt();

                    resultView.setText(String.valueOf(result));

                } catch (PyException e){
                    e.printStackTrace();
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity3.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
