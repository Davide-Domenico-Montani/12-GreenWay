package it.unimib.greenway.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        private final Button buttonNavigation;

        public RoutesViewHolder(@NonNull View itemView) {
            super(itemView);
            routeDuration = itemView.findViewById(R.id.time_card_route);
            routeDistance = itemView.findViewById(R.id.distance_value);
            buttonNavigation = itemView.findViewById(R.id.buttonNavigate);
            itemView.setOnClickListener(this);
        }

        public void bind(Route route) {
            int totalSeconds = Integer.valueOf(route.getDuration().substring(0, route.getDuration().length() - 1));
            int hours = totalSeconds / 3600;
            int minutes = (totalSeconds % 3600) / 60;
            int seconds = totalSeconds % 60;
            String polyline = "enc:" + route.getPolyline().getEncodedPolyline();
            LatLng start = route.getStart();
            LatLng destination = route.getDestination();
            String uri = "https://www.google.com/maps/dir/?api=1&travelmode=driving&origin=" + start.latitude+ ","+ start.longitude  + "&destination=" + destination.latitude + "," + destination.longitude + "&polyline=" + polyline;

            if(hours != 0)
                routeDuration.setText(hours +"h " + minutes + "m");
            else
                routeDuration.setText(minutes + "m " + seconds + "s");
            routeDistance.setText(String.valueOf(route.getDistanceMeters()));

            buttonNavigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri gmmIntentUri = Uri.parse(uri);
                    Context context = v.getContext();
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps"); // Specifica che vuoi aprire l'app Google Maps
                    context.startActivity(mapIntent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void clear() {
        routeList.clear();
    }

    public void addAll(List<Route> newList) {
        routeList.addAll(newList);
    }
}
