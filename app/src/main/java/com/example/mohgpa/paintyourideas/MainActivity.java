package com.example.mohgpa.paintyourideas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Spinner sizeSpinnerPen;
    Spinner colorSpinner;
    private MyCanvasView myCanvas;
    Spinner eraserSpinner;

    private int penSize;
    private int eraserSize;
    private int currentColor;

    private boolean isPenPressed=true;
    private boolean isEraserPressed=false;

    private int countBackPressed=0;
    private boolean isOptionsVisible=false;

    String[] sizeNums={"1","2","3","4","5","6","7","8","9"};
    String [] colorsRange={ "Black","Red","Orange","SkyBlue","LightGreen","DarkGreen","Yellow","Pink","DarkBlue"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toast toast=Toast.makeText(MainActivity.this,"Please select PEN Button before changing pen attributes \n:)",Toast.LENGTH_SHORT);
        final Toast toast2=Toast.makeText(MainActivity.this,"Please select ERASER Button before changing ERASER size \n:)",Toast.LENGTH_SHORT);


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

                if(isPenPressed){
                penSize=position+1;
                myCanvas.setPenSize((float)penSize*4);
            }
            else {
                     toast.show();
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (isPenPressed){penSize=16;
                myCanvas.setPenSize((float)penSize);
            }
            else toast.show();
            }
        });

        eraserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if(isEraserPressed){  eraserSize=position+1;
                myCanvas.setPenSize((float)eraserSize*5);
            }
              else if (countBackPressed>0)toast2.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if(isEraserPressed){eraserSize=16;
                myCanvas.setPenSize((float)eraserSize);
            }

            }
        });


        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(isPenPressed){
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

                else toast.show();


            }

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
        myCanvas.setPenSize(penSize*4);
        myCanvas.setPenColor(currentColor);
        isPenPressed=true;
        isEraserPressed=false;
    }

    public void setEraser(View view){
        //setting eraser i will do later after the completion of spinner object
        myCanvas.setPenSize(eraserSize*5);
        myCanvas.setPenColor(R.color.White);
        isPenPressed=false;
        isEraserPressed=true;
        countBackPressed++;
    }

    public void toolOptions(View view){

    }
}
