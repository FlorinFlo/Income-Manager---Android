package incomemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import service.Service;

/**
 * Created by Florea on 10/12/2015.
 */
public class RescheduleAlarm_Activity extends Activity {

    private Service service=Service.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle extra=getIntent().getExtras();
        String rule=extra.getString("rule");
        String message=extra.getString("message");
        int hour=extra.getInt("hour");
        int minute=extra.getInt("minute");
        String dateString=extra.getString("date");
        Date date= service.returnDateFromString(dateString);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);

        if(rule.equals("Monthly")){
            calendar.add(Calendar.MONTH,1);
            service.createAlarm(this,rule,service.getStringFromCalendar(calendar),message,hour,minute);
            Log.w("RULE EQUALS","Monthly");

        }else if(rule.equals("Biweekly")){
            calendar.add(Calendar.WEEK_OF_YEAR,2);
            service.createAlarm(this, rule, service.getStringFromCalendar(calendar), message, hour, minute);
            Log.w("RULE EQUALS","Biweekly");
        }else if (rule.equals("Weekly")){
            calendar.add(Calendar.WEEK_OF_YEAR,1);
            service.createAlarm(this, rule,service.getStringFromCalendar(calendar),message,hour,minute);
            Log.w("RULE EQUALS","weekly");
        }else{

        }


        Intent finalIntent=new Intent(this,MainActivity.class);
        startActivity(finalIntent);

        this.finish();
    }

}
