package it.unimib.greenway;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import it.unimib.greenway.util.SharedPreferencesUtil;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28})
public class SharedPreferencesUtilTest {

    private SharedPreferencesUtil sharedPreferencesUtil;
    private Application context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        sharedPreferencesUtil = new SharedPreferencesUtil(context);
    }

    @Test
    public void testWriteAndReadBooleanData() {
        String fileName = "test_prefs";
        String key = "test_boolean_key";
        boolean value = true;

        sharedPreferencesUtil.writeBooleanData(fileName, key, value);
        boolean result = sharedPreferencesUtil.readBooleanData(fileName, key);

        assertTrue(result);
    }

    @Test
    public void testWriteAndReadStringData() {
        String fileName = "test_prefs";
        String key = "test_string_key";
        String value = "test_value";

        sharedPreferencesUtil.writeStringData(fileName, key, value);
        String result = sharedPreferencesUtil.readStringData(fileName, key);

        assertEquals(value, result);
    }

    @Test
    public void testReadBooleanDataDefault() {
        String fileName = "test_prefs";
        String key = "non_existing_key";

        boolean result = sharedPreferencesUtil.readBooleanData(fileName, key);

        assertEquals(false, result);
    }

    @Test
    public void testReadStringDataDefault() {
        String fileName = "test_prefs";
        String key = "non_existing_key";

        String result = sharedPreferencesUtil.readStringData(fileName, key);

        assertNull(result);
    }
}
