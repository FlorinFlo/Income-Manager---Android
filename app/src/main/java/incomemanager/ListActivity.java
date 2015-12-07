package incomemanager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.incomemanager.R;

import service.Service;

/**
 * Created by Florea on 11/18/2015.
 */
public class ListActivity extends ActionBarActivity {

    private ActionBar actionBar;
    private String scope;
    private String duration;
    private Service service= Service.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent=getIntent();
        scope=intent.getStringExtra("scope");
        duration=intent.getStringExtra("duration");

        ListView lv= (ListView) findViewById(R.id.list_money);
        service.populateMoneyList(this, scope, duration, lv);
        if(scope.equals("Income")){
            setTitle("Incomes");
        }else{
            setTitle("Expenses");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#606060")));
        actionBar.setDisplayHomeAsUpEnabled(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }
}
