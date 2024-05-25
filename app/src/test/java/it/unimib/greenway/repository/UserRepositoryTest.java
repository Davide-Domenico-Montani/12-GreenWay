package it.unimib.greenway.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import it.unimib.greenway.data.repository.user.UserRepository;
import it.unimib.greenway.data.source.airQuality.BaseAirQualityLocalDataSource;
import it.unimib.greenway.data.source.user.BaseUserAuthenticationRemoteDataSource;
import it.unimib.greenway.data.source.user.BaseUserDataRemoteDataSource;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.StatusChallenge;
import it.unimib.greenway.model.User;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

    @Mock
    private BaseUserAuthenticationRemoteDataSource userRemoteDataSource;

    @Mock
    private BaseUserDataRemoteDataSource userDataRemoteDataSource;

    @Mock
    private BaseAirQualityLocalDataSource airQualityLocalDataSource;

    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = new UserRepository(userRemoteDataSource, userDataRemoteDataSource, airQualityLocalDataSource);
    }

    @Test
    public void testGetGoogleUser() {
        // Arrange
        String idToken = "testToken";
        List<StatusChallenge> statusChallengeList = new ArrayList<>();
        Result expected = new Result.UserResponseSuccess(new User());

        // Act
        MutableLiveData<Result> result = userRepository.getGoogleUser(idToken, statusChallengeList);

        // Assert
        verify(userRemoteDataSource).signInWithGoogle(idToken, statusChallengeList);
        assertEquals(expected, result.getValue());
    }

    @Test
    public void testRegisterUser() {
        // Arrange
        String nome = "testNome";
        String cognome = "testCognome";
        String email = "testEmail";
        String password = "testPassword";
        List<StatusChallenge> statusChallengeList = new ArrayList<>();
        Result expected = new Result.UserResponseSuccess(new User());

        // Act
        MutableLiveData<Result> result = userRepository.registerUser(nome, cognome, email, password, statusChallengeList);

        // Assert
        verify(userRemoteDataSource).signUp(nome, cognome, email, password, statusChallengeList);
        assertEquals(expected, result.getValue());
    }

    @Test
    public void testLoginUser() {
        // Arrange
        String email = "testEmail";
        String password = "testPassword";
        Result expected = new Result.UserResponseSuccess(new User());

        // Act
        MutableLiveData<Result> result = userRepository.loginUser(email, password);

        // Assert
        verify(userRemoteDataSource).login(email, password);
        assertEquals(expected, result.getValue());
    }

    @Test
    public void testGetUserData() {
        // Arrange
        String email = "testEmail";
        String password = "testPassword";
        Result expected = new Result.UserResponseSuccess(new User());

        // Act
        MutableLiveData<Result> result = userRepository.getUserData(email, password);

        // Assert
        verify(userRemoteDataSource).getUserCredential(email, password);
        assertEquals(expected, result.getValue());
    }

    @Test
    public void testGetUser() {
        // Arrange
        String idToken = "testToken";
        Result expected = new Result.UserResponseSuccess(new User());

        // Act
        MutableLiveData<Result> result = userRepository.getUser(idToken);

        // Assert
        verify(userDataRemoteDataSource).getUserInfo(idToken);
        assertEquals(expected, result.getValue());
    }

    // Add more tests for other methods in UserRepository
}