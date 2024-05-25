package it.unimib.greenway;
import android.content.Context;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import it.unimib.greenway.util.DataEncryptionUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
public class DataEncryptionUtilTest {

    private DataEncryptionUtil dataEncryptionUtil;
    private Context context;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.getApplication();
        dataEncryptionUtil = new DataEncryptionUtil(context);
    }

    @Test
    public void testWriteAndReadSecretDataWithEncryptedSharedPreferences() throws GeneralSecurityException, IOException {
        String sharedPreferencesFileName = "test_shared_prefs";
        String key = "test_key";
        String value = "test_value";

        // Write data
        dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(sharedPreferencesFileName, key, value);

        // Read data
        String retrievedValue = dataEncryptionUtil.readSecretDataWithEncryptedSharedPreferences(sharedPreferencesFileName, key);

        assertEquals(value, retrievedValue);
    }

    @Test
    public void testWriteAndReadSecreteDataOnFile() throws GeneralSecurityException, IOException {
        String fileName = "test_file";
        String data = "test_data";

        // Write data
        dataEncryptionUtil.writeSecreteDataOnFile(fileName, data);

        // Read data
        String retrievedData = dataEncryptionUtil.readSecretDataOnFile(fileName);

        assertEquals(data, retrievedData);
    }

    @Test
    public void testDeleteAll() throws GeneralSecurityException, IOException {
        String sharedPreferencesFileName = "test_shared_prefs";
        String fileName = "test_file";
        String key = "test_key";
        String value = "test_value";
        String data = "test_data";

        // Write data to shared preferences and file
        dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(sharedPreferencesFileName, key, value);
        dataEncryptionUtil.writeSecreteDataOnFile(fileName, data);

        // Verify data is written
        assertEquals(value, dataEncryptionUtil.readSecretDataWithEncryptedSharedPreferences(sharedPreferencesFileName, key));
        assertEquals(data, dataEncryptionUtil.readSecretDataOnFile(fileName));

        // Delete all data
        dataEncryptionUtil.deleteAll(sharedPreferencesFileName, fileName);

        // Verify data is deleted from shared preferences
        assertNull(dataEncryptionUtil.readSecretDataWithEncryptedSharedPreferences(sharedPreferencesFileName, key));

    }
}
