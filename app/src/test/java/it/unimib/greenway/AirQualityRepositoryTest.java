package it.unimib.greenway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import it.unimib.greenway.data.repository.airQuality.AirQualityRepositoryWithLiveData;
import it.unimib.greenway.model.Result;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class AirQualityRepositoryTest {

    private AirQualityRepositoryWithLiveData repository;

    @Before
    public void setUp() {
        repository = mock(AirQualityRepositoryWithLiveData.class);
    }

    @Test
    public void testFetchAllAirQuality_fetchesFromRepository() {
        // Arrange
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        int lastUpdate = 1;
        View view = mock(View.class);
        when(repository.fetchAllAirQUality(lastUpdate, view)).thenReturn(liveData);

        // Act
        MutableLiveData<Result> result = repository.fetchAllAirQUality(lastUpdate, view);

        // Assert
        assertNotNull(result);
        assertEquals(result, liveData);
    }

    // Add more tests for other methods in the AirQualityRepository

}