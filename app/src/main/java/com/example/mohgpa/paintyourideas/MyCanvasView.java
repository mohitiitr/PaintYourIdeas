package com.example.mohgpa.paintyourideas;

import android.view.View;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyCanvasView extends View {
     public int width;
     public int height;
     private Bitmap mbitmap;
     private Canvas mcanvas;
     private Path mpath;
     Context context;
     private Paint mpaint;
     private float mX,mY;
     private static final float TOLERANCE=5;


     protected MyCanvasView(){
         //the default constructor
     }
     public MyCanvasView(Context c, AttributeSet attrs){
         super(c,attrs);
         context=c;
         mpath=new Path();

         mpaint = new Paint();
         mpaint.setAntiAlias(true);
         mpaint.setColor(Color.BLACK);
         mpaint.setStyle(Paint.Style.STROKE);
         mpaint.
     }

}


















}
