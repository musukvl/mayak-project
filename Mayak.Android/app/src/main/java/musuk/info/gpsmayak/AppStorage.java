package musuk.info.gpsmayak;

import android.content.Context;
import android.content.SharedPreferences;

public class AppStorage {
    private static AppStorage mInstance;

    public static AppStorage getInstance(Context context) {

        if (mInstance == null) {
            synchronized (AppStorage.class) {
                if (mInstance == null) {
                    mInstance = new AppStorage(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    private SharedPreferences mSharedPreferences;

    private AppStorage(Context context) {
        mSharedPreferences = context.getSharedPreferences("MyStorage", Context.MODE_PRIVATE);
    }

    public long getLastSyncTime(){
        return mSharedPreferences.getLong("LastSyncTime", 0);
    }

    public void saveLastSyncTime() {
        mSharedPreferences.edit()
                .putLong("LastSyncTime", System.currentTimeMillis())
                .apply();
    }
}