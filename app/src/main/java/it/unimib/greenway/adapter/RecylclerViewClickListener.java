package it.unimib.greenway.adapter;

import android.view.View;

import it.unimib.greenway.model.Route;

public interface RecylclerViewClickListener {
    void onClick(Route route);
    void onClick(String userId, boolean checked);
    void onClickMaps(Route route);
}
