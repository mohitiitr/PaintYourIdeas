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

import java.util.ArrayList;

public class MyCanvasView extends View {
     public int width;
     public int height;
     protected Bitmap mbitmap;
     private Canvas mcanvas;
     private Path mpath;
     Context context;
     private Paint mpaint;
     private float mX,mY;
     private static final float TOLERANCE=5;
     private ArrayList<PathCollector> paths=new ArrayList<>();
     private Paint mbtmppaint=new Paint(Paint.DITHER_FLAG);



     public MyCanvasView(Context c, AttributeSet attrs){
         super(c,attrs);
         context=c;
         mpath=new Path();

         mpaint = new Paint();
         mpaint.setAntiAlias(true);
         mpaint.setColor(Color.BLACK);
         mpaint.setStyle(Paint.Style.STROKE);
         mpaint.setStrokeJoin(Paint.Join.ROUND);
         mpaint.setStrokeWidth(4f);
     }
     //constructor completed

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        height=h;
        mbitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mbitmap.eraseColor(getResources().getColor(R.color.White));
//        mbitmap.setHasAlpha(true);
        mcanvas = new Canvas(mbitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.drawPath(mpath, mpaint);

        canvas.save();

        for (PathCollector pc :paths){
            mpaint.setColor(pc.colorOfPath);
            mpaint.setStrokeWidth(pc.strokeWidthOfPath);
            mcanvas.drawPath(pc.path,mpaint);
        }
        canvas.drawBitmap(mbitmap,0,0,mbtmppaint);
        canvas.restore();

    }

    private void startTouch(float x, float y) {
        mpath=new Path();

        PathCollector pc= new PathCollector(mpaint.getColor(),mpaint.getStrokeWidth(),mpath);
        paths.add(pc);

        mpath.moveTo(x, y);
        mX = x;
        mY = y;
      }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mpath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
         }
    }

    public void clearCanvas(){
        for(PathCollector pc : paths){
            pc.path.reset();
        }
        paths=new ArrayList<>();
        invalidate();
        mbitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mbitmap.eraseColor(getResources().getColor(R.color.White));
//        mbitmap.setHasAlpha(true);
        mcanvas = new Canvas(mbitmap);
    }

    private void upTouch(){
         mpath.lineTo(mX,mY);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x=event.getX();
        float y=event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startTouch(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x,y);
                invalidate();
                break;
        }
        return true;
    }


    public void setPenColor(int color){
         mpaint.setColor(getResources().getColor(color));
    }
    public void setPenSize(float size){
         mpaint.setStrokeWidth(size);
    }


}
