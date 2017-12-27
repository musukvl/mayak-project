package musuk.info.gpsmayak;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import musuk.info.gpsmayak.model.Waypoint;

/**
 * Created by Alexey on 23.03.2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static volatile DbHelper mInstance;

    public static DbHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DbHelper.class) {
                if (mInstance == null) {
                    mInstance = new DbHelper(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    private DbHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("", "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table locations ("
                + "id integer primary key autoincrement,"
                + "lat REAL,"
                + "lon REAL,"
                + "date INTEGER"
                + ");");
    }

    public List<Waypoint> getLocations(long date) throws JSONException {


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id, lat, lon, date FROM locations WHERE date > ?",
                new String[] { String.valueOf(date) });
        if (!cursor.moveToFirst()) {
            return null;
        }

        int latColIndex = cursor.getColumnIndex("lat");
        int lonColIndex = cursor.getColumnIndex("lon");
        int dateColIndex = cursor.getColumnIndex("date");

        List<Waypoint> result = new ArrayList<Waypoint>();
        do {
            Waypoint location = new Waypoint();
            location.lat = cursor.getDouble(latColIndex);
            location.lon = cursor.getDouble(lonColIndex);
            location.date = cursor.getLong(dateColIndex);
            result.add(location);
        }
        while (cursor.moveToNext());
        return result;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}