package it.unimib.greenway.data.source.routes;

import static it.unimib.greenway.BuildConfig.MAPS_API_KEY;
import static it.unimib.greenway.util.Constants.DRIVE_CONSTANT;
import static it.unimib.greenway.util.Constants.TRANSIT_CONSTANT;
import static it.unimib.greenway.util.Constants.TRANSIT_CONSTANT_PREFERENCES;
import static it.unimib.greenway.util.Constants.WALK_CONSTANT;

import android.util.Log;

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
                routingPreference = " \"routingPreference\": \"TRAFFIC_AWARE\",\n";
                departureTime = "  \"departureTime\": \"2024-10-23T15:01:23.045123456Z\",\n";
                transitPreference = "";
            }else if(i == 1) {
                transport = TRANSIT_CONSTANT;
                routingPreference = "";
                departureTime = "";
                transitPreference = TRANSIT_CONSTANT_PREFERENCES;
            } else {
                transport = WALK_CONSTANT;
                routingPreference = "";
                departureTime = "  \"departureTime\": \"2024-10-23T15:01:23.045123456Z\",\n";
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

            Call<RoutesApiResponse> call = routesApiService.createRoute(requestBody, "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline", MAPS_API_KEY);
            String finalDrive = transport;

            call.enqueue(new Callback<RoutesApiResponse>() {
                @Override
                public void onResponse(Call<RoutesApiResponse> call, Response<RoutesApiResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRoutes() != null){ List<Route> route = response.body().getRoutes();
                            for(int i = 0; i < response.body().getRoutes().size(); i++){
                                route.get(i).setTravelMode(finalDrive);
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
