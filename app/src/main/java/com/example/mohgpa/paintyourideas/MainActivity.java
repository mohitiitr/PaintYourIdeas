package com.example.mohgpa.paintyourideas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.time.chrono.Era;

public class MainActivity extends AppCompatActivity {
    Spinner sizeSpinnerPen;
    Spinner colorSpinner;
    private MyCanvasView myCanvas;
    Spinner eraserSpinner;

    private int penSize;
    private int eraserSize;
    private int currentColor;

    private boolean isOptionsVisible=false;
    private boolean isPenSelected=true;


    String[] sizeNums={"1","2","3","4","5","6","7","8","9"};
    String [] colorsRange={ "Black","Red","Orange","SkyBlue","LightGreen","DarkGreen","Yellow","Pink","DarkBlue"};


    Button PenButton;
    Button EraserButton;
    Button ClearAllButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PenButton=(Button)findViewById(R.id.penButton);
        EraserButton=(Button)findViewById(R.id.eraserButton);
        ClearAllButton=(Button)findViewById(R.id.clearAll);

        myCanvas=(MyCanvasView) findViewById(R.id.canvastodraw);


        sizeSpinnerPen=(Spinner)findViewById(R.id.sizeSpinner);
        colorSpinner=(Spinner)findViewById(R.id.colorSpinner);
        eraserSpinner=(Spinner)findViewById(R.id.sizeSpinnerForEraser);

        final ArrayAdapter penSizeAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, sizeNums);
        penSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        sizeSpinnerPen.setAdapter(penSizeAdapter);
        eraserSpinner.setAdapter(penSizeAdapter);
        colorSpinner.setAdapter( new MyAdapter(MainActivity.this,R.layout.my_custom_layout,colorsRange));

        sizeSpinnerPen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                penSize=position+1;
                myCanvas.setPenSize((float)penSize*4);

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                penSize=16;
                myCanvas.setPenSize((float)penSize);

            }
        });



        eraserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eraserSize=position+1;
                myCanvas.setPenSize(position*5);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                eraserSize=16;
                myCanvas.setPenSize((float)eraserSize);
            }
        });


        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        currentColor=(int)R.color.cl4;
                        break;
                    case 1:
                        currentColor=(int)R.color.cl1;
                        break;
                    case 2:
                        currentColor=(int)R.color.cl2;
                        break;
                    case 3:
                        currentColor=(int)R.color.cl3;
                        break;
                    case 4:
                        currentColor=(int)R.color.cl6;
                        break;
                    case 5:
                        currentColor=(int)R.color.cl5;
                        break;
                    case 6:
                        currentColor=(int)R.color.cl7;
                        break;
                    case 7:
                        currentColor=(int)R.color.cl8;
                        break;
                    case 8:
                        currentColor=(int)R.color.cl9;
                        break;
                    default:
                        currentColor=(int)R.color.cl4;
                }
                myCanvas.setPenColor(currentColor);}


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //currentColor=0x000000;
                //myCanvas.setPenColor(currentColor);
            }
        });



    }





    public void clearTheCanvas(View view){
        myCanvas.clearCanvas();
    }

    public void setPen(View view){
        //setting the pen doing later
        isPenSelected=true;
        myCanvas.setPenSize(penSize*4);
        myCanvas.setPenColor(currentColor);
        sizeSpinnerPen.setVisibility(View.VISIBLE);
        colorSpinner.setVisibility(View.VISIBLE);
        eraserSpinner.setVisibility(View.GONE);
    }

    public void setEraser(View view){
        //setting eraser i will do later after the completion of spinner object
        myCanvas.setPenSize(eraserSize*5);
        myCanvas.setPenColor(R.color.White);
        eraserSpinner.setVisibility(View.VISIBLE);
        sizeSpinnerPen.setVisibility(View.GONE);
        colorSpinner.setVisibility(View.GONE);
        isPenSelected=false;

    }

    public void toolOptions(View view){
        if (isOptionsVisible){
            PenButton.setVisibility(View.GONE);
            sizeSpinnerPen.setVisibility(View.GONE);
            colorSpinner.setVisibility(View.GONE);
            EraserButton.setVisibility(View.GONE);
            eraserSpinner.setVisibility(View.GONE);
            ClearAllButton.setVisibility(View.GONE);
            isOptionsVisible=false;
        }

        else {
            PenButton.setVisibility(View.VISIBLE);
            EraserButton.setVisibility(View.VISIBLE);
            ClearAllButton.setVisibility(View.VISIBLE);
            isOptionsVisible=true;

            if(isPenSelected){
                sizeSpinnerPen.setVisibility(View.VISIBLE);
                colorSpinner.setVisibility(View.VISIBLE);
            }
            else eraserSpinner.setVisibility(View.VISIBLE);

        }

    }
}
