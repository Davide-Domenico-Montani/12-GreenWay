package it.unimib.greenway.adapter;

import android.app.Application;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import it.unimib.greenway.R;
import it.unimib.greenway.model.Route;

public class RoutesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public interface OnItemClickListener {
        void onRouteItemClick(Route route);
    }

    private final List<Route> routeList;
    private final OnItemClickListener onItemClickListener;
    private final Application application;

    public RoutesRecyclerViewAdapter(List<Route> routeList, Application application,
                                     OnItemClickListener onItemClickListener) {
        this.routeList = routeList;
        this.onItemClickListener = onItemClickListener;
        this.application = application;

    }

    @Override
    public int getItemViewType(int position) {
        if (routeList.get(position) == null) {
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
                inflate(R.layout.route_card_item, parent, false);
        Log.d("routeHolder", "onBindViewHolder: " + view);
        return new RoutesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RoutesViewHolder) {
            ((RoutesViewHolder) holder).bind(routeList.get(position));
        }
    }


    @Override
    public int getItemCount() {
        if (routeList != null) {
            return routeList.size();
        }
        return 0;
    }

    public class RoutesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView routeDistance;
        private final TextView routeDuration;

        public RoutesViewHolder(@NonNull View itemView) {
            super(itemView);
            routeDuration = itemView.findViewById(R.id.time_card_route);
            routeDistance = itemView.findViewById(R.id.distance_value);
            Log.d("routeHolder", "onBindViewHolder: " + routeDistance);
            itemView.setOnClickListener(this);
        }

        public void bind(Route route) {
            routeDistance.setText(String.valueOf(route.getDistanceMeters()));
            routeDuration.setText(route.getDuration());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
