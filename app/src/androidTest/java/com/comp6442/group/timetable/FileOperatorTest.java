/**
 * The FileOperatorTest class implements tests for
 * testing all the methods defined in FileOperator
 *
 * @author  Yongchao Lyu (u6874539)
 * @version 1.0
 * @since   2019-10-16
 */

package com.comp6442.group.timetable;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class FileOperatorTest {

    private Context appContext;
    private Map<String, Integer> rawFileMap = new HashMap<>();

    @Before
    public void initialSetting() {
        appContext = InstrumentationRegistry.getTargetContext();

        rawFileMap.put("user.json", R.raw.user);
        rawFileMap.put("courses.json", R.raw.courses);
        rawFileMap.put("specialisation.json", R.raw.specialisation);
        rawFileMap.put("compatibility.json", R.raw.compatibility);
    }

    @Test
    public void readRawFile() {
        // Test for reading all files in res/raw
        for (String fileName: rawFileMap.keySet()) {
            FileOperator fileOperator = new FileOperator(appContext, fileName);
            String rawFileContent = fileOperator.readRawFile(rawFileMap.get(fileName));
            assertTrue(
                    "Cannot read raw file: " + fileName,
                    rawFileContent != null && !rawFileContent.isEmpty());
        }
    }

    @Test
    public void placeInternalFile() {
        // Test for migrating file from res/raw to /data/data/packageName/files
        for (String fileName: rawFileMap.keySet()) {
            String filePath = Paths.get(appContext.getFilesDir().getAbsolutePath(), fileName).toString();
            File internalFile = new File(filePath);
            if (!internalFile.exists() || internalFile.delete()) {
                FileOperator fileOperator = new FileOperator(appContext, fileName);
                fileOperator.placeInternalFile(rawFileMap.get(fileName));
            }
            assertTrue("Cannot migrate raw file: " + fileName, internalFile.exists());
        }
    }

    @Test
    public void readInternalFile() {
        // Test for reading internal files, which located at /data/data/packageName/files
        for (String fileName: rawFileMap.keySet()) {
            String filePath = Paths.get(appContext.getFilesDir().getAbsolutePath(), fileName).toString();
            File internalFile = new File(filePath);
            assertTrue("File doesn't exist: " + fileName, internalFile.exists());

            if (internalFile.exists()) {
                FileOperator fileOperator = new FileOperator(appContext, fileName);
                String fileContent = fileOperator.readInternalFile();
                assertTrue(fileContent != null && !fileContent.isEmpty());
            }
        }
    }

    @Test
    public void writeInternalFile() {
        // Test for writing internal files, which located at /data/data/packageName/files
        Set<String> jsonStrings = new HashSet<>();
        jsonStrings.add("{\"i1\": 1, \"i2\": 2}");
        jsonStrings.add("{\"s1\": \"c1\", \"s2\": \"c2\", \"s3\": \"c3\"}");
        jsonStrings.add("{\"m1\": \"c1\", \"m2\": 2, \"m3\": \"c3\"}");

        for (String fileName: rawFileMap.keySet()) {
            FileOperator fileOperator = new FileOperator(appContext, fileName);
            for (String jsonString: jsonStrings) {
                fileOperator.writeInternalFile(jsonString);
                String readString = fileOperator.readInternalFile();
                assertEquals(
                        String.format("\nExpect: %s\nGot: %s", jsonString, readString),
                        readString, jsonString);
            }
        }
    }
}