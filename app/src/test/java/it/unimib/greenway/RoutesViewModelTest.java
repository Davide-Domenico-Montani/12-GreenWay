package it.unimib.greenway;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import it.unimib.greenway.data.repository.routes.IRoutesRepositoryWithLiveData;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.Route;
import it.unimib.greenway.model.RoutesResponse;
import it.unimib.greenway.ui.main.RoutesViewModel;

@RunWith(RobolectricTestRunner.class)
public class RoutesViewModelTest {

    @Mock
    IRoutesRepositoryWithLiveData routesRepositoryWithLiveData;

    @Mock
    Observer<Result> observer;

    @InjectMocks
    RoutesViewModel routesViewModel;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        routesViewModel = new RoutesViewModel(routesRepositoryWithLiveData);
    }

    @Test
    public void testGetRoutes_success() {
        // Prepare test data
        double startLat = 40.748817;
        double startLng = -73.985428;
        double destLat = 34.052235;
        double destLng = -118.243683;
        double defaultCo2 = 0.2;

        List<Route> testRoutes = new ArrayList<>();
        Route route1 = new Route("drive", 10000, "3600s", null, new LatLng(startLat, startLng), new LatLng(destLat, destLng), new ArrayList<>(), defaultCo2);
        Route route2 = new Route("transit", 20000, "7200s", null, new LatLng(startLat, startLng), new LatLng(destLat, destLng), new ArrayList<>(), defaultCo2);
        Route route3 = new Route("walk", 3000, "10800s", null, new LatLng(startLat, startLng), new LatLng(destLat, destLng), new ArrayList<>(), defaultCo2);
        testRoutes.add(route1);
        testRoutes.add(route2);
        testRoutes.add(route3);

        Result.RouteResponseSuccess successResult = new Result.RouteResponseSuccess(new RoutesResponse(testRoutes));

        MutableLiveData<Result> liveData = new MutableLiveData<>();
        liveData.setValue(successResult);

        when(routesRepositoryWithLiveData.fetchRoutes(startLat, startLng, destLat, destLng, defaultCo2)).thenReturn(liveData);

        // Observe the LiveData
        routesViewModel.getRoutes(startLat, startLng, destLat, destLng, defaultCo2).observeForever(observer);

        // Verify the results
        verify(observer).onChanged(successResult);

        // Verify the route data
        assertEquals(3, successResult.getData().getRoutes().size());

        Route driveRoute = successResult.getData().getRoutes().get(0);
        Route transitRoute = successResult.getData().getRoutes().get(1);
        Route walkRoute = successResult.getData().getRoutes().get(2);

        assertEquals("drive", driveRoute.getTravelMode());
        assertEquals(10000, driveRoute.getDistanceMeters());
        assertEquals("3600s", driveRoute.getStaticDuration());
        assertEquals(defaultCo2, driveRoute.getCo2(), 0.0);

        assertEquals("transit", transitRoute.getTravelMode());
        assertEquals(20000, transitRoute.getDistanceMeters());
        assertEquals("7200s", transitRoute.getStaticDuration());
        assertEquals(defaultCo2, transitRoute.getCo2(), 0.0);

        assertEquals("walk", walkRoute.getTravelMode());
        assertEquals(3000, walkRoute.getDistanceMeters());
        assertEquals("10800s", walkRoute.getStaticDuration());
        assertEquals(defaultCo2, walkRoute.getCo2(), 0.0);
    }

    @Test
    public void testGetRoutes_error() {
        // Prepare test data
        double startLat = 40.748817;
        double startLng = -73.985428;
        double destLat = 34.052235;
        double destLng = -118.243683;
        double defaultCo2 = 0.2;

        Result.Error errorResult = new Result.Error("Error occurred");

        MutableLiveData<Result> liveData = new MutableLiveData<>();
        liveData.setValue(errorResult);

        when(routesRepositoryWithLiveData.fetchRoutes(startLat, startLng, destLat, destLng, defaultCo2)).thenReturn(liveData);

        // Observe the LiveData
        routesViewModel.getRoutes(startLat, startLng, destLat, destLng, defaultCo2).observeForever(observer);

        // Verify the results
        verify(observer).onChanged(errorResult);
        // Add your snackbar verification logic here if needed
    }
}
