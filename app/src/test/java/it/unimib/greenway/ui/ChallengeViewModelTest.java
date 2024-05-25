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

import java.util.ArrayList;
import java.util.List;

import it.unimib.greenway.data.repository.challenge.ChallengeRepositoryWithLiveData;
import it.unimib.greenway.model.Challenge;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.ui.main.ChallengeViewModel;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ChallengeViewModelTest {

    private ChallengeRepositoryWithLiveData repository;
    private ChallengeViewModel viewModel;

    @Before
    public void setUp() {
        repository = mock(ChallengeRepositoryWithLiveData.class);
        viewModel = new ChallengeViewModel(repository);
    }

    @Test
    public void testGetChallenge_whenLiveDataIsNull_fetchesFromRepository() {
        // Arrange
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        when(repository.getChallenge()).thenReturn(liveData);

        // Act
        MutableLiveData<Result> result = viewModel.getChallengeMutableLiveData();

        // Assert
        assertNotNull(result);
        assertEquals(result, liveData);
    }

    @Test
    public void testGetChallenge_fetchesFromRepository() {
        // Arrange
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        when(repository.getChallenge()).thenReturn(liveData);

        // Act
        viewModel.getChallenge();
        MutableLiveData<Result> result = viewModel.getChallengeMutableLiveData();

        // Assert
        assertNotNull(result);
        assertEquals(result, liveData);
    }
}