package com.example.mohgpa.paintyourideas;

import android.graphics.Path;

public class PathCollector {

    int colorOfPath;
    float strokeWidthOfPath;
    Path path;

    public PathCollector(int c,float s,Path p){
        colorOfPath=c;
        strokeWidthOfPath=s;
        path=p;
    }

}
