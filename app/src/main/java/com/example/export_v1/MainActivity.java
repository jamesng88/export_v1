package com.example.export_v1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.RequiresApi;

import org.w3c.dom.Text;

public class MainActivity extends Activity implements OnClickListener {

    private static final String SAMPLE_DB_NAME = "TrekBook";
    private static final String SAMPLE_TABLE_NAME = "Info";
    String x;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                deleteDB();
                break;
            case R.id.button2:
                exportDB();

                break;
            case R.id.button3:
                createDB();
                break;
        }
    }


    private void deleteDB() {
        boolean result = this.deleteDatabase(SAMPLE_DB_NAME);
        if (result == true) {
            Toast.makeText(this, "DB Deleted!", Toast.LENGTH_LONG).show();
        }
    }

    private void createDB() {
        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                SAMPLE_TABLE_NAME +
                " (LastName VARCHAR, FirstName VARCHAR," +
                " Rank VARCHAR);");
        sampleDB.execSQL("INSERT INTO " +
                SAMPLE_TABLE_NAME +
                " Values ('Kirk','James, T','Captain');");
        sampleDB.close();
        sampleDB.getPath();
        x = sampleDB.getPath();
        Toast.makeText(this, "DB Created @ " + sampleDB.getPath(), Toast.LENGTH_LONG).show();
    }

    /*private static final String SAMPLE_DB_NAME = "TrekBook";
    private static final String SAMPLE_TABLE_NAME = "Info";*/
   /* private void exportDB() throws IOException {
        String sd = Environment.getExternalStorageDirectory().getPath();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/data/<com.example.export_v1>/databases/Info.db";
        String backupDBPath = "TrekBook.db";
        File currentDB = new File( currentDBPath);
        //File backupDB = new File( "/内部存储/data/",backupDBPath);
        File backupDB = new File( sd,backupDBPath);
        FileInputStream fis = new FileInputStream(currentDB);

        String outFileName = Environment.getExternalStorageDirectory()+"/database_copy.db";

        // Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);

        // Transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer))>0){
            output.write(buffer, 0, length);
        }

        // Close the streams
        output.flush();
        output.close();
        fis.close();
    }*/
    /*private void exportDB(){
        //String sd = Environment.getExternalStorageDirectory().getPath();
        final String inFileName = Environment.getDataDirectory().getPath();
        String sd = "/storage/sdcard0/";
        String s2 = getApplicationContext().getApplicationInfo().packageName;
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/data/com.example.export_v1/databases/TrekBook.db";
        String backupDBPath = SAMPLE_DB_NAME+".db";
        File currentDB = new File(currentDBPath);
        //File backupDB = new File( "/内部存储/data/",backupDBPath);
        //File backupDB = new File( sd,backupDBPath);
        File backupDB = new File(data ,"/data/com.example.export_v1/databases/new");
        try {
            //FileInputStream fin = ;
            //FileInputStream fin = new FileInputStream(currentDB);
            //source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(inFileName).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
            Toast.makeText(this, s2, Toast.LENGTH_LONG).show();
        }
    }*/

    /*private void exportDB() {

        File file;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
            File dir = getFilesDir();

            File ddfile = new File(Environment.getExternalStorageDirectory() , "/csvname");
            if (!ddfile.exists())
            {
                ddfile.mkdir();
            }
            file = new File(ddfile,"csvname.csv");
            StringBuilder data = new StringBuilder();
            try {
                SQLiteDatabase db = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
                Cursor curCSV = db.rawQuery("SELECT * FROM " + SAMPLE_TABLE_NAME, null);
                000
                try {
                    FileOutputStream stream = new FileOutputStream(file);
                }
                catch (FileNotFoundException e)
                {
                    Toast.makeText(this, "e1", Toast.LENGTH_LONG).show();
                }
                catch (IOException e)
                {
                    Toast.makeText(this, "e2", Toast.LENGTH_LONG).show();
                } 000
                FileWriter stream = new FileWriter(file);
                int i = 0;
                while (curCSV.moveToNext()) {
                    //Which column you want to exprort
                    data.append(curCSV.getString(0));
                    //data[i] = (curCSV.getString(0));
                    i++;
                }

                stream.write(data.toString());
                //stream.flush();
                stream.close();

                //MediaScannerConnection.scanFile(this, new String[] {file.getAbsolutePath()},new String[] (), null);

                //curCSV.close();
                Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
                //Toast.makeText(this, data.toString(), Toast.LENGTH_LONG).show();
            } catch (Exception sqlEx) {
                Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "cant read", Toast.LENGTH_LONG).show();
        }

    }*/

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void exportDB() {

        File file;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
            File dir = getFilesDir();

            File ddfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/csvname");
            if (!ddfile.exists())
            {
                ddfile.mkdir();
            }
            file = new File(ddfile,"csvname.csv");
            StringBuilder data = new StringBuilder();
            try {
                SQLiteDatabase db = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
                Cursor curCSV = db.rawQuery("SELECT * FROM " + SAMPLE_TABLE_NAME, null);
                /*
                try {
                    FileOutputStream stream = new FileOutputStream(file);
                }
                catch (FileNotFoundException e)
                {
                    Toast.makeText(this, "e1", Toast.LENGTH_LONG).show();
                }
                catch (IOException e)
                {
                    Toast.makeText(this, "e2", Toast.LENGTH_LONG).show();
                }*/
                FileWriter stream = new FileWriter(file);
                int i = 0;
                while (curCSV.moveToNext()) {
                    //Which column you want to exprort
                    data.append(curCSV.getString(0));
                    //data[i] = (curCSV.getString(0));
                    i++;
                }

                stream.write(data.toString());
                //stream.flush();
                stream.close();
                scanMedia(file.toString());
                //MediaScannerConnection.scanFile(this, new String[] {file.getAbsolutePath()},new String[] (), null);

                //curCSV.close();
                Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
                //Toast.makeText(this, data.toString(), Toast.LENGTH_LONG).show();
            } catch (Exception sqlEx) {
                Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "cant read", Toast.LENGTH_LONG).show();
        }

    }

    private void scanMedia(String path) {
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        Intent scanFileIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        sendBroadcast(scanFileIntent);
    }
}