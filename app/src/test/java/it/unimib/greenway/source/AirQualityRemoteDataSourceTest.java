package it.unimib.greenway.source;

import it.unimib.greenway.data.service.AirQualityApiService;
import it.unimib.greenway.data.source.airQuality.AirQualityRemoteDataSource;
import it.unimib.greenway.model.AirQuality;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

@RunWith(MockitoJUnitRunner.class)
public class AirQualityRemoteDataSourceTest {

    @Mock
    private AirQualityApiService airQualityApiService;

    @Mock
    private Call<ResponseBody> call;

    @Captor
    private ArgumentCaptor<Callback<ResponseBody>> callbackCaptor;

    private AirQualityRemoteDataSource airQualityRemoteDataSource;

    @Before
    public void setup() {
        airQualityApiService = mock(AirQualityApiService.class);
        airQualityRemoteDataSource = new AirQualityRemoteDataSource();
    }

    @Test
    public void testGetAirQuality() {
        View view = mock(View.class);  // Create a mock View
        when(airQualityApiService.fetchAirQualityImage(anyString(), anyInt(), anyInt(), anyInt(), anyString()))
                .thenReturn(call);

        airQualityRemoteDataSource.getAirQuality(view);  // Pass the mock View

        verify(call).enqueue(callbackCaptor.capture());

        // Now you can trigger the onResponse or onFailure manually
        // For example:
        callbackCaptor.getValue().onResponse(call, Response.success(mock(ResponseBody.class)));

        verify(airQualityApiService, times(1)).fetchAirQualityImage(anyString(), anyInt(), anyInt(), anyInt(), anyString());
    }
}