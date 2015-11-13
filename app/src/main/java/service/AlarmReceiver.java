package service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Florea on 10/8/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private Service service=Service.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("Alarm","Alarm");
        service.createNotification(context,intent);
    }


}
