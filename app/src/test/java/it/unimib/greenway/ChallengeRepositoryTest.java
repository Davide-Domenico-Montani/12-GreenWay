package it.unimib.greenway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import it.unimib.greenway.data.repository.challenge.ChallengeRepositoryWithLiveData;
import it.unimib.greenway.data.source.challenge.BaseChallengeRemoteDataSource;
import it.unimib.greenway.data.source.challenge.ChallengeCallBack;
import it.unimib.greenway.model.ChallengeResponse;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.Challenge;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ChallengeRepositoryTest {

    private ChallengeRepositoryWithLiveData repository;
    private BaseChallengeRemoteDataSource dataSource;

    @Before
    public void setUp() {
        dataSource = mock(BaseChallengeRemoteDataSource.class);
        repository = new ChallengeRepositoryWithLiveData(dataSource);
        dataSource.setChallengeCallBack(repository);
    }


    @Test
    public void testGetChallenge_fetchesFromRepository() throws InterruptedException {
        // Arrange
        List<Challenge> challenges = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        doAnswer(invocation -> {
            repository.onSuccessFromRemote(challenges);
            latch.countDown();
            return null;
        }).when(dataSource).getChallenge();

        // Act
        repository.getChallenge();
        latch.await(2, TimeUnit.SECONDS);  // wait for onSuccessFromRemote to be called

        // Let any queued tasks on the main looper run
        Shadows.shadowOf(Looper.getMainLooper()).idle();

        // Assert
        Result expected = new Result.ChallengeResponseSuccess(new ChallengeResponse(challenges));
        assertEquals(expected, repository.getChallenge().getValue());
    }
}