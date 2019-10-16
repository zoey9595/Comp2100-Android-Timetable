package com.comp6442.group.timetable;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class CompatibilityTest {

    private Context appContext;
    private Map<String, List<String>> testCases;

    /**
     * @author  Jingwei Wang (u68919789)
     *
     * Initial settings and test cases before execute tests
     */
    @Before
    public void initialSetting() {
        appContext = InstrumentationRegistry.getTargetContext();
    }

    /**
     * @author  Yongchao Lyu (u6874539)
     *
     * Test for the method getCompatibilityInstance
     */
    @Test
    public void getCompatibilityInstanceTest() {
        Compatibility compatibility = Compatibility.getCompatibilityInstance(appContext);
        assertNotNull("\nCannot get an instance of Course.", compatibility);
    }

    /**
     * @author  Yongchao Lyu (u6874539)
     *
     * Test for the method getCoursesCompatiblityById
     */
    @Test
    public void getCoursesCompatiblityByIdTest() {
        Compatibility compatibility = Compatibility.getCompatibilityInstance(appContext);

        Map<String, String> compatibilityDetails = new HashMap<>();
        compatibilityDetails = compatibility.getCoursesCompatiblityById("COMP8420");
        assertNotNull(compatibilityDetails); //COMP8420 does not has compatibility
    }

}
