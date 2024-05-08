package it.unimib.greenway.adapter;


import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;

import it.unimib.greenway.R;
import it.unimib.greenway.model.Challenge;
import it.unimib.greenway.model.Route;
import it.unimib.greenway.model.StatusChallenge;
import it.unimib.greenway.model.User;

public class ChallengeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<Challenge> challengeList;
    private final Application application;
    private User user;

    public ChallengeRecyclerViewAdapter(List<Challenge> challengeList, Application application, User user){
        this.challengeList = challengeList;
        this.application = application;
        this.user = user;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.challenge_card_item, parent, false);
        return new ChallengeRecyclerViewAdapter.ChallengeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChallengeRecyclerViewAdapter.ChallengeViewHolder) {
            ((ChallengeRecyclerViewAdapter.ChallengeViewHolder) holder).bind(challengeList.get(position));

        }
    }

    @Override
    public int getItemCount() {
        if (challengeList != null) {
            return challengeList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (challengeList.get(position) == null) {
            return 1;
        } else {
            return 0;
        }
    }

    public class ChallengeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView description, point, percentageTextView;
        private final ImageView image;
        private final ConstraintLayout layout;
        private final LinearProgressIndicator progressIndicator;

        public ChallengeViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.challengeNameTextView);
            image = itemView.findViewById(R.id.challengeIconImageView);
            layout = itemView.findViewById(R.id.layoutChallengeCard);
            point = itemView.findViewById(R.id.challengePointTextView);
            progressIndicator = itemView.findViewById(R.id.progressBarChallenge);
            percentageTextView = itemView.findViewById(R.id.challengePercentageTextView);
        }

        @Override
        public void onClick(View v) {

        }

        public void bind(Challenge challenge) {

            int percentage = user.getStatusChallengeList().get(challenge.getId()-1).getPercentage();
            progressIndicator.setProgressCompat(percentage, true);
            percentageTextView.setText(percentage + "%");
            point.setText(challenge.getPoint() + "pt");
            description.setText(challenge.getDescription());

            //challenge completata
            if(percentage >= 100){
                percentageTextView.setText("100%");
                image.setImageResource(R.drawable.icon_check);
                layout.setBackgroundResource(R.color.md_theme_primaryContainer);
            }
        }
    }
}
