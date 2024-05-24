package it.unimib.greenway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import it.unimib.greenway.data.repository.airQuality.IAirQualityRepositoryWithLiveData;
import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.ui.main.AirQualityViewModel;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class AirQualityViewModelTest {

    private IAirQualityRepositoryWithLiveData repository;
    private AirQualityViewModel viewModel;
    private View view;

    @Before
    public void setUp() {
        repository = mock(IAirQualityRepositoryWithLiveData.class);
        viewModel = new AirQualityViewModel(repository);
        view = mock(View.class); // Mocking the View object
    }

    @Test
    public void testGetAirQuality_whenLiveDataIsNull_fetchesFromRepository() {
        // Arrange
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        when(repository.fetchAllAirQUality(eq(0L), eq(view))).thenReturn(liveData);

        // Act
        MutableLiveData<Result> result = viewModel.getAirQuality(0, view);

        // Assert
        assertNotNull(result);
        assertEquals(result, liveData);
    }

    @Test
    public void testFetchAirQuality_fetchesFromRepository() {
        // Arrange
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        when(repository.fetchAllAirQUality(anyLong(), eq(view))).thenReturn(liveData);

        // Act
        viewModel.fetchAirQuality(0, view);
        MutableLiveData<Result> result = viewModel.getAirQuality(0, view);

        // Assert
        assertNotNull(result);
        assertEquals(result, liveData);
    }
}