package com.example.mohgpa.paintyourideas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MyCanvasView myCanvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCanvas=(MyCanvasView) findViewById(R.id.canvastodraw);
    }

    public void clearTheCanvas(View view){
        myCanvas.clearCanvas();
    }
}
