package it.unimib.greenway.ui.main;

import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_ALL_USERS;
import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_CHALLENGE;
import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_FRIENDS;
import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_USER_INFO;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.unimib.greenway.R;
import it.unimib.greenway.adapter.ChallengeRecyclerViewAdapter;
import it.unimib.greenway.adapter.FriendsRecyclerViewAdapter;
import it.unimib.greenway.adapter.RoutesRecyclerViewAdapter;
import it.unimib.greenway.data.repository.challenge.IChallengeRepositoryWithLiveData;
import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.model.Challenge;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.Route;
import it.unimib.greenway.model.User;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.UserViewModelFactory;
import it.unimib.greenway.util.ServiceLocator;


public class ChallengeFragment extends Fragment {

    private ChallengeViewModel challengeViewModel;

    private ImageButton backButton;
    private List<Challenge> challengeList;
    private List<User> friendsList;
    private RecyclerView recyclerViewChallenge;
    private RecyclerView.Adapter currentAdapter;
    private FriendsRecyclerViewAdapter friendsRecyclerViewAdapter;
    private ChallengeRecyclerViewAdapter challengeRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private UserViewModel userViewModel;
    private TabLayout tabLayout;

    private FloatingActionButton floatingActionButton;

    public ChallengeFragment() {
        // Required empty public constructor
    }

    public static ChallengeFragment newInstance() {
        ChallengeFragment fragment = new ChallengeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        challengeList = new ArrayList<>();
        friendsList = new ArrayList<>();

        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                this,
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        IChallengeRepositoryWithLiveData challengeRepositoryWithLiveData =
                ServiceLocator.getInstance().getChallengeRepository(
                        requireActivity().getApplication()
                );
        challengeViewModel = new ViewModelProvider(this, new ChallengeViewModelFactory(challengeRepositoryWithLiveData)).get(ChallengeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenge, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        backButton = requireActivity().findViewById(R.id.backButton);

        backButton.setVisibility(View.INVISIBLE);


        recyclerViewChallenge = view.findViewById(R.id.recyclerViewChallengeFriends);
        tabLayout = view.findViewById(R.id.tabLayoutChallenge);
        floatingActionButton = view.findViewById(R.id.floating_action_button);
        floatingActionButton.hide();

        layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);

        friendsRecyclerViewAdapter = new FriendsRecyclerViewAdapter(friendsList,
                requireActivity().getApplication(),
                new FriendsRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onFriendItemClick(User friend) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("friend", friend);
                        Log.d("Friend", friend.toString());
                        Navigation.findNavController(view).navigate(R.id.action_challengeFragment_to_friendFragment, bundle);
                    }
                });


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if(position == 0){
                    currentAdapter = challengeRecyclerViewAdapter;
                    floatingActionButton.hide();
                    recyclerViewChallenge.setLayoutManager(new GridLayoutManager(getContext(), 1));
                }else{

                    currentAdapter = friendsRecyclerViewAdapter;
                    floatingActionButton.show();
                    recyclerViewChallenge.setLayoutManager(new GridLayoutManager(getContext(), 2));

                }
                recyclerViewChallenge.setAdapter(currentAdapter);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        floatingActionButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_challengeFragment_to_addFriendFragment);

        });
    }
    @Override
    public void onResume() {
        super.onResume();

        userViewModel.getUserDataMutableLiveData(userViewModel.getLoggedUser().getUserId()).observe(getViewLifecycleOwner(), result -> {
            if(result.isSuccessUser()){
                User user = ((Result.UserResponseSuccess) result).getData();


                challengeRecyclerViewAdapter = new ChallengeRecyclerViewAdapter(challengeList,
                        requireActivity().getApplication(), user);
                currentAdapter = challengeRecyclerViewAdapter;
                recyclerViewChallenge.setAdapter(currentAdapter);
                recyclerViewChallenge.setLayoutManager(layoutManager);

                challengeViewModel.getChallengeMutableLiveData().observe(getViewLifecycleOwner(),
                        result2 -> {
                            if(result2.isSuccessChallenge()){
                                this.challengeList.clear();
                                this.challengeList.addAll(((Result.ChallengeResponseSuccess) result2).getData().getChallenges());
                                challengeRecyclerViewAdapter.notifyDataSetChanged();

                            }else if(result2.isError()){
                                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                        getErrorMessage(((Result.Error) result2).getMessage()),
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        });

            }else if(result.isError()){
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        getErrorMessage(((Result.Error) result).getMessage()),
                        Snackbar.LENGTH_SHORT).show();
            }
        });

        userViewModel.getFriendsMutableLiveData(userViewModel.getLoggedUser().getUserId()).observe(
                getViewLifecycleOwner(), result -> {
                    if (result.isSuccessFriends()) {
                        List<User> friends = ((Result.FriendResponseSuccess) result).getData();
                        this.friendsList.clear();
                        this.friendsList.addAll(friends);
                        reorderList(friends);
                        friendsRecyclerViewAdapter.notifyDataSetChanged();

                    }else if(result.isError()){
                        Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                getErrorMessage(((Result.Error) result).getMessage()),
                                Snackbar.LENGTH_SHORT).show();
                    }
                });

    }



    private String getErrorMessage(String errorType) {
        switch (errorType) {
            case ERROR_RETRIEVING_CHALLENGE:
                return requireActivity().getString(R.string.error_retrieving_challenge);
            case ERROR_RETRIEVING_USER_INFO:
                return requireActivity().getString(R.string.error_retrieving_user_info);
            case ERROR_RETRIEVING_FRIENDS:
                return requireActivity().getString(R.string.error_retrieving_friends);
            case ERROR_RETRIEVING_ALL_USERS:
                return requireActivity().getString(R.string.error_retrieving_all_users);
            default:
                return requireActivity().getString(R.string.unexpected_error);
        }
    }

    public void reorderList(List<User> friendsList) {
        Comparator<User> comparator = new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return Double.compare(user1.getPoint(), user2.getPoint());
            }
        };

        Collections.sort(friendsList, comparator);
    }
}