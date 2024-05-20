package it.unimib.greenway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;
import java.util.List;

import it.unimib.greenway.data.repository.user.UserRepository;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.StatusChallenge;
import it.unimib.greenway.model.User;
import it.unimib.greenway.ui.UserViewModel;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class UserViewModelTest {

    @Mock
    private UserRepository userRepository;

    private UserViewModel userViewModel;
    private List<StatusChallenge> statusChallengeList;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out; // Per vedere i log di Robolectric nel terminale
        Application application = mock(Application.class);
        userRepository = mock(UserRepository.class);
        userViewModel = new UserViewModel(userRepository);
        statusChallengeList = new ArrayList<>();

    }

    @Test
    public void loginUser_success() {
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        Result.UserResponseSuccess successResult = new Result.UserResponseSuccess(new User("userId", "email", "name"));
        liveData.setValue(successResult);

        when(userRepository.loginUser(anyString(), anyString())).thenReturn(liveData);

        userViewModel.loginUserMutableLiveData("email", "password").observeForever(result -> {
            assertTrue(result.isSuccessUser());
            assertEquals("userId", ((Result.UserResponseSuccess) result).getData().getUserId());
        });
    }

    @Test
    public void loginUser_failure() {
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        Result.Error errorResult = new Result.Error("Login failed");
        liveData.setValue(errorResult);

        when(userRepository.loginUser(anyString(), anyString())).thenReturn(liveData);

        userViewModel.loginUserMutableLiveData("email", "password").observeForever(result -> {
            assertFalse(result.isSuccessUser());
            assertEquals("Login failed", ((Result.Error) result).getMessage());
        });
    }

    @Test
    public void registerUser_success() {
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        Result.UserResponseSuccess successResult = new Result.UserResponseSuccess(new User("userId", "email", "name"));
        liveData.setValue(successResult);

        when(userRepository.registerUser(anyString(), anyString(), anyString(), anyString(), anyList()))
                .thenReturn(liveData);

        userViewModel.registerUserMutableLiveData("Nome", "Cognome", "Email", "Password", statusChallengeList)
                .observeForever(result -> {
                    assertTrue(result.isSuccessUser());
                });
    }

    @Test
    public void registerUser_failure() {
        // Prepara il MutableLiveData e il risultato di errore
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        Result.Error errorResult = new Result.Error("Registration failed");
        liveData.setValue(errorResult);

        when(userRepository.registerUser(anyString(), anyString(), anyString(), anyString(), anyList()))
                .thenReturn(liveData);

        userViewModel.registerUserMutableLiveData("Nome", "Cognome", "Email", "Password", statusChallengeList)
                .observeForever(result -> {
                    assertFalse(result.isSuccessUser());
                });
    }

    @Test
    public void getUserData_success() {
        // Creare un utente di esempio con ID noto
        User expectedUser = new User("userId", "email", "name");

        // Simulare il risultato di successo dalla repository
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        Result.UserResponseSuccess successResult = new Result.UserResponseSuccess(expectedUser);
        liveData.setValue(successResult);
        when(userRepository.getUser(anyString())).thenReturn(liveData);

        // Osservare il LiveData del ViewModel per il risultato
        userViewModel.getUserDataMutableLiveData("userId").observeForever(result -> {
            User user = ((Result.UserResponseSuccess) result).getData();
            // Verificare che il risultato sia un successo
            assertNotNull(user);

            assertTrue(result.isSuccessUser());
            // Verificare che l'utente restituito sia lo stesso di quello atteso
            assertEquals(expectedUser, user);
        });
    }






}