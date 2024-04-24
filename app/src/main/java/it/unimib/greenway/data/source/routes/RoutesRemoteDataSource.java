package it.unimib.greenway.data.source.routes;

import static it.unimib.greenway.BuildConfig.MAPS_API_KEY;
import static it.unimib.greenway.util.Constants.DEPARTURE_TIME_CONSTANT;
import static it.unimib.greenway.util.Constants.DRIVE_CONSTANT;
import static it.unimib.greenway.util.Constants.FIELDMASK_ROUTE;
import static it.unimib.greenway.util.Constants.ROUTING_PREFERENCE_CONSTANT;
import static it.unimib.greenway.util.Constants.TRANSIT_CONSTANT;
import static it.unimib.greenway.util.Constants.WALK_CONSTANT;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import it.unimib.greenway.data.service.RoutesApiService;
import it.unimib.greenway.model.Route;
import it.unimib.greenway.model.RoutesApiResponse;
import it.unimib.greenway.model.RoutesResponse;
import it.unimib.greenway.util.ServiceLocator;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoutesRemoteDataSource extends BaseRoutesRemoteDataSource{

    int count;
    private final RoutesApiService routesApiService;
    public RoutesRemoteDataSource() {
        this.routesApiService = ServiceLocator.getInstance().getRoutesApiService();

    }

    @Override
    public void getRoutes(double latStart, double lonStart, double latEnd, double lonEnd) {

            String transport;
            String routingPreference;
            String departureTime;
            String transitPreference;
            count= 0;
            List<Route> routeList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if(i == 0) {
                transport = DRIVE_CONSTANT;
                routingPreference = ROUTING_PREFERENCE_CONSTANT;
                departureTime = DEPARTURE_TIME_CONSTANT;
                transitPreference = "";
            }else if(i == 1) {
                transport = TRANSIT_CONSTANT;
                routingPreference = "";
                departureTime = "";
                transitPreference = "\"transitPreferences\": {\n" +
                        "\"routingPreference\": \"LESS_WALKING\", \n" +
                        "\"allowedTravelModes\": [\"BUS\", \"SUBWAY\", \"TRAIN\", \"LIGHT_RAIL\", \"RAIL\"]},";
            } else {
                transport = WALK_CONSTANT;
                routingPreference = "";
                departureTime = DEPARTURE_TIME_CONSTANT;
                transitPreference = "";
            }

            String body = "{\n" +
                    "  \"origin\":{\n" +
                    "    \"location\":{\n" +
                    "      \"latLng\":{\n" +
                    "        \"latitude\": " + latStart + ",\n" +
                    "        \"longitude\": " + lonStart + "\n" +
                    "      }\n" +
                    "    }\n" +
                    "  },\n" +
                    "  \"destination\":{\n" +
                    "    \"location\":{\n" +
                    "      \"latLng\":{\n" +
                    "        \"latitude\": " + latEnd + ",\n" +
                    "        \"longitude\": " + lonEnd + "\n" +
                    "      }\n" +
                    "    }\n" +
                    "  },\n" +
                    "  \"travelMode\": \"" + transport + "\",\n" +
                         routingPreference +
                        departureTime +
                    transitPreference +
                    "  \"computeAlternativeRoutes\": true,\n" +
                    "  \"routeModifiers\": {\n" +
                    "    \"avoidTolls\": false,\n" +
                    "    \"avoidHighways\": false,\n" +
                    "    \"avoidFerries\": false\n" +
                    "  },\n" +
                    "  \"languageCode\": \"en-US\",\n" +
                    "  \"units\": \"IMPERIAL\"\n" +
                    "}";

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body);

            Call<RoutesApiResponse> call = routesApiService.createRoute(requestBody, FIELDMASK_ROUTE, MAPS_API_KEY);
            String finalDrive = transport;

            call.enqueue(new Callback<RoutesApiResponse>() {
                @Override
                public void onResponse(Call<RoutesApiResponse> call, Response<RoutesApiResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRoutes() != null){ List<Route> route = response.body().getRoutes();
                            for(int i = 0; i < response.body().getRoutes().size(); i++){
                                route.get(i).setTravelMode(finalDrive);
                                route.get(i).setStart(new LatLng(latStart, lonStart));
                                route.get(i).setDestination(new LatLng(latEnd, lonEnd));
                            }
                            routeList.addAll(route);
                        }
                       
                        if(count == 2){
                            routesCallBack.onSuccessFromRemote(routeList);
                        }
                        count++;
                    } else {
                        // handle request errors

                    }
                }

                @Override
                public void onFailure(Call<RoutesApiResponse> call, Throwable t) {
                    // handle network errors
                    Log.d("routeProva", "onFailure", t);
                }
            });
        }
    }
}
