package it.unimib.greenway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static it.unimib.greenway.util.Constants.ADDING_FRIEND_ERROR;
import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_ALL_USERS;
import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_USER_INFO;
import static it.unimib.greenway.util.Constants.REMOVE_FRIEND_ERROR;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.unimib.greenway.data.repository.user.UserRepository;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.StatusChallenge;
import it.unimib.greenway.model.User;
import it.unimib.greenway.ui.UserViewModel;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest=Config.NONE)
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

    @Test
    public void getUserData_failure() {
        // Prepara il MutableLiveData e il risultato di errore
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        Result.Error errorResult = new Result.Error(ERROR_RETRIEVING_USER_INFO);
        liveData.setValue(errorResult);

        when(userRepository.getUser(anyString()))
                .thenReturn(liveData);

        userViewModel.getUserDataMutableLiveData("userId")
                .observeForever(result -> {
                    assertFalse(result.isSuccessUser());
                });
    }

    @Test
    public void logout_success() {
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        Result.UserResponseSuccess successResult = new Result.UserResponseSuccess(new User("", "", "", "", "", "", 0,0,0,0,0,0,0,0,0, null, null, 0)); // Puoi personalizzare il successResult se necessario
        liveData.setValue(successResult);

        when(userViewModel.logout()).thenReturn(liveData);

        userViewModel.logout().observeForever(result -> {
            assertTrue(result.isSuccessUser());
            User user = ((Result.UserResponseSuccess) result).getData();
            assertEquals(successResult.getData(), user);
        });
    }

    @Test
    public void getFriends_success() {
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        List<User> mockFriends = Arrays.asList(new User("friend1", "email1", "name1"), new User("friend2", "email2", "name2"));
        Result.FriendResponseSuccess successResult = new Result.FriendResponseSuccess(mockFriends);
        liveData.setValue(successResult);

        when(userRepository.getFriends(anyString())).thenReturn(liveData);

        userViewModel.getFriendsMutableLiveData("userId").observeForever(result -> {
            assertTrue(result.isSuccessFriends());
            assertEquals(mockFriends, ((Result.FriendResponseSuccess) result).getData());
        });
        // Verifica che il metodo getFriends sia stato chiamato esattamente una volta
        verify(userRepository, times(1)).getFriends(anyString());

        // Verifica che il LiveData abbia osservatori
        assertTrue(liveData.hasObservers());
    }

    @Test
    public void getFriends_error() {
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        Result.Error errorResult = new Result.Error(ERROR_RETRIEVING_ALL_USERS);
        liveData.setValue(errorResult);

        when(userRepository.getFriends(anyString())).thenReturn(liveData);

        userViewModel.getFriendsMutableLiveData("userId").observeForever(result -> {
            assertFalse(result.isSuccessFriends());
            assertEquals(ERROR_RETRIEVING_ALL_USERS, ((Result.Error) result).getMessage());
        });
    }

    @Test
    public void getAllUser_success() {
        // Creazione e Configurazione del LiveData
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        List<User> mockUsers = Arrays.asList(new User("user1", "email1", "password1"), new User("user2", "email2", "password2"));
        Result.AllUserResponseSuccess successResult = new Result.AllUserResponseSuccess(mockUsers);
        liveData.setValue(successResult);

        // Mock del UserRepository
        when(userRepository.getAllUsers(anyString())).thenReturn(liveData);

        // Osservazione del LiveData e Verifica del Risultato
        userViewModel.getAllUser("userId").observeForever(result -> {
            assertTrue(result.isSuccessAllUsers());
            List<User> users = ((Result.AllUserResponseSuccess) result).getData();
            assertEquals(mockUsers, users);

            // Verifica il numero di utenti
            assertEquals(2, users.size());

            // Verifica i dettagli del primo utente
            assertEquals("user1", users.get(0).getUserId());
            assertEquals("email1", users.get(0).getEmail());
            assertEquals("password1", users.get(0).getPassword());

            // Verifica i dettagli del secondo utente
            assertEquals("user2", users.get(1).getUserId());
            assertEquals("email2", users.get(1).getEmail());
            assertEquals("password2", users.get(1).getPassword());
        });

        // Verifica che il metodo getAllUser sia stato chiamato esattamente una volta
        verify(userRepository, times(1)).getAllUsers(anyString());

        // Verifica che il LiveData abbia osservatori
        assertTrue(liveData.hasObservers());
    }

    @Test
    public void getAllUser_failure() {
        // Creazione e Configurazione del LiveData
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        Result.Error errorResult = new Result.Error(ERROR_RETRIEVING_ALL_USERS);
        liveData.setValue(errorResult);

        // Mock del UserRepository
        when(userRepository.getAllUsers(anyString())).thenReturn(liveData);

        // Osservazione del LiveData e Verifica del Risultato
        userViewModel.getAllUser("userId").observeForever(result -> {
            assertTrue(result.isError());
            String errorMessage = ((Result.Error) result).getMessage();
            assertEquals(ERROR_RETRIEVING_ALL_USERS, errorMessage);
        });

        // Verifica che il metodo getAllUser sia stato chiamato esattamente una volta
        verify(userRepository, times(1)).getAllUsers(anyString());

        // Verifica che il LiveData abbia osservatori
        assertTrue(liveData.hasObservers());
    }

    @Test
    public void addFriend_failure() {
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        Result.Error errorResult = new Result.Error(ADDING_FRIEND_ERROR);
        liveData.setValue(errorResult);

        when(userRepository.addFriend(anyString(), anyString())).thenReturn(liveData);

        userViewModel.addFriendMutableLiveData("userId", "friendId").observeForever(result -> {
            assertTrue(result.isError());
            String errorMessage = ((Result.Error) result).getMessage();
            assertEquals(ADDING_FRIEND_ERROR, errorMessage);
        });

        assertTrue(liveData.hasObservers());
    }

    @Test
    public void removeFriend_failure() {
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        Result.Error errorResult = new Result.Error(REMOVE_FRIEND_ERROR);
        liveData.setValue(errorResult);

        when(userRepository.removeFriend(anyString(), anyString())).thenReturn(liveData);

        userViewModel.removeFriendMutableLiveData("userId", "friendId").observeForever(result -> {
            assertTrue(result.isError());
            String errorMessage = ((Result.Error) result).getMessage();
            assertEquals(REMOVE_FRIEND_ERROR, errorMessage);
        });

        assertTrue(liveData.hasObservers());
    }


}