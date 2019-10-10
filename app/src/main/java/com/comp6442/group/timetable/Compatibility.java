package com.comp6442.group.timetable;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Compatibility {
    private static Compatibility compatibilityInstance = null;
    private JSONObject compatibility = new JSONObject();

    public Compatibility(Context context)
    {
        try {

            InputStream inputStream = context.getResources().openRawResource(R.raw.compatibility);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();

            String jsonFileLine = bReader.readLine();
            while (jsonFileLine != null) {
                stringBuilder.append(jsonFileLine);
                jsonFileLine = bReader.readLine();
            }
            this.compatibility =  new JSONObject(stringBuilder.toString());

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
        Map<String,String> compatibility = new HashMap<>();

        try {
            JSONObject courseDetail = (JSONObject) this.compatibility.get(courseKey);

            compatibility.put(Utility.FULL_NAME,(String) courseDetail.get(Utility.FULL_NAME));
            compatibility.put(Utility.REQUISITE,(String) courseDetail.get(Utility.REQUISITE));
            compatibility.put(Utility.INCOMPATIBILITY,(String) courseDetail.get(Utility.INCOMPATIBILITY));



        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }

        return compatibility;
    }
}
