package musuk.info.gpsmayak.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;

import musuk.info.gpsmayak.AppStorage;
import musuk.info.gpsmayak.DbHelper;
import musuk.info.gpsmayak.api.WaypointApi;
import musuk.info.gpsmayak.model.AddWaypointRequest;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SyncReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        DbHelper dbHelper = DbHelper.getInstance(context);
        AddWaypointRequest request = new AddWaypointRequest();
        final AppStorage appStorage = AppStorage.getInstance(context);
        try {
            request.locations = dbHelper.getLocations(appStorage.getLastSyncTime());
            if (request.locations == null) {
                Log.d("SyncReceiver", "Nothing to sync");
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("SyncReceiver", request.toString());
        WaypointApi api = WaypointApi.getInstance();
        api.addLocations(request, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                appStorage.saveLastSyncTime();
                Log.d("SyncReceiver", "Request Success");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.w("SyncReceiver", "Request Failed");
                Log.w("SyncReceiver", error);
            }
        });

    }
}
