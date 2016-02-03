package incomemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import model.Money;
import service.Service;

/**
 * Created by Florea on 10/12/2015.
 */
public class RescheduleAlarm_Activity extends Activity {

    private Service service = Service.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle extra = getIntent().getExtras();
        Money money = extra.getParcelable("Money");
        String rule = money.getRule();
        String message = money.getNotes();
        int hour = extra.getInt("hour");
        int minute = extra.getInt("minute");
        String dateString = service.getStringFromDate(money.getDate());

        service.createAlarm(this, money, hour, minute);

        Intent finalIntent = new Intent(this, MainActivity.class);
        finalIntent.putExtra("Money", money);
        startActivity(finalIntent);

        this.finish();
    }

}
