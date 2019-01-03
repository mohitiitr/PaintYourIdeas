package com.example.mohgpa.paintyourideas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter {

    Context context;
    String[] textForView;
    public MyAdapter(Context context,int textViewResourceId,String[] elements){
        super(context,textViewResourceId,elements);
        this.context=context;
        textForView=elements;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater= (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View layout =inflater.inflate(R.layout.my_custom_layout,parent,false);

        TextView colors=(TextView)layout.findViewById(R.id.txt);
        colors.setText(textForView[position]);
        int currentColor;
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

        colors.setTextColor(getContext().getResources().getColor(currentColor));

        return layout;

    }


    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}
