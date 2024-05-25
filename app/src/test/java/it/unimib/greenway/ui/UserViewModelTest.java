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


import it.unimib.greenway.data.repository.user.UserRepository;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.StatusChallenge;
import it.unimib.greenway.ui.UserViewModel;


@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class UserViewModelTest {

    private UserRepository repository;
    private UserViewModel viewModel;

    @Before
    public void setUp() {
        repository = mock(UserRepository.class);
        viewModel = new UserViewModel(repository);
    }

    @Test
    public void testGetGoogleUser_whenLiveDataIsNull_fetchesFromRepository() {
        // Arrange
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        List<StatusChallenge> statusChallengeList = new ArrayList<>();
        when(repository.getGoogleUser("token", statusChallengeList)).thenReturn(liveData);

        // Act
        MutableLiveData<Result> result = viewModel.getGoogleUserMutableLiveData("token", statusChallengeList);

        // Assert
        assertNotNull(result);
        assertEquals(result, liveData);
    }

    @Test
    public void testRegisterUser_whenLiveDataIsNull_fetchesFromRepository() {
        // Arrange
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        List<StatusChallenge> statusChallengeList = new ArrayList<>();
        when(repository.registerUser("nome", "cognome", "email", "password", statusChallengeList)).thenReturn(liveData);

        // Act
        MutableLiveData<Result> result = viewModel.registerUserMutableLiveData("nome", "cognome", "email", "password", statusChallengeList);

        // Assert
        assertNotNull(result);
        assertEquals(result, liveData);
    }

    // Add more tests for other methods in the UserViewModel
}