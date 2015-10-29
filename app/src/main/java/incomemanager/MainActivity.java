package incomemanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.incomemanager.R;

import contentprovider.MyMonetaryContentProvider;
import service.MyFragmentPageAdapter;
import service.Service;
import tabs.SlidingTabLayout;

public class MainActivity extends ActionBarActivity {


    private ActionBar actionBar;
    private Activity activity = this;
    private String tabTitle[] = {"Budget","Expenses","Tools"};
    private ViewPager mPager;
    private SlidingTabLayout mtabs;
    private Service service = Service.getInstance();
    private MyMonetaryContentProvider contentProvider=service.contentProvider();




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPager= (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyFragmentPageAdapter(getSupportFragmentManager(), tabTitle, this));
        mtabs= (SlidingTabLayout) findViewById(R.id.tabs);
        mtabs.setDistributeEvenly(true);
        mtabs.setViewPager(mPager);



        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if (!pref.getBoolean("firstTime", false)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        contentProvider.createCategoriesForFirstTime(activity, "category");
                        }catch (Exception e){

                        }


                }
            }).start();

            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_activity, menu);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#606060")));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case (R.id.action_add):

                Intent intent_income = new Intent(this, Income_Activity.class);
                startActivity(intent_income);

                return true;
            case (R.id.action_remove):

                Intent intent_expense = new Intent(this, Expenses_Activity.class);
                startActivity(intent_expense);
                return true;

            case (R.id.settings):

                Intent intent_settings = new Intent(this, Settings_Activity.class);
                startActivity(intent_settings);

                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onResume() {
        super.onResume();


    }


}
