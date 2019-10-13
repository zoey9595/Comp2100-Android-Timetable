package com.comp6442.group.timetable;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Compatibility extends FileOperator{
    private static Compatibility compatibilityInstance = null;
    private JSONObject compatibility = new JSONObject();

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

    public static Compatibility getCompatibilityInstance(Context context) {
        if (compatibilityInstance == null)
            compatibilityInstance = new Compatibility(context);
        return compatibilityInstance;
    }

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
