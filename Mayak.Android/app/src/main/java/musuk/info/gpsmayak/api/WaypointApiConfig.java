package musuk.info.gpsmayak.api;

import musuk.info.gpsmayak.model.AddWaypointRequest;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface WaypointApiConfig {
    @POST("/api/location/add")
    void addLocations(@Body AddWaypointRequest request, Callback<String> cb);
}
