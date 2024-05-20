package it.unimib.greenway;


import static com.google.common.base.Verify.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_CHALLENGE;

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
import java.util.List;

import it.unimib.greenway.data.repository.challenge.ChallengeRepositoryWithLiveData;
import it.unimib.greenway.data.repository.user.UserRepository;
import it.unimib.greenway.model.Challenge;
import it.unimib.greenway.model.ChallengeResponse;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.main.ChallengeViewModel;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest=Config.NONE)
public class ChallengeViewModelTest {

    @Mock
    ChallengeRepositoryWithLiveData challengeRepositoryWithLiveData;

    private ChallengeViewModel challengeViewModel;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out; // Per vedere i log di Robolectric nel terminale
        Application application = mock(Application.class);
        challengeRepositoryWithLiveData = mock(ChallengeRepositoryWithLiveData.class);
        challengeViewModel = new ChallengeViewModel(challengeRepositoryWithLiveData);
    }

    @Test
    public void getChallenge_success() {
        // Creazione e configurazione del LiveData
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        Challenge mockChallenge = new Challenge(0, "challengeName", 100, 20.0, true, false, true, false, false, false);
        List<Challenge> mockChallengeList = new ArrayList<>();
        mockChallengeList.add(mockChallenge);
        Result.ChallengeResponseSuccess successResult = new Result.ChallengeResponseSuccess(new ChallengeResponse(mockChallengeList));
        liveData.setValue(successResult);

        // Mock del ChallengeRepository
        when(challengeRepositoryWithLiveData.getChallenge()).thenReturn(liveData);

        // Osservazione del LiveData e verifica del risultato
        challengeViewModel.getChallengeMutableLiveData().observeForever(result -> {
            assertTrue(result.isSuccessChallenge());
            assertEquals(mockChallenge, ((Result.ChallengeResponseSuccess) result).getData().getChallenges().get(0));
        });

        // Verifica che il LiveData abbia osservatori
        assertTrue(liveData.hasObservers());
    }


    @Test
    public void getChallenge_failure() {
        // Creazione e configurazione del LiveData
        MutableLiveData<Result> liveData = new MutableLiveData<>();
        Result.Error errorResult = new Result.Error(ERROR_RETRIEVING_CHALLENGE);
        liveData.setValue(errorResult);

        // Mock del ChallengeRepository
        when(challengeRepositoryWithLiveData.getChallenge()).thenReturn(liveData);

        // Osservazione del LiveData e verifica del risultato
        challengeViewModel.getChallengeMutableLiveData().observeForever(result -> {
            assertTrue(result.isError());
            String errorMessage = ((Result.Error) result).getMessage();
            assertEquals(ERROR_RETRIEVING_CHALLENGE, errorMessage);
        });

        // Verifica che il LiveData abbia osservatori
        assertTrue(liveData.hasObservers());
    }
}
