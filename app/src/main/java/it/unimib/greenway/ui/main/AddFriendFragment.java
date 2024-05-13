package it.unimib.greenway.ui.main;

import static it.unimib.greenway.util.Constants.DRIVE_CONSTANT;
import static it.unimib.greenway.util.Constants.TRANSIT_CONSTANT;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return inflater.inflate(R.layout.fragment_add_friend, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewAddFriend);

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
            if (result.isSuccessFriends()) {
                this.userList.clear();
                this.userList.addAll(((Result.FriendResponseSuccess) result).getData());
                addFriendRecyclerViewAdapter.notifyDataSetChanged();

            }
        });



    }


    @Override
    public void onClick(String userId) {

    }

    @Override
    public void onClick(Route route) {

    }
}