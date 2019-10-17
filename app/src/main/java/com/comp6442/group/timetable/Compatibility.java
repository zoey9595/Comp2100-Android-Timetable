package com.comp6442.group.timetable;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Compatibility extends FileOperator{
    private static Compatibility compatibilityInstance = null;
    private JSONObject compatibility = new JSONObject();

    /**
     * @author Yongchao Lyu (u6874539), Jingwei Wang(u6891978)
     *
     * The constructor of the class for initialising
     * 1. Set the filename of the file to be operated
     * 2. Migrate the resource file to internal storage if it has not been done yet.
     * 3. Read the content of this file from internal storage to a member variable
     *
     * Note: the modifier is "private", which means we are defining a singleton class
     *
     * @param context context of the application
     *
     */
    public Compatibility(Context context) {
        super(context, "compatibility.json");

        try {
            this.placeInternalFile(R.raw.compatibility);
            String jsonString = this.readInternalFile();
            if (jsonString != null)
                this.compatibility = new JSONObject(jsonString);

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
    }

    /**
     * @author Yongchao Lyu (u6874539), Jingwei Wang(u6891978)
     *
     * Method for getting the instance of this singleton class
     *
     * Note: The class can only be instantiated once for preventing the file
     * to be read several times. This kind of design fobidden read the same file
     * more than once such that the limited memory of the device can be saved.
     *
     * @param context context of the application
     *
     */
    public static Compatibility getCompatibilityInstance(Context context) {
        if (compatibilityInstance == null)
            compatibilityInstance = new Compatibility(context);
        return compatibilityInstance;
    }

    //get course compatibility information
    public Map<String, String> getCoursesCompatiblityById(String courseKey) {
        Map<String, String> compatibility = new HashMap<>();

        try {
            JSONObject courseDetail = (JSONObject) this.compatibility.get(courseKey);
            compatibility.put(Utility.FULL_NAME, (String) courseDetail.get(Utility.FULL_NAME));
            compatibility.put(Utility.REQUISITE, (String) courseDetail.get(Utility.REQUISITE));
            compatibility.put(Utility.INCOMPATIBILITY, (String) courseDetail.get(Utility.INCOMPATIBILITY));

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return compatibility;
    }
}
