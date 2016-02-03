package incomemanager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.incomemanager.R;

import dialog.NewCategoryDialog;
import fragment.Salary_Fragment;
import model.Category;
import model.Money;
import service.MyFragmentPageAdapter;
import service.Service;


public class Income_Activity extends ActionBarActivity implements Salary_Fragment.OnDataPass {

    private ActionBar actionBar;
    private ViewPager mPager;
    private String tabTitle[] = {"Other Income", "Salary"};
    private Service service = Service.getInstance();
    private boolean repeatingAlarm = false;
    private int hour;
    private int minute;
    private long salary = 0;
    private long cat_id = 1;
    private boolean modifie = false;
    private String scope;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);


        mPager = (ViewPager) findViewById(R.id.pager_income);
        mPager.setAdapter(new MyFragmentPageAdapter(
                getSupportFragmentManager(), tabTitle, this));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.action_bar_save, menu);


        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#606060")));
        actionBar.setDisplayHomeAsUpEnabled(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        String amount;
        String notes;
        String date;
        Category category;
        String rule;


        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_add:
                modifie = true;
                if (mPager.getCurrentItem() == 0) {
                    EditText text_amount = (EditText) findViewById(R.id.amount);
                    EditText text_notes = (EditText) findViewById(R.id.notes);
                    EditText text_date = (EditText) findViewById(R.id.date);
                    Spinner spiner_cat = (Spinner) findViewById(R.id.spinner_category_income);

                    if (!service.emptyText(text_amount)
                            && !service.emptyText(text_date)
                            && !service.emptyText(text_notes)) {

                        amount = text_amount.getText().toString();
                        notes = text_notes.getText().toString();
                        date = text_date.getText().toString();
                        category = (Category) spiner_cat.getSelectedItem();
                        rule = "1";// one time income "Other income"

                        if (category.getCategory_id() == -1) {
                            Toast toast = Toast
                                    .makeText(this, "Please choose a category",
                                            Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            Money money = new Money(category.getCategory_id(), Double.parseDouble(amount), notes, service.getDateFromString(date), rule, "Income", 1);

                            service.addIncomeExpense(this, money);

                            Intent intent = new Intent(this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }


                    } else {
                        if (service.emptyText(text_amount)) {
                            Toast toast = Toast
                                    .makeText(this, "Please complete the amount",
                                            Toast.LENGTH_LONG);
                            toast.show();
                        } else if (service.emptyText(text_date)) {
                            Toast toast = Toast.makeText(this,
                                    "Please choose a date", Toast.LENGTH_LONG);
                            toast.show();
                        } else if (service.emptyText(text_notes)) {
                            Toast toast = Toast.makeText(this,
                                    "Please write a note", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                } else if (mPager.getCurrentItem() == 1) {

                    Spinner spiner_rule_salary = (Spinner) findViewById(R.id.spinner_rule_salary);
                    //ToggleButton repeat= (ToggleButton) findViewById(R.id.toggle_repeat);
                    EditText text_amount_salary = (EditText) findViewById(R.id.salary_amount);
                    EditText text_notes_salary = (EditText) findViewById(R.id.salary_notes);
                    EditText text_date_salary = (EditText) findViewById(R.id.salary_date);

                    if (!service.emptyText(text_amount_salary)
                            && !service.emptyText(text_date_salary)
                            && !service.emptyText(text_notes_salary)) {

                        amount = text_amount_salary.getText().toString();
                        rule = spiner_rule_salary.getSelectedItem().toString();

                        notes = text_notes_salary.getText().toString();
                        date = text_date_salary.getText().toString();
                        Money money = new Money(salary, Double.parseDouble(amount), notes, service.getDateFromString(date), rule, "Income", 1);

                        service.addIncomeExpense(this, money);
                        if (repeatingAlarm) {

                            service.createAlarm(this, money, hour, minute);
                        }
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        if (service.emptyText(text_amount_salary)) {
                            Toast toast = Toast
                                    .makeText(this, "Please complete the amount",
                                            Toast.LENGTH_LONG);
                            toast.show();
                        } else if (service.emptyText(text_date_salary)) {
                            Toast toast = Toast.makeText(this,
                                    "Please choose a date", Toast.LENGTH_LONG);
                            toast.show();
                        } else if (service.emptyText(text_notes_salary)) {
                            Toast toast = Toast.makeText(this,
                                    "Please write a note", Toast.LENGTH_LONG);
                            toast.show();
                        }

                    }

                }

                break;

            case android.R.id.home:
                //TODO
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addCategories(View view) {
        Spinner spiner_cat = (Spinner) findViewById(R.id.spinner_category_income);
        final NewCategoryDialog dialog = new NewCategoryDialog(this, spiner_cat);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return super.onKeyDown(keyCode, event);
    }

    public void setCategory(long categoryId) {
        this.cat_id = categoryId;
    }

    @Override
    public void onDataPass(boolean data) {

        repeatingAlarm = data;
    }

    @Override
    public void onTimePass(int hour, int minute) {

        this.hour = hour;
        this.minute = minute;
    }


}
