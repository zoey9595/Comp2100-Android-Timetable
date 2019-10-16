/**
 * The FileOperator class implements methods for operating files,
 * both resource files that locate at res/raw of the project and
 * internal files that locate at /data/data/packageName/files of android.
 * One of the methods is used for migrating files from resource to internal.
 *
 * @author  Yongchao Lyu (u6874539)
 * @version 1.0
 * @since   2019-10-16
 */

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

    /**
     * Constructor of the class for initialise member variables
     *
     * @param context application context
     * @param filename filename of the file to be operated
     *
     */
    FileOperator(Context context, String filename) {
        this.context = context;
        this.fileName = filename;
        this.filePath = Paths.get(
                this.context.getFilesDir().getAbsolutePath(),
                this.fileName).toString();
    }

    /**
     * Read a resource file using resource ID, such as R.raw.user
     *
     * @param rID resource id for the file, eg. R.raw.courses
     * @return String json string that read from the file
     *
     */
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

    /**
     * Migrate resource files to internal storage, i.e.
     * From res/raw to /data/data/packageName/files
     *
     * @param rID resource id for the file, eg. R.raw.courses
     *
     */
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

    /**
     * Read a file stored at internal storage,
     * use the fileName stored at a member variable
     *
     * @return String json string that read from the file
     *
     */
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

    /**
     * Write a file stored at internal storage
     *
     * @param jsonString a json string to be write into the internal file
     *
     */
    public void writeInternalFile(String jsonString) {
        try {
            FileOutputStream outputStream;
            outputStream = this.context.openFileOutput(this.fileName, 0);
            outputStream.write(jsonString.replace("\\", "").getBytes());
            outputStream.close();

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
    }
}
