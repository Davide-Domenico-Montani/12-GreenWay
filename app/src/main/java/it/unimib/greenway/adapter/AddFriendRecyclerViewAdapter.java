package it.unimib.greenway.adapter;

import static it.unimib.greenway.util.Constants.URI_STRING_MAPS;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

import it.unimib.greenway.R;
import it.unimib.greenway.model.Route;
import it.unimib.greenway.model.User;
import it.unimib.greenway.util.ConverterUtil;

public class AddFriendRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<User> friendList;
    private final Application application;
    private final RecylclerViewClickListener mlistener;
    private final User loggedUser;


    public AddFriendRecyclerViewAdapter(List<User> friendList, Application application,
                                      RecylclerViewClickListener listener, User loggedUser) {
        this.friendList = friendList;

        this.application = application;
        mlistener = listener;
        this.loggedUser = loggedUser;
    }


    @Override
    public int getItemViewType(int position) {
        if (friendList.get(position) == null) {
            return 1;
        } else {
            return 0;
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.add_friend_card_item, parent, false);
        return new AddFriendRecyclerViewAdapter.AddFriendViewHolder(view, mlistener);    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AddFriendRecyclerViewAdapter.AddFriendViewHolder) {
            ((AddFriendRecyclerViewAdapter.AddFriendViewHolder) holder).bind(friendList.get(position));

        }
    }

    @Override
    public int getItemCount() {
        if (friendList != null) {
            return friendList.size();
        }
        return 0;
    }

    public void filterList(List<User> filteredList) {
        friendList.clear();
        friendList.addAll(filteredList);
        notifyDataSetChanged();
    }

    public class AddFriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context context;

        private final RecylclerViewClickListener mlistener;
        private final ImageView profileImage;
        private final TextView userName, userPoint;
        private final MaterialCheckBox checkBox;
        public AddFriendViewHolder(@NonNull View itemView, RecylclerViewClickListener listener) {
            super(itemView);
            mlistener = listener;
            profileImage = itemView.findViewById(R.id.addFriendImage);
            context = itemView.getContext();
            userName = itemView.findViewById(R.id.addFriendName);
            userPoint = itemView.findViewById(R.id.addFriendPoint);
            checkBox = itemView.findViewById(R.id.addFriendCheckBox);
            itemView.setOnClickListener(this);
        }

        public void bind(User user) {
            profileImage.setImageResource(R.drawable.icon_user);
            if(user.getPhotoUrlGoogle() != null && user.getPhotoUrl().equals("")){
                Glide.with(context)
                        .load(user.getPhotoUrlGoogle())
                        .placeholder(R.drawable.icon_user)
                        .error(R.drawable.icon_user)
                        .circleCrop()
                        .into(profileImage);
            }else if(!user.getPhotoUrl().equals("")){
                Glide.with(context)
                        .load(ConverterUtil.stringToBitmap(user.getPhotoUrl()))
                        .error(R.drawable.icon_user)
                        .circleCrop()
                        .into(profileImage);
            }

            userName.setText(user.getName() + " " + user.getSurname());
            userPoint.setText(user.getPoint() + "pt");
            checkBox.setChecked(loggedUser.getIdFriends().contains(user.getUserId()));

            checkBox.setOnClickListener(v -> {
                    mlistener.onClick(user.getUserId(), checkBox.isChecked());
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void clear() {
        friendList.clear();
    }

    public void addAll(List<User> newList) {
        friendList.addAll(newList);
    }

}

