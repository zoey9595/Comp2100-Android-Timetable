package com.comp6442.group.timetable;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class FileOperator {

    private Context context;
    private String fileName;
    private String filePath;

    FileOperator(Context context, String filename) {
        this.context = context;
        this.fileName = filename;
        this.filePath = Paths.get(
                this.context.getFilesDir().getAbsolutePath(),
                this.fileName).toString();
    }

    public String readRawFile(int rID) {
        try {
            InputStream inputStream = this.context.getResources().openRawResource(rID);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();

            String jsonFileLine = bReader.readLine();
            while (jsonFileLine != null) {
                stringBuilder.append(jsonFileLine);
                jsonFileLine = bReader.readLine();
            }

            return stringBuilder.toString();

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }

        return null;
    }

    public void placeInternalFile(int rID) {
        try {
            File internalFile = new File(this.filePath);
            if (!internalFile.exists()) {
                String rawString = readRawFile(rID);
                if (rawString != null) {
                    FileOutputStream outputStream;
                    outputStream = this.context.openFileOutput(this.fileName, 0);
                    outputStream.write(rawString.getBytes());
                    outputStream.close();
                }
            }

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
    }

    public String readInternalFile() {
        try {
            FileInputStream inputStream;
            inputStream = this.context.openFileInput(this.fileName);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();

            String jsonFileLine = bReader.readLine();
            while (jsonFileLine != null) {
                stringBuilder.append(jsonFileLine);
                jsonFileLine = bReader.readLine();
            }

            return stringBuilder.toString();

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }

        return null;
    }

    public void writeInternalFile(String jsonString) {
        try {
            FileOutputStream outputStream;
            outputStream = this.context.openFileOutput(this.fileName, 0);
            outputStream.write(jsonString.getBytes());
            outputStream.close();

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
    }
}
