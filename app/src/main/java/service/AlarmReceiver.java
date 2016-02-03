package service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Florea on 10/8/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private Service service = Service.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {

        service.createNotification(context, intent);
    }


}
