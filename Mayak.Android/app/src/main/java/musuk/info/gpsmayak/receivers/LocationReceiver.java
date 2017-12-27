package musuk.info.gpsmayak.receivers;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import musuk.info.gpsmayak.DbHelper;


public class LocationReceiver extends BroadcastReceiver {

    public LocationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Location location = intent.getParcelableExtra("location");
        String message = String.format("%s [%s,%s]\n", location.getProvider(), location.getLatitude(), location.getLongitude());
        Log.d("Action", String.format("action=%s", intent.getAction()));
        Log.d("LocationReceiver", message);
        saveLocation(DbHelper.getInstance(context), location);
    }


    private void saveLocation(DbHelper dbHelper, Location location) {

        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put("lat", location.getLatitude());
        cv.put("lon", location.getLongitude());
        cv.put("date", System.currentTimeMillis());
        long rowID = db.insert("locations", null, cv);
        Log.d("LocationReceiver", "row inserted, ID = " + rowID);
    }

}