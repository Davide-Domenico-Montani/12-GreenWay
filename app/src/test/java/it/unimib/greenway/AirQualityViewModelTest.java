package it.unimib.greenway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import it.unimib.greenway.data.source.airQuality.BaseAirQualityLocalDataSource;
import it.unimib.greenway.data.source.airQuality.BaseAirQualityRemoteDataSource;
import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.model.AirQualityResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import org.junit.Before;


import java.util.ArrayList;
import java.util.List;

import it.unimib.greenway.data.repository.airQuality.AirQualityRepositoryWithLiveData;

import it.unimib.greenway.model.Result;
import it.unimib.greenway.ui.main.AirQualityViewModel;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class AirQualityViewModelTest {

    private AirQualityRepositoryWithLiveData repository;
    private AirQualityViewModel viewModel;

    @Before
    public void setUp() {
        BaseAirQualityLocalDataSource localDataSource = mock(BaseAirQualityLocalDataSource.class);
        BaseAirQualityRemoteDataSource remoteDataSource = mock(BaseAirQualityRemoteDataSource.class);

        repository = new AirQualityRepositoryWithLiveData(localDataSource, remoteDataSource);
        viewModel = new AirQualityViewModel(repository);
    }

    @Test
    public void testFetchAllAirQuality_whenLastUpdateIsZero_fetchesFromRemote() {
        // Arrange
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        when(repository.fetchAllAirQUality(0)).thenReturn(liveData);

        // Act
        LiveData<Result> result = viewModel.getAirQuality(0);

        // Assert
        assertNotNull(result);
        assertEquals(result, liveData);
    }

    @Test
    public void testRefreshData_whenLastUpdateIsRecent_fetchesFromLocal() {
        // Arrange
        long lastUpdate = System.currentTimeMillis() - 1 * 60 * 60 * 1000; // 1 hour ago
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        when(repository.fetchAllAirQUality(lastUpdate)).thenReturn(liveData);

        // Act
        viewModel.fetchAirQuality(lastUpdate);
        LiveData<Result> result = viewModel.getAirQuality(lastUpdate);

        // Assert
        assertNotNull(result);
        assertEquals(result, liveData);
    }

    @Test
    public void testOnSuccessFromLocal_postsResultToLiveData() {
        // Arrange
        List<AirQuality> airQualityList = new ArrayList<>();
        airQualityList.add(new AirQuality(new byte[]{}, 1, 2));
        Result.AirQualityResponseSuccess success = new Result.AirQualityResponseSuccess(new AirQualityResponse(airQualityList));
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        when(repository.fetchAllAirQUality(anyLong())).thenReturn(liveData);
        liveData.postValue(success);

        // Act
        viewModel.fetchAirQuality(0);
        LiveData<Result> result = viewModel.getAirQuality(0);

        // Assert
        assertNotNull(result);
        assertTrue(result.getValue() instanceof Result.AirQualityResponseSuccess);
        assertEquals(((Result.AirQualityResponseSuccess) result.getValue()).getData().getAirQualities(), airQualityList);
    }
}