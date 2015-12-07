package incomemanager;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.incomemanager.R;

import dialog.NewCategoryDialog;
import model.Category;
import model.Money;
import service.MyFragmentPageAdapter;
import service.Service;

public class Expenses_Activity extends ActionBarActivity {

    private ActionBar actionBar;
    private ViewPager mPager;
    private String tabTitle[] = {"Expenses", "Bills"};
    private Service service = Service.getInstance();
    private long cat_Id = 1;
    private boolean repeatingAlarm = false;
    private int hour;
    private int minute;
    private boolean modifie=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

            mPager = (ViewPager) findViewById(R.id.pager_expenses);

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
        String rule;
        String repeat;
        Category category;
        Money money;


        int id = item.getItemId();

        switch (id) {

            case R.id.action_add:
                modifie=true;
                if (mPager.getCurrentItem() == 0) {

                    EditText text_amount = (EditText) findViewById(R.id.expenses_amount);
                    EditText text_note = (EditText) findViewById(R.id.expenses_notes);
                    EditText text_date = (EditText) findViewById(R.id.expense_date);
                    Spinner spinnerCat = (Spinner) findViewById(R.id.spinner_category_expenses);

                    if (!service.emptyText(text_amount)
                            && !service.emptyText(text_date)
                            && !service.emptyText(text_note)) {
                        amount = text_amount.getText().toString();
                        notes = text_note.getText().toString();
                        date = text_date.getText().toString();
                        rule = "1"; //one time expense
                        category = (Category) spinnerCat.getSelectedItem();
                        if(category.getCategory_id()==-1){
                            Toast toast = Toast
                                    .makeText(this, "Please choose a category",
                                            Toast.LENGTH_LONG);
                            toast.show();
                        }else {

                            money = new Money(category.getCategory_id(), Double.parseDouble(amount), notes, service.getDateFromString(date), rule, "Expense", 1);
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
                        } else if (service.emptyText(text_note)) {
                            Toast toast = Toast.makeText(this,
                                    "Please write a note", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }


                } else if (mPager.getCurrentItem() == 1) {

                    Spinner spiner_rule = (Spinner) findViewById(R.id.spinner_rule);
                    EditText amount_bill = (EditText) findViewById(R.id.bill_amount);
                    EditText notes_bill = (EditText) findViewById(R.id.bill_note);
                    EditText date_bill = (EditText) findViewById(R.id.bill_date);
                    Spinner cat_bill = (Spinner) findViewById(R.id.bill_category);
                    if (!service.emptyText(amount_bill) && !service.emptyText(notes_bill)
                            && !service.emptyText(date_bill)) {

                        rule = spiner_rule.getSelectedItem().toString();
                        amount = amount_bill.getText().toString();
                        notes = notes_bill.getText().toString();
                        date = date_bill.getText().toString();
                        category = (Category) cat_bill.getSelectedItem();
                        if(category.getCategory_id()==-1){
                            Toast toast = Toast
                                    .makeText(this, "Please choose a category",
                                            Toast.LENGTH_LONG);
                            toast.show();
                        }else {
                            money = new Money(category.getCategory_id(), Double.parseDouble(amount), notes, service.getDateFromString(date), rule, "Expense", 1);

                            service.addIncomeExpense(this, money);

                            if (repeatingAlarm) {
                                service.createAlarm(this, money, hour, minute);
                            }

                            Intent intent = new Intent(this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    } else {
                        if (service.emptyText(amount_bill)) {
                            Toast toast = Toast
                                    .makeText(this, "Please complete the amount",
                                            Toast.LENGTH_LONG);
                            toast.show();
                        } else if (service.emptyText(date_bill)) {
                            Toast toast = Toast.makeText(this,
                                    "Please choose a date", Toast.LENGTH_LONG);
                            toast.show();

                        } else if (service.emptyText(notes_bill)) {
                            Toast toast = Toast.makeText(this,
                                    "Please write a note", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                }

                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addCategories(View view) {
        Spinner spiner_cat = (Spinner) findViewById(R.id.spinner_category_expenses);
        final NewCategoryDialog dialog = new NewCategoryDialog(this, spiner_cat);

    }

    public void setCategory(long category_Id) {
        cat_Id = category_Id;
    }

    public void setReapeatingAlarm(boolean repeatingAlarm) {
        this.repeatingAlarm = repeatingAlarm;
    }

    public void setTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }


}
