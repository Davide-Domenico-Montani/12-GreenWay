package it.unimib.greenway.source;

import it.unimib.greenway.data.database.AirQualityDao;
import it.unimib.greenway.data.database.AirQualityDatabase;
import it.unimib.greenway.data.source.airQuality.AirQualityLocalDataSource;
import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.util.SharedPreferencesUtil;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

@RunWith(MockitoJUnitRunner.class)
@Config(manifest=Config.NONE)
public class AirQualityLocalDataSourceTest {

    @Mock
    private AirQualityDao airQualityDao;

    @Mock
    private AirQualityDatabase airQualityDatabase;

    private AirQualityLocalDataSource airQualityLocalDataSource;

    @Before
    public void setup() {
        AirQualityDatabase airQualityDatabase = mock(AirQualityDatabase.class);
        SharedPreferencesUtil sharedPreferencesUtil = mock(SharedPreferencesUtil.class);
        airQualityDao = mock(AirQualityDao.class);

        when(airQualityDatabase.airQualityDao()).thenReturn(airQualityDao);

        airQualityLocalDataSource = new AirQualityLocalDataSource(airQualityDatabase, sharedPreferencesUtil);
    }

    @Test
    public void testInsertAirQuality() throws InterruptedException {
        List<AirQuality> airQualityList = new ArrayList<>();
        airQualityList.add(mock(AirQuality.class));
        airQualityList.add(mock(AirQuality.class));

        CountDownLatch latch = new CountDownLatch(1);

        doAnswer(invocation -> {
            latch.countDown();
            return null;
        }).when(airQualityDao).insertAirQuality(any(AirQuality.class));

        airQualityLocalDataSource.insertAirQuality(airQualityList);

        latch.await();  // Wait until insertAirQuality has been called

        for (AirQuality airQuality : airQualityList) {
            verify(airQualityDao, times(1)).insertAirQuality(airQuality);
        }
    }

    @Test
    public void testGetAirQuality() {
        airQualityLocalDataSource.getAirQuality();
        verify(airQualityDao, times(1)).getAll();
    }

    @Test
    public void testGetAirQualityList() throws ExecutionException, InterruptedException {
        airQualityLocalDataSource.getAirQualityList();
        verify(airQualityDao, times(1)).getAll();
    }
}