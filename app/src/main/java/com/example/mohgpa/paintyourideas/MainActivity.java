package com.example.mohgpa.paintyourideas;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {
    Spinner sizeSpinnerPen;//to set size of pen
    Spinner colorSpinner;//to set color of pen
    private MyCanvasView myCanvas;//this is where drawing will take place
    Spinner eraserSpinner;//to set eraser size

    private int penSize;//stores pen's current size
    private int eraserSize;//eraser's current color
    private int currentColor;//pen's current color


    private boolean isOptionsVisible=false;
    private boolean isPenSelected=true;

    AlertDialog.Builder builder;
    String titleInstructions="Instructions";
    String msgInstructions="Clicking on the \"Tools\" button will show you the pen and eraser options.\n" +
            "\n"+"After selecting Pen button,drop down menus will appear you can choose pen's tip size and color from them.\n" +
            "Similarly for eraser you can change eraser size. \n" +
            "Clear all button will reset the complete canvas.\n\n" +
            "Clicking on tools will hide options .";
    String titleAbout="About";
    String msgAbout="Thanks for using \"Paint Your Ideas\".\n" +
            "\n" +"Hope you enjoyed the app"+"\n"+
            "I am open for feedback ,for your feedback please mail me at \"mohitkumarbti@gamil.com\"" +
            "\n\n Current version: \n1.0.1\n \n I am continously working to make this a more wonder full app.\n" +
            "Stay tuned to my repo for future updates as this is not currently published";



    String[] sizeNums={"1","2","3","4","5","6","7","8","9"};
    String [] colorsRange={ "Black","Red","Orange","SkyBlue","LightGreen","DarkGreen","Yellow","Pink","DarkBlue"};


    Button PenButton;
    Button EraserButton;
    Button ClearAllButton;
    Button ToolsButton;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        builder= new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(titleInstructions).setMessage(msgInstructions);
        builder.setPositiveButton(R.string.ok,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //just simply dismiss the dialog box
                //its fun doing android
            }
        });

        dialog=builder.create();
        dialog.show();

        PenButton=(Button)findViewById(R.id.penButton);
        EraserButton=(Button)findViewById(R.id.eraserButton);
        ClearAllButton=(Button)findViewById(R.id.clearAll);
        ToolsButton=(Button)findViewById(R.id.tools);

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
                myCanvas.setPenSize((float)eraserSize*5);
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



    }//onCreate method finishes here


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }
    //menu is not able to be created
    //just implementation left

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.toolsMenuButton:
                toolOptions();
                return true;
            case R.id.ins:
                dialog.show();
                return true;
            case R.id.about:
                builder.setTitle(titleAbout).setMessage(msgAbout);
                builder.setPositiveButton(R.string.ok,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //just simply dismiss the dialog box
                        //its fun doing android
                    }
                });
                AlertDialog dialog2=builder.create();
                dialog2.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public void toolOptions(){
        if (isOptionsVisible){
            PenButton.setVisibility(View.GONE);
            sizeSpinnerPen.setVisibility(View.GONE);
            colorSpinner.setVisibility(View.GONE);
            EraserButton.setVisibility(View.GONE);
            eraserSpinner.setVisibility(View.GONE);
            ClearAllButton.setVisibility(View.GONE);
            isOptionsVisible=false;
            ToolsButton.setVisibility(View.GONE);
        }

        else {
            PenButton.setVisibility(View.VISIBLE);
            EraserButton.setVisibility(View.VISIBLE);
            ClearAllButton.setVisibility(View.VISIBLE);
            isOptionsVisible=true;
            ToolsButton.setVisibility(View.VISIBLE);

            if(isPenSelected){
                sizeSpinnerPen.setVisibility(View.VISIBLE);
                colorSpinner.setVisibility(View.VISIBLE);
            }
            else eraserSpinner.setVisibility(View.VISIBLE);

        }

    }

    public void toolOptions(View view){
      toolOptions();
    }
}
