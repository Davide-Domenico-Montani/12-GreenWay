package it.unimib.greenway.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import it.unimib.greenway.data.repository.routes.RoutesRepositoryWithLiveData;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.ui.main.RoutesViewModel;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class RoutesViewModelTest {

    private RoutesRepositoryWithLiveData repository;
    private RoutesViewModel viewModel;

    @Before
    public void setUp() {
        repository = mock(RoutesRepositoryWithLiveData.class);
        viewModel = new RoutesViewModel(repository);
    }

    @Test
    public void testGetRoutes_whenLiveDataIsNull_fetchesFromRepository() {
        // Arrange
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        when(repository.fetchRoutes(0.0, 0.0, 0.0, 0.0, 0.0)).thenReturn(liveData);

        // Act
        MutableLiveData<Result> result = viewModel.getRoutes(0.0, 0.0, 0.0, 0.0, 0.0);

        // Assert
        assertNotNull(result);
        assertEquals(result, liveData);
    }

    @Test
    public void testFetchRoutes_fetchesFromRepository() {
        // Arrange
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        when(repository.fetchRoutes(0.0, 0.0, 0.0, 0.0, 0.0)).thenReturn(liveData);

        // Act
        viewModel.fetchRoutes(0.0, 0.0, 0.0, 0.0, 0.0);
        MutableLiveData<Result> result = viewModel.getRoutes(0.0, 0.0, 0.0, 0.0, 0.0);

        // Assert
        assertNotNull(result);
        assertEquals(result, liveData);
    }
}