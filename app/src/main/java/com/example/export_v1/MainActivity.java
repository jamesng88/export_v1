package com.example.export_v1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import android.content.ContentValues;
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
    SQLiteDatabase db;

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
        db = this.openOrCreateDatabase("Database",MODE_PRIVATE,null);
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
        createPatientTable();
        createNurseTable();
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
                SQLiteDatabase db = this.openOrCreateDatabase("Database", MODE_PRIVATE, null);
                Cursor curCSV = db.rawQuery("SELECT * FROM " + "Nurse", null);

                FileWriter stream = new FileWriter(file);
                int i = 0;
                while (curCSV.moveToNext()) {
                    //Which column you want to export
                    data.append(curCSV.getString(0));
                    data.append(";");
                    data.append(curCSV.getString(1));
                    data.append(";");
                    data.append(curCSV.getString(2));
                    data.append(";");
                    data.append(curCSV.getString(3));
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

    private void createNurseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Nurse (nurse_id TEXT NOT NULL, nurse_name TEXT NOT NULL, nurse_password TEXT NOT NULL, nurse_authority INT NOT NULL, PRIMARY KEY(nurse_id))";
        db.execSQL(sql);
        ContentValues contentValues = new ContentValues(1);
        Cursor cursor = db.rawQuery("SELECT * FROM Nurse", null);
        if (!cursor.moveToFirst()) {
            contentValues.put("nurse_id", "admin");
            contentValues.put("nurse_name", "Admin");
            contentValues.put("nurse_password", "admin");
            contentValues.put("nurse_authority", 1);
            db.insert("Nurse", null, contentValues);
            contentValues.put("nurse_id", "admin1");
            contentValues.put("nurse_name", "Admin1");
            contentValues.put("nurse_password", "admin1");
            contentValues.put("nurse_authority", 1);
            db.insert("Nurse", null, contentValues);
            contentValues.put("nurse_id", "admin2");
            contentValues.put("nurse_name", "Admin2");
            contentValues.put("nurse_password", "admin2");
            contentValues.put("nurse_authority", 1);
            db.insert("Nurse", null, contentValues);
        }
    }

    private void createPatientTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Patient (patient_id TEXT NOT NULL, patient_name TEXT NOT NULL, patient_gender INT, patient_register DATE, patient_sign TEXT, patient_birth DATE , patient_incharge TEXT NOT NULL, PRIMARY KEY(patient_id), FOREIGN KEY(patient_incharge) REFERENCES Nurse(nurse_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
        ContentValues contentValues = new ContentValues(1);
        Cursor cursor = db.rawQuery("Select * From Patient",null);
        if (!cursor.moveToFirst()) {
            contentValues.put("patient_id", "p1");
            contentValues.put("patient_name", "p21");
            contentValues.put("patient_gender", "4");
            contentValues.put("patient_incharge", "admin");
            db.insert("Patient", null, contentValues);
            contentValues.put("patient_id", "p2");
            contentValues.put("patient_name", "p22");
            contentValues.put("patient_gender", "4");
            contentValues.put("patient_incharge", "admin1");
            db.insert("Patient", null, contentValues);
            contentValues.put("patient_id", "p3");
            contentValues.put("patient_name", "p23");
            contentValues.put("patient_gender", "4");
            contentValues.put("patient_incharge", "admin3");
            db.insert("Patient", null, contentValues);
        }
    }



}