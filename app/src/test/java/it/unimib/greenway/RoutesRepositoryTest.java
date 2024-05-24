package it.unimib.greenway;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import android.os.Looper;

import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import it.unimib.greenway.data.repository.routes.RoutesRepositoryWithLiveData;
import it.unimib.greenway.data.source.routes.BaseRoutesRemoteDataSource;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.Route;
import it.unimib.greenway.model.RoutesResponse;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class RoutesRepositoryTest {

    private RoutesRepositoryWithLiveData repository;
    private BaseRoutesRemoteDataSource dataSource;

    @Before
    public void setUp() {
        dataSource = mock(BaseRoutesRemoteDataSource.class);
        repository = new RoutesRepositoryWithLiveData(dataSource);
        dataSource.setRoutesCallBack(repository);
    }

    @Test
    public void testFetchRoutes_fetchesFromRepository() throws InterruptedException {
        // Arrange
        List<Route> routes = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        doAnswer(invocation -> {
            repository.onSuccessFromRemote(routes);
            latch.countDown();
            return null;
        }).when(dataSource).getRoutes(1.0, 1.0, 2.0, 2.0, 1.0);

        // Act
        repository.fetchRoutes(1.0, 1.0, 2.0, 2.0, 1.0);
        latch.await(2, TimeUnit.SECONDS);  // wait for onSuccessFromRemote to be called

        // Let any queued tasks on the main looper run
        Shadows.shadowOf(Looper.getMainLooper()).idle();

        // Assert
        Result expected = new Result.RouteResponseSuccess(new RoutesResponse(routes));
        assertEquals(expected, repository.fetchRoutes(0, 0, 0, 0, 0).getValue());
    }
}