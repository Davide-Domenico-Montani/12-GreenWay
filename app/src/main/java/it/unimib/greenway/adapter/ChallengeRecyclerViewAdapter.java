package it.unimib.greenway.adapter;


import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Application;
import android.graphics.drawable.Drawable;
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

public class ChallengeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<Challenge> challengeList;
    private final Application application;

    public ChallengeRecyclerViewAdapter(List<Challenge> challengeList, Application application){
        this.challengeList = challengeList;
        this.application = application;
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
        private final TextView description, point;
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
        }

        @Override
        public void onClick(View v) {

        }

        public void bind(Challenge challenge) {
            //TODO: Calcolo progresso challenge
            point.setText(challenge.getPoint() + "pt");
            description.setText(challenge.getDescription());
            if(challenge.getPoint() == 100){

                // Impostare il Drawable nell'ImageView
                progressIndicator.setProgressCompat(100, true);
                image.setImageResource(R.drawable.icon_check);
                layout.setBackgroundResource(R.color.md_theme_primaryContainer);
            }
        }
    }
}
