package it.unimib.greenway.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import it.unimib.greenway.R;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.User;
import it.unimib.greenway.util.ConverterUtil;


public class FriendsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<User> friendsList;
    private final Application application;
    private ConverterUtil converterUtil;

    public FriendsRecyclerViewAdapter(List<User> friendsList, Application application) {
        this.friendsList = friendsList;
        this.application = application;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.friend_card_item, parent, false);
        return new FriendsRecyclerViewAdapter.FriendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FriendsRecyclerViewAdapter.FriendsViewHolder){
            ((FriendsRecyclerViewAdapter.FriendsViewHolder) holder).bind(friendsList.get(position));

        }
    }

    @Override
    public int getItemCount() {
        if (friendsList != null) {
            return friendsList.size();
        }
        return 0;
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView friendName, friendPoint, friendCo2Saved;
        private final ImageView friendImage;
        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            friendName= itemView.findViewById(R.id.friendName);
            friendImage = itemView.findViewById(R.id.friendImage);
            friendPoint = itemView.findViewById(R.id.friendPoints);
            friendCo2Saved = itemView.findViewById(R.id.friendCo2Saved);
        }

        @Override
        public void onClick(View v) {

        }

        public void bind(User friend) {
            if(friend.getPhotoUrlGoogle() != null && friend.getPhotoUrl().equals("")){
                Glide.with(application.getApplicationContext())
                        .load(friend.getPhotoUrlGoogle())
                        .placeholder(R.drawable.icon_user)
                        .error(R.drawable.icon_user)
                        .into(friendImage);
            }else if(!friend.getPhotoUrl().equals("")){
                Glide.with(application.getApplicationContext())
                        .load(converterUtil.stringToBitmap(friend.getPhotoUrl()))
                        .error(R.drawable.icon_user)
                        .into(friendImage);
            }
            double co2Saved = friend.getCo2SavedCar() + friend.getCo2SavedTransit() + friend.getCo2SavedWalk();
            friendName.setText(friend.getName() + " " + friend.getSurname());
            friendPoint.setText(friend.getPoint() + "pt");
            String format = "%." + 3 + "f";
            String formattedString = String.format(format,co2Saved);
            friendCo2Saved.setText(formattedString + "kg");

        }
    }

    public void clear() {
        friendsList.clear();
    }

    public void addAll(List<User> newList) {
        friendsList.addAll(newList);
    }
}
