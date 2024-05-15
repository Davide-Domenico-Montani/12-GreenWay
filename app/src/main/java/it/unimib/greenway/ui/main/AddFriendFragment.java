package it.unimib.greenway.ui.main;

import static it.unimib.greenway.util.Constants.DRIVE_CONSTANT;
import static it.unimib.greenway.util.Constants.TRANSIT_CONSTANT;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.greenway.R;
import it.unimib.greenway.adapter.AddFriendRecyclerViewAdapter;
import it.unimib.greenway.adapter.RecylclerViewClickListener;
import it.unimib.greenway.adapter.RoutesRecyclerViewAdapter;
import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.Route;
import it.unimib.greenway.model.User;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.UserViewModelFactory;
import it.unimib.greenway.util.ServiceLocator;


public class AddFriendFragment extends Fragment implements RecylclerViewClickListener{

   private UserViewModel userViewModel;
   private RecyclerView recyclerView;
   private AddFriendRecyclerViewAdapter addFriendRecyclerViewAdapter;
   private List<User> userList;
    private RecyclerView.LayoutManager layoutManager;
    private RecylclerViewClickListener listener;
    private ImageButton backButton;
    private FragmentManager fragmentManager;
    private SearchView searchView;
    private  String previousText;
    private List<User> friendsListBackUp;


    public AddFriendFragment() {
        // Required empty public constructor
    }


    public static AddFriendFragment newInstance(String param1, String param2) {
        AddFriendFragment fragment = new AddFriendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (RecylclerViewClickListener) this;
        userList = new ArrayList<>();
        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                this,
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        backButton = requireActivity().findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        fragmentManager = requireActivity().getSupportFragmentManager();

        return inflater.inflate(R.layout.fragment_add_friend, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewAddFriend);
        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();

        previousText = "";


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // Aggiorna la stringa precedente con quella corrente
                // Esegui il filtro degli amici con il nuovo testo
                filterFriends(newText);
                previousText = newText;

                return true;
            }
        });

        layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);

        userViewModel.getUserDataMutableLiveData(userViewModel.getLoggedUser().getUserId()).observe(
                getViewLifecycleOwner(), result -> {
            if (result.isSuccessUser()) {
                User user = ((Result.UserResponseSuccess) result).getData();
                addFriendRecyclerViewAdapter = new AddFriendRecyclerViewAdapter(userList,
                        requireActivity().getApplication(),
                        listener, user);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(addFriendRecyclerViewAdapter);

            }
        });


        userViewModel.getAllUser(userViewModel.getLoggedUser().getUserId()).observe(
                getViewLifecycleOwner(), result -> {
            if (result.isSuccessAllUsers()) {
                this.userList.clear();
                this.userList.addAll(((Result.AllUserResponseSuccess) result).getData());
                addFriendRecyclerViewAdapter.notifyDataSetChanged();

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

    }


    @Override
    public void onClick(String userId, boolean checked){
        if(!checked){
            userViewModel.removeFriend(userViewModel.getLoggedUser().getUserId(), userId);
        }else{
            userViewModel.addFriend(userViewModel.getLoggedUser().getUserId(), userId);
        }

    }

    @Override
    public void onClickMaps(Route route) {

    }

    @Override
    public void onClick(Route route) {

    }

    public void filterFriends(String newText){
        if(friendsListBackUp == null) {
            friendsListBackUp = new ArrayList<>();
            friendsListBackUp.addAll(userList);
        }
        Log.d("Stampa", "Size: " + friendsListBackUp.size());
        List<User> filteredList = new ArrayList<>();

        // Altrimenti, filtra la lista in base al testo
        for(User friend : friendsListBackUp){
            if(friend.getName().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(friend);
            }
        }
        addFriendRecyclerViewAdapter.filterList(filteredList);
    }
}