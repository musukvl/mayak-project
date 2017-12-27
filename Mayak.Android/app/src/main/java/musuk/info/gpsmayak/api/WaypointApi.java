package musuk.info.gpsmayak.api;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import musuk.info.gpsmayak.model.AddWaypointRequest;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class WaypointApi {
    private static volatile WaypointApi mInstance;

    public static WaypointApi getInstance() {

        if (mInstance == null) {
            synchronized (WaypointApi.class) {
                if (mInstance == null) {
                    mInstance = new WaypointApi();
                }
            }
        }
        return mInstance;

    }

    private WaypointApiConfig mLocationApiConfig;

    private WaypointApi() {

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://mayak.musuk.info")
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        Log.d("api", message);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();

        mLocationApiConfig = restAdapter.create(WaypointApiConfig.class);
    }

    public void addLocations(AddWaypointRequest request, Callback<String> cb) {
        mLocationApiConfig.addLocations(request, cb);
    }
}

