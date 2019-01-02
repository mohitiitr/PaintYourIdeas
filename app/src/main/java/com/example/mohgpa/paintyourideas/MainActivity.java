package com.example.mohgpa.paintyourideas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    Spinner sizeSpinnerPen;
    Spinner colorSpinner;
    private MyCanvasView myCanvas;

    private int penSize;
    private int eraserSize;

    String[] sizeNums={"1","2","3","4","5","6","7","8","9"};
    String [] colorsRange={"Red","Orange","SkyBlue", "DarkBlue","Black","LightGreen","DarkGreen","Yellow","Pink"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCanvas=(MyCanvasView) findViewById(R.id.canvastodraw);


        sizeSpinnerPen=(Spinner)findViewById(R.id.sizeSpinner);
        colorSpinner=(Spinner)findViewById(R.id.colorSpinner);

        ArrayAdapter penSizeAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, sizeNums);
        penSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter penColorAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, colorsRange);
        penColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sizeSpinnerPen.setAdapter(penSizeAdapter);
        colorSpinner.setAdapter(penColorAdapter);

        sizeSpinnerPen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                penSize=position+1;
                myCanvas.setPenSize((float)penSize);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //nothing for now
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }





    public void clearTheCanvas(View view){
        myCanvas.clearCanvas();
    }

    public void setPen(View view){
        //setting the pen doing later
    }

    public void setEraser(View view){
        //setting eraser i will do later after the completion of spinner object
    }
}
