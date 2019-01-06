package com.example.mohgpa.paintyourideas;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_STORAGE = 112;//for getting storage permission ,,this acts a key code XD

    SharedPreferences sharedpreferences;
    public static final String myPrefsName="mySharedPrefs";//my shared preferences file name
    public static final String numberImage="numberKey";//stores the index at which the next idea is to be saved
    public static final String alwaysInstruction="dialogKey";//saves whether user wants to receive instructions every time the app starts
    public static final String alwaysInstructionDialog="dialogSettingKey";//helps in one time display of instructions always dialog

    Spinner sizeSpinnerPen;//to set size of pen
    Spinner colorSpinner;//to set color of pen
    private MyCanvasView myCanvas;//this is where drawing will take place
    Spinner eraserSpinner;//to set eraser size

    private int penSize;//stores pen's current size
    private int eraserSize;//eraser's current color
    private int currentColor;//pen's current color


    private boolean isOptionsVisible = false;//helps in full view mode
    private boolean isPenSelected = true;//helps to decide pens property

    File dir ;//directory where files will be saved

    AlertDialog.Builder builder;//this creates dialog

    //strings variable names are self explaining their purpose
    String titleInstructions = "Instructions";
    String msgInstructions = "Clicking on the \"Tools\" button will show you the pen and eraser options.\n" +
            "\n" + "After selecting Pen button,drop down menus will appear you can choose pen's tip size and color from them.\n" +
            "Similarly for eraser you can change eraser size. \n" +
            "Clear all button will reset the complete canvas.\n\n" +
            "Clicking on tools will hide options ." +
            "\n Clicking on the save will save the image,also you can change the name of image.\n\n:)";



    String titleAbout = "About";
    String msgAbout = "Thanks for using \"Paint Your Ideas\".\n" +
            "\n" + "Hope you enjoyed the app" + "\n" +
            "I am open for feedback ,for your feedback please mail me at \"mohitkumarbti@gamil.com\"" +
            "\n\n Current version: \n1.0.1\n \n I am continously working to make this a more wonder full app.\n" +
            "Stay tuned to my repo for future updates as this is not currently published";

    String titleSettingInstruction="Pop Up Instructions";
    String msgSettingInstructions="Do you want to recieve instructions every time when the app is launched ,press YES otherwise NO :)";


    String[] sizeNums = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};//pen size array
    String[] colorsRange = {"Black", "Red", "Orange", "SkyBlue", "LightGreen", "DarkGreen", "Yellow", "Pink", "DarkBlue"};//array that stores colors name

    //buttons in activity_main.xml
    Button PenButton;
    Button EraserButton;
    Button ClearAllButton;
    Button ToolsButton;
    ImageButton SaveButton;

    //dialogs used more than once
    AlertDialog dialog;
    AlertDialog dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences=getSharedPreferences(myPrefsName,MODE_PRIVATE);//set's the shared preferences file

        SharedPreferences.Editor editor=sharedpreferences.edit();//editor
        if (sharedpreferences.getInt(numberImage,-1)==-1){
            editor.putInt(numberImage,0);
            editor.apply();
        }//this statement helps to initiallize the numberImage


        //this demands for the permission to create files in the app so as to save the bitmaps into the storage of my mi note 4
        boolean hasPermission = (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }

        builder = new AlertDialog.Builder(MainActivity.this);//this initializes the builder

        //the dialog one is being setup below
        builder.setTitle(titleInstructions).setMessage(msgInstructions);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //just simply dismiss the dialog box
                //its fun doing android
            }
        });

        dialog = builder.create();

        //this is to debug the case when the about is pressed after the pop up setting menu item
        //if this is not done then about contains an unusual NO button ,also I was shocked seeing that but it is
        //now corrected after this builder setup
        builder.setTitle(titleAbout).setMessage(msgAbout);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //just simply dismiss the dialog box
                //its fun doing android
            }
        });
        dialog2 = builder.create();


        //shows instructions if user wants to see instructions every time he opens the app
        if (sharedpreferences.getBoolean(alwaysInstruction,true)){
            dialog.show();
        }

        //asks user for the instructions settings
        if (sharedpreferences.getBoolean(alwaysInstructionDialog,true)){
            showAlwaysInstructionDialogBox();
        }




        // i have removed the casting as android studio showed this as redundant

        PenButton =  findViewById(R.id.penButton);
        EraserButton =  findViewById(R.id.eraserButton);
        ClearAllButton = findViewById(R.id.clearAll);
        ToolsButton = findViewById(R.id.tools);
        SaveButton =  findViewById(R.id.save);

        myCanvas =  findViewById(R.id.canvastodraw);


        sizeSpinnerPen =  findViewById(R.id.sizeSpinner);
        colorSpinner =  findViewById(R.id.colorSpinner);
        eraserSpinner =  findViewById(R.id.sizeSpinnerForEraser);


        final ArrayAdapter penSizeAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, sizeNums);
        penSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        sizeSpinnerPen.setAdapter(penSizeAdapter);
        eraserSpinner.setAdapter(penSizeAdapter);
        colorSpinner.setAdapter(new MyAdapter(MainActivity.this, R.layout.my_custom_layout, colorsRange));

        sizeSpinnerPen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                penSize = position + 1;
                myCanvas.setPenSize((float) penSize * 4);
                //this set's the pen size as per user selection
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                penSize = 16;
                myCanvas.setPenSize((float) penSize);
                //some sort of default case is this

            }
        });


        eraserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eraserSize = position + 1;
                //stores eraser size in a variable to access the size any time needed
                myCanvas.setPenSize((float) eraserSize * 5);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                eraserSize = 16;
                myCanvas.setPenSize((float) eraserSize);
            }
        });


        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        currentColor =  R.color.cl4;
                        break;
                    case 1:
                        currentColor =  R.color.cl1;
                        break;
                    case 2:
                        currentColor =  R.color.cl2;
                        break;
                    case 3:
                        currentColor =  R.color.cl3;
                        break;
                    case 4:
                        currentColor =  R.color.cl6;
                        break;
                    case 5:
                        currentColor =  R.color.cl5;
                        break;
                    case 6:
                        currentColor =  R.color.cl7;
                        break;
                    case 7:
                        currentColor =  R.color.cl8;
                        break;
                    case 8:
                        currentColor =  R.color.cl9;
                        break;
                    default:
                        currentColor =  R.color.cl4;
                }
                myCanvas.setPenColor(currentColor);//my defined function ,view the My canvas view to see its details
                //basically is a modified version of setColor(int color);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //currentColor=0x000000;
                //myCanvas.setPenColor(currentColor);
            }
        });


    }//onCreate method finishes here

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
       return Environment.MEDIA_MOUNTED.equals(state);
    }

    public File getPublicAlbumStorageDir(String albumName) {

//        File file = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES), albumName);
//        if (!file.mkdirs()) {
//            Log.e("appname", "Directory not created");
//            Log.e("appname",Environment.getExternalStoragePublicDirectory(
//                    Environment.DIRECTORY_PICTURES).toString());
//        }
        String s=Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString();
        File file=new File(s+"/"+albumName);
        if(!file.exists())
            if (file.mkdirs())
                file.mkdir();

        if (!file.exists()) {
            Log.e("appname", "Directory not created");
            Log.e("appname",file.toString());
        }

        return file;
    }


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
            case R.id.dialogBoxSettings:
                showAlwaysInstructionDialogBox();
                return true;
            case R.id.about:
                dialog2.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void clearTheCanvas(View view) {
        myCanvas.clearCanvas();
    }

    public void setPen(View view) {
        //setting the pen doing later
        isPenSelected = true;
        myCanvas.setPenSize(penSize * 4);
        myCanvas.setPenColor(currentColor);
        sizeSpinnerPen.setVisibility(View.VISIBLE);
        colorSpinner.setVisibility(View.VISIBLE);
        eraserSpinner.setVisibility(View.GONE);
    }

    public void setEraser(View view) {
        //setting eraser i will do later after the completion of spinner object
        myCanvas.setPenSize(eraserSize * 5);
        myCanvas.setPenColor(R.color.White);
        eraserSpinner.setVisibility(View.VISIBLE);
        sizeSpinnerPen.setVisibility(View.GONE);
        colorSpinner.setVisibility(View.GONE);
        isPenSelected = false;

    }

    public void toolOptions() {
        if (isOptionsVisible) {
            PenButton.setVisibility(View.GONE);
            sizeSpinnerPen.setVisibility(View.GONE);
            colorSpinner.setVisibility(View.GONE);
            EraserButton.setVisibility(View.GONE);
            eraserSpinner.setVisibility(View.GONE);
            ClearAllButton.setVisibility(View.GONE);
            isOptionsVisible = false;
            ToolsButton.setVisibility(View.GONE);
            SaveButton.setVisibility(View.GONE);
        } else {
            PenButton.setVisibility(View.VISIBLE);
            EraserButton.setVisibility(View.VISIBLE);
            ClearAllButton.setVisibility(View.VISIBLE);
            SaveButton.setVisibility(View.VISIBLE);
            isOptionsVisible = true;
            ToolsButton.setVisibility(View.VISIBLE);

            if (isPenSelected) {
                sizeSpinnerPen.setVisibility(View.VISIBLE);
                colorSpinner.setVisibility(View.VISIBLE);
            } else eraserSpinner.setVisibility(View.VISIBLE);

        }

    }

    public void toolOptions(View view) {
        toolOptions();
    }
    //tools option method

    public void saveFileMK(View view){

        if(isExternalStorageWritable()){
            dir=getPublicAlbumStorageDir("PaintYourIdeas");
        }
        try {
            if (!dir.exists()) {
              if (!dir.mkdirs())
                  dir.mkdir();//ignor the result
            }


            builder.setTitle("Save Your Amazing Idea");
            builder.setMessage("Enter the file name below: (change the default name in the space below)");
            final EditText input = new EditText(MainActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            builder.setView(input);

            String defName="YourAwesomeIdea"+sharedpreferences.getInt(numberImage,0);
            input.setText(defName,TextView.BufferType.EDITABLE);


            builder.setPositiveButton("Save the Idea", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String finalFileName=input.getText().toString();
                    if ((finalFileName.substring(0,5)).equals("YourA")){
                        SharedPreferences.Editor editor=sharedpreferences.edit();
                        editor.putInt(numberImage,sharedpreferences.getInt(numberImage,0)+1);
                        editor.apply();
                    }
                    File toSave = new File(dir.toString(), finalFileName + ".png");
                    OutputStream outputStream ;

                    try {
                        outputStream = new FileOutputStream(toSave);
                        myCanvas.mbitmap.setHasAlpha(false);
                        myCanvas.mbitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        outputStream.flush();
                        outputStream.close();

                        MediaScannerConnection.scanFile(MainActivity.this, new String[]{toSave.toString()}, null,
                                new MediaScannerConnection.OnScanCompletedListener() {
                                    public void onScanCompleted(String path, Uri uri) {
                                        Log.d("appname", "image is saved in gallery and gallery is refreshed.");
                                    }
                                }
                        );


                        if (toSave.exists()) {
                            Toast toast = Toast.makeText(MainActivity.this, "file saved" + "\n" + toSave.toString(), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } catch (Exception e) {
                        String s = "" + e;
                        Toast toast = Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    //file saved if possible to save the file

                }//try statement

            });
            builder.setNegativeButton("Don't save ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //just simply dismiss the dialog
                }
            });

            AlertDialog dialog4=builder.create();
            dialog4.show();

        }


        catch(Exception e){
                String s=""+e;
                Toast toast =Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT);
                toast.show();
            }



    }//method finished




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //this is left blank as if permission is granted ,user can save ideas easily there will be no  problem for him
                } else
                {
                    Toast.makeText(MainActivity.this
                            , "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }




    public void showAlwaysInstructionDialogBox(){

        //setting the dialog box for the always instruction prompts ,it was an amazing experience creating this dialog
        //a lot of debug ,hhhhhhh!!
        builder.setTitle(titleSettingInstruction);
        builder.setMessage(msgSettingInstructions);
        builder.setPositiveButton(R.string.y, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor=sharedpreferences.edit();
                editor.putBoolean(alwaysInstruction,true);
                editor.apply();
            }
        });
        builder.setNegativeButton(R.string.n, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor=sharedpreferences.edit();
                editor.putBoolean(alwaysInstruction,false);
                editor.apply();
            }
        });
        AlertDialog dialog3=builder.create();

        dialog3.show();

        SharedPreferences.Editor editor=sharedpreferences.edit();
        editor.putBoolean(alwaysInstructionDialog,false);
        editor.apply();
    }
}
