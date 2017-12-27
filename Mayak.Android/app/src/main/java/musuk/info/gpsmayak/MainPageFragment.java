package musuk.info.gpsmayak;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Alexey on 23.03.2015.
 */
public class MainPageFragment extends Fragment {

    private TextView mTextView;
    private Button mBtnRun;
    private Button mBtnStop;
    private DbHelper myDbHelper;
    private Context mContext;

    // Receive locations for UI
    private BroadcastReceiver mUILocationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra("location");
            String message = String.format("%s [%s, %s]\n", location.getProvider(), location.getLatitude(), location.getLongitude());
            showLog(message);
        }
    };

    public MainPageFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        myDbHelper = DbHelper.getInstance(mContext);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Consts.Actions.LOCATION);
        mContext.registerReceiver(mUILocationReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        mContext.unregisterReceiver(mUILocationReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mTextView = (TextView) rootView.findViewById(R.id.textView);
        mBtnRun = (Button) rootView.findViewById(R.id.btnRun);
        mBtnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRunClick(v);
            }
        });

        mBtnStop = (Button) rootView.findViewById(R.id.btnStop);
        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStopClick(v);
            }
        });
        Log.d("x", "onviewcreate");
        return rootView;
    }

    private void btnStopClick(View v) {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(getLocationPendingIntent());

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getSyncPendingIntent());
    }

    /*
     SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Count(*) FROM locations", null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            showLog(String.format("Logged %d points", count));
        }
     */

    public void btnRunClick(View view) {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);
        locationManager.requestLocationUpdates(provider, 1000, 1, getLocationPendingIntent());

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, 10 * 1000, getSyncPendingIntent());

        showLog("Service started");

    }

    private PendingIntent getLocationPendingIntent() {
        Intent intent = new Intent(Consts.Actions.LOCATION);
        return PendingIntent.getBroadcast(mContext, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getSyncPendingIntent() {
        Intent intent = new Intent(Consts.Actions.SYNC);
        return PendingIntent.getBroadcast(mContext, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void showLog(String message) {
        String text = mTextView.getText().toString();
        text += message + "\n";
        mTextView.setText(text);
    }
}