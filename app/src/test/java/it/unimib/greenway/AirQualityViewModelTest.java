package it.unimib.greenway;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.unimib.greenway.data.repository.airQuality.AirQualityRepositoryWithLiveData;
import it.unimib.greenway.data.source.airQuality.BaseAirQualityLocalDataSource;
import it.unimib.greenway.data.source.airQuality.BaseAirQualityRemoteDataSource;
import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.model.Result;

@RunWith(RobolectricTestRunner.class)
public class AirQualityViewModelTest {


    @Mock
    BaseAirQualityLocalDataSource mockLocalDataSource;

    @Mock
    BaseAirQualityRemoteDataSource mockRemoteDataSource;

    private AirQualityRepositoryWithLiveData repository;
    private final long TWO_HOURS = 2 * 60 * 60 * 1000;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        repository = new AirQualityRepositoryWithLiveData(mockLocalDataSource, mockRemoteDataSource);
    }

    @Test
    public void testFetchAllAirQuality_remoteDataSourceCalled() {
        // Given
        long lastUpdate = System.currentTimeMillis() - TWO_HOURS - 1;

        // When
        repository.fetchAllAirQUality(lastUpdate);

        // Then
        verify(mockRemoteDataSource).getAirQuality();
        verify(mockLocalDataSource, never()).getAirQuality();
    }

    @Test
    public void testFetchAllAirQuality_localDataSourceCalled() {
        // Given
        long lastUpdate = System.currentTimeMillis() - TWO_HOURS + 1;

        // When
        repository.fetchAllAirQUality(lastUpdate);

        // Then
        verify(mockLocalDataSource).getAirQuality();
        verify(mockRemoteDataSource, never()).getAirQuality();
    }

    @Test
    public void testOnSuccessFromLocal_updatesLiveData() {
        // Given
        List<AirQuality> airQualityList = Collections.singletonList(new AirQuality(new byte[0], 1, 2));

        // When
        repository.onSuccessFromLocal(airQualityList);

        // Then
        MutableLiveData<Result> liveData = repository.fetchAllAirQUality(0);
        Result result = liveData.getValue();
        assertTrue(result instanceof Result.AirQualityResponseSuccess);
        assertEquals(((Result.AirQualityResponseSuccess) result).getData().getAirQualities(), airQualityList);
    }

    @Test
    public void testGetAirQualityList() throws ExecutionException, InterruptedException {
        // Given
        List<AirQuality> airQualityList = Collections.singletonList(new AirQuality(new byte[0], 1, 2));
        when(mockLocalDataSource.getAirQualityList()).thenReturn(airQualityList);

        // When
        List<AirQuality> result = repository.getAirQualityList();

        // Then
        assertEquals(result, airQualityList);
    }
}