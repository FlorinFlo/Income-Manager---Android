package service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.incomemanager.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import contentprovider.MyMonetaryContentProvider;
import graphics.Bar;
import graphics.BarGraph;
import graphics.PieGraph;
import graphics.PieSlice;
import incomemanager.RescheduleAlarm_Activity;
import model.Balance;
import model.Category;
import model.GraphValue;
import model.Money;

public class Service {

    private static Service instance = null;

    private SimpleDateFormat dateFormater;

    private MyMonetaryContentProvider contentProvider;

    private Date date1;
    private Calendar myCalendar = Calendar.getInstance();
    private ArrayList<Money> listWeekIncExp = new ArrayList<>();
    private ArrayList<Money> listMonthIncExp = new ArrayList<>();


    // private Uri uri = null;
    // int val;

    // private MonetaryDatabaseHelper database;

    // private ArrayList<Money> expensesCurentMonth = new ArrayList<Money>();

    // private ArrayList<Money> expensesForChart = new ArrayList<Money>();

//    private String[] code = new String[]{"Eclair & Older", "Froyo",
//            "Gingerbread", "Honeycomb", "IceCream Sandwich", "Jelly Bean"};


    public Service() {

    }

    public static Service getInstance() {

        if (instance == null) {

            instance = new Service();
        }

        return instance;

    }

    public MyMonetaryContentProvider contentProvider() {
        contentProvider = new MyMonetaryContentProvider();
        return contentProvider;
    }


    //check if edittext is empty
    public Boolean emptyText(EditText text) {
        if (text.getText().toString().matches(""))
            return true;

        return false;

    }

    /**
     * Date Related Methods
     */

    public void initiateDate(final EditText dateField, final Context context, Date date1) {

        dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        dateField.setText(dateFormater.format(date1));

        dateField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Update text

                        dateField.setText(dateFormater.format(new Date(
                                myCalendar.getTimeInMillis())));


                    }

                };

                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


    public Date getDateFromString(String date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date returnedDate = null;
        try {
            returnedDate = format.parse(date);

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return returnedDate;

    }

    public String getMonths(int month) {

        switch (month) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                break;
        }

        return null;

    }

    public void setTime(TextView textTime, int hour, int minute) {


        String hours = "";
        String minutes = "";

        if (minute < 10) {
            minutes = "0" + minute;
        } else {
            minutes = "" + minute;
        }

        if (hour > 0 && hour < 10) {

            hours = "0" + hour;
        } else if (hour == 0) {

            hours = "00";
        } else {
            hours = "" + hour;

        }


        textTime.setText(hours + ":" + minutes);


    }

    public int[] getTimeNow(Calendar calendar) {
        int[] time = new int[2];

        calendar = Calendar.getInstance();
        time[0] = calendar.get(Calendar.HOUR_OF_DAY);
        time[1] = calendar.get(Calendar.MINUTE);

        return time;
    }

    public String getStringFromCalendar(Calendar calendar) {
        String date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (calendar != null) {
            date = sdf.format(calendar.getTime());
        }
        return date;
    }

    public int getWeekOfYear(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR) - 1;
    }

    public int getDayNumber(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public Date getUpdateDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return date = calendar.getTime();
    }

    public boolean checkUpdateBalance(Money money) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Calendar givenCalendar = Calendar.getInstance();
        givenCalendar.setTime(money.getDate());
        if (calendar.get(Calendar.YEAR) == givenCalendar.get(Calendar.YEAR)
                && calendar.get(Calendar.DAY_OF_YEAR) >= givenCalendar.get(Calendar.DAY_OF_YEAR) && money.getStatus() == 1)
            return true;
        else
            return false;


    }

    public int getMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public Date removePeriodFromDate(Date date, String period) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (period.equals("week")) {
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
        } else if (period.equals("month")) {
            calendar.add(Calendar.MONTH, -1);
        } else if (period.equals("year")) {
            calendar.add(Calendar.YEAR, -1);
        }

        return calendar.getTime();
    }


    //Return hex color from int
    public String returnHexColorFromInt(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }

    //Returns int color
    public int getColorFromHex(String hexColor) {
        return Color.parseColor(hexColor);
    }


    public void populateSpinnerCategory(Spinner spiner, Context context) {
        List<Category> categories_list = this.contentProvider.getCategories(context);
        Category cat = new Category(-1, "Category", null);
        categories_list.add(cat);
        CategoryAdapter adapter = new CategoryAdapter(context, categories_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spiner.setAdapter(adapter);
        spiner.setSelection(adapter.getCount());


    }

    public void createAlarm(Context context, Money money, int hour, int minute) {
        try {
            Calendar calendar = Calendar.getInstance();
            Date dat = money.getDate();
            calendar.setTime(dat);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);

            if (money.getRule().equals("Monthly")) {
                calendar.add(Calendar.MONTH, 1);
            } else if (money.getRule().equals("Biweekly")) {
                calendar.add(Calendar.WEEK_OF_YEAR, 2);
            } else if (money.getRule().equals("Weekly")) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
            }

            money.setDate(calendar.getTime());
            money.setStatus(0);

            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(context, AlarmReceiver.class);
            intent.putExtra("Money", money);
            intent.putExtra("hour", hour);
            intent.putExtra("minute", minute);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);


            contentProvider.createIncomeExpense(money, context);


        } catch (Exception ex) {
            Toast toast = Toast.makeText(context, ex + " ", Toast.LENGTH_LONG);
        }


    }

    public void createNotification(Context context, Intent intent) {
        try {
            Bundle extra = intent.getExtras();
            Money money = extra.getParcelable("Money");
            String message = money.getNotes();
            String rule = money.getRule();
            int hour = extra.getInt("hour");
            int minute = extra.getInt("minute");
            String date = getStringFromDate(money.getDate());
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Intent nextIntent = new Intent(context, RescheduleAlarm_Activity.class);
            nextIntent.putExtra("Money", money);
            nextIntent.putExtra("hour", hour);
            nextIntent.putExtra("minute", minute);
            PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), nextIntent, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle("Income Manager");
            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setContentText(message);
            builder.setContentIntent(pIntent);
            builder.setAutoCancel(true);
            builder.setSound(sound);
           // builder.addAction(R.drawable.ic_action_postpone, "Postpone", pIntent).build();


            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(1, builder.build());

        } catch (Exception ex) {
            Toast toast = Toast.makeText(context, ex + " ", Toast.LENGTH_LONG);
        }


    }


    public void addIncomeExpense(Context context, Money money) {
        contentProvider.createIncomeExpense(money, context);
        Balance balance;
        if (checkUpdateBalance(money)) {
            balance = contentProvider.getSavedBalance(context);
            if (money.getType().equals("Income")) {
                balance.setAmount(balance.getAmount() + money.getAmount());

            } else if (money.getType().equals("Expense")) {
                balance.setAmount(balance.getAmount() - money.getAmount());
            }
            contentProvider.updateBalance(balance.getAmount(), money.getDate());


        }

    }


    public void getExpensesCurentMonth() {

        contentProvider().getExpenses();
    }


    public Map<Long, Double> getExpenseWithSameCategory(ArrayList<Money> expenses) {

        Date date = new Date();
        Map<Long, Double> list = new HashMap<Long, Double>();


        for (Money exp : expenses) {

            if (exp.getDate().before(date) || exp.getDate().equals(date)) {
                if (list.containsKey(exp.getCategory())) {

                    double amount = list.get(exp.getCategory());

                    amount += exp.getAmount();
                    list.put(exp.getCategory(), amount);

                } else {
                    list.put(exp.getCategory(), exp.getAmount());
                }


            }
        }

        return list;
    }

    public Money[] populateArray(Money[] returnedArray, ArrayList<Money> list) {
        returnedArray = new Money[list.size()];
//        for(int i=0;i<list.size();i++){
//            returnedArray[i]=list.get(i);
//        }
        list.toArray(returnedArray);
        return returnedArray;
    }


    public String getStringFromDate(Date date) {
        String returnString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (date != null) {
            returnString = sdf.format(date);
        }
        return returnString;


    }

    public Double getExpensesToday(Calendar calendar, ArrayList<Money> expenses) {
        double returnedAmount = 0;
        for (Money exp : expenses) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(exp.getDate());
            if (cal.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)) {
                returnedAmount += exp.getAmount();
            }
        }
        return returnedAmount;
    }


    public Spanned appendTextColor(String str, String hexColor) {

        String text = "<font color=" + hexColor + ">" + str + " </font>";
        return Html.fromHtml(text);

    }

    public void updateBalance(Context context) {


    }

    /* the function strftimr()that is used
     with week of the year starts with week 0 */

    public void cleanButton(Button button, String amount) {

        String string = button.getText().toString();

        String newString = string.replaceAll("\\n.*", "");


        button.setText(newString + "\n" + amount);

    }


    public double getAmountWeek(String type) {
        Date date = new Date();
        listWeekIncExp = contentProvider.getWeekIncomesExpenses(date);
        return getAmountFromList(listWeekIncExp, type);
    }

    public double getAmountMonth(String type) {
        Date date = new Date();
        listMonthIncExp = contentProvider.getMonthIncomesExpenses(getMonthFromDate(date));
        return getAmountFromList(listMonthIncExp, type);
    }


    public double getAmountFromList(ArrayList<Money> money, String type) {
        double amount = 0;
        if (money != null && money.size() > 0) {
            for (Money m : money) {
                if (m.getType().equals(type))
                    amount += m.getAmount();
            }
        }
        return amount;
    }

    public double getAmountExpToday() {
        double amount = 0;
        for (Money m : listWeekIncExp) {
            if (getDayNumber(m.getDate()) == getDayNumber(new Date()) && m.getType().equals("Expense")) {
                amount += m.getAmount();

            }
        }
        return amount;
    }

    public ArrayList<Money> getListWeekIncomes() {
        ArrayList<Money> money = new ArrayList<>();
        for (Money m : listWeekIncExp) {
            if (m.getType().equals("Income"))
                money.add(m);
        }
        return money;
    }

    public void populateMoneyList(Activity activity, String scope, String duration, ListView lv) {

        if (scope.equals("Income")) {
            if (duration.equals("week")) {
                getIncomesExpense(listWeekIncExp, "Income", activity, lv);
            } else {
                getIncomesExpense(listMonthIncExp, "Income", activity, lv);
            }
        } else if (scope.equals("Expense")) {
            if (duration.equals("week")) {
                getIncomesExpense(listWeekIncExp, "Expense", activity, lv);
            } else if (duration.equals("month")) {
                getIncomesExpense(listMonthIncExp, "Expense", activity, lv);
            } else if (duration.equals("today")) {
                getIncomesExpense(getTodayExpenses(listWeekIncExp), "Expense", activity, lv);

            }
        }
    }

    public void getIncomesExpense(ArrayList<Money> list, String type, Activity activity, ListView lv) {
        ArrayList<Money> returnList = new ArrayList<Money>();
        ListAdapter adapter = new ListAdapter(activity, returnList);
        lv.setAdapter(adapter);

        for (Money m : list) {
            if (m.getType().equals(type)) {
                returnList.add(m);
                adapter.notifyDataSetChanged();
            }
        }

    }

    public ArrayList<Money> getTodayExpenses(ArrayList<Money> money) {
        ArrayList<Money> returnList = new ArrayList<>();
        for (Money m : money) {
            if (getDayNumber(m.getDate()) == getDayNumber(new Date())) {
                returnList.add(m);
            }
        }
        return returnList;
    }

    public void initiateChart(final Activity activity, Category selectedCategory, Date date1, Date date2) {

        final PieGraph pg = (PieGraph) activity.findViewById(R.id.pie_graph);
        final TextView text = (TextView) activity.findViewById(R.id.value_slice);
        final TextView bartext= (TextView) activity.findViewById(R.id.bar_category);
        final EditText dateText1= (EditText) activity.findViewById(R.id.date_after);
        final EditText dateText2= (EditText) activity.findViewById(R.id.date_before);
        final TextView textFrom= (TextView) activity.findViewById(R.id.text_to);
        final TextView textTo= (TextView) activity.findViewById(R.id.text_from);
        final Spinner spinner= (Spinner) activity.findViewById(R.id.spinner_period);

        final BarGraph bg = (BarGraph) activity.findViewById(R.id.bar_graph);

        

        if (selectedCategory == null) {

            pg.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
            bg.setVisibility(View.GONE);
            bartext.setVisibility(View.GONE);
            dateText1.setVisibility(View.VISIBLE);
            dateText2.setVisibility(View.VISIBLE);
            textTo.setVisibility(View.VISIBLE);
            textFrom.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            pg.removeSlices();
            ArrayList<Money> expenses = contentProvider.getExpensesForPeriod(date1, date2);
            Map<Long, Double> catAmountMap = getExpensesCategoryWithAmount(expenses);

            if (catAmountMap.size() > 0) {

                for (Long key : catAmountMap.keySet()) {

                    Category category = contentProvider.getCategoryForId(key);

                    PieSlice slice = new PieSlice();

                    slice.setColor(Color.parseColor(category.getColor()));
                    slice.setTitle(category.getName());
                    slice.setValue(catAmountMap.get(key).floatValue());

                    pg.addSlice(slice);
                    pg.setInnerCircleRatio(200);
                    pg.setPadding(5);

                }

                final Activity activ = activity;

                pg.setOnSliceClickedListener(new PieGraph.OnSliceClickedListener() {

                    @Override
                    public void onClick(int index) {

                        if (index != -1) {
                            String value = Float.toString(pg.getSlice(index)
                                    .getValue());
                            String title = pg.getSlice(index).getTitle()
                                    .toString();

                            text.setText(title + "\n " + value);

                            text.setTextColor(pg.getSlice(index).getColor());

                            text.setShadowLayer(2, 2, 2, Color.BLACK);

                            text.setGravity(Gravity.CENTER_HORIZONTAL);

                            //list.setAdapter(new ListAdapter(activ, expensesForChart));

                        }

                    }
                });


            }
        } else {

            pg.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
            dateText1.setVisibility(View.GONE);
            dateText2.setVisibility(View.GONE);
            textTo.setVisibility(View.GONE);
            textFrom.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);

            bg.setVisibility(View.VISIBLE);
            bartext.setVisibility(View.VISIBLE);
            dateText1.setVisibility(View.GONE);
            bartext.setText(selectedCategory.getName());
            View.OnClickListener first=new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCategoryList(activity);
                }
            };
            bartext.setOnClickListener(first);

            final ArrayList<Bar> points = new ArrayList<>();
            ArrayList<GraphValue> expense=getExpensesPerMonths(new Date());
            for (GraphValue gv:expense) {
                Bar b=new Bar();
                b.setColor(Color.parseColor(selectedCategory.getColor()));
                b.setName(gv.getMonth() + " " + gv.getYear());
                b.setValue(gv.getAmount());
                b.setValuePrefix("$");

                points.add(b);
            }

            bg.setBars(points);
        }


    }

    public Map<Long, Double> getExpensesCategoryWithAmount(ArrayList<Money> money) {
        Map<Long, Double> categoryMap = new HashMap<>();
        if (money.size() > 0) {

            for (Money m : money) {
                if (categoryMap.containsKey(m.getCategory())) {

                    double amount = categoryMap.get(m.getCategory());
                    categoryMap.put(m.getCategory(), amount + m.getAmount());
                } else {

                    categoryMap.put(m.getCategory(), m.getAmount());
                }

            }
        }
        return categoryMap;
    }

    public String getPeriod(Date dateBefore, Date dateAfter) {

        String period = "other";
        Calendar calBefore = Calendar.getInstance();
        Calendar calAfter = Calendar.getInstance();

        calBefore.setTime(dateBefore);
        calAfter.setTime(dateAfter);

        if (calBefore.compareTo(substractWeek(calAfter)) == 0) {
            Log.w("Period", "Week");
            period = "week";
        }
        calAfter.setTime(dateAfter);
        if (calBefore.compareTo(substractMonth(calAfter)) == 0) {
            Log.w("Period", "Month");
            period = "month";

        }
        calAfter.setTime(dateAfter);
        if(calBefore.compareTo(substractYear(calAfter))==0){
            Log.w("Period", "Year");
            period="year";
        }

        return period;
    }

    public Calendar substractWeek(Calendar calendar) {
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        return calendar;
    }

    public Calendar substractMonth(Calendar calendar) {
        calendar.add(Calendar.MONTH, -1);
        return calendar;
    }
    public Calendar substractYear(Calendar calendar){
        calendar.add(Calendar.YEAR,-1);
        return calendar;
    }

    public ArrayList<GraphValue> getExpensesPerMonths(Date date) {

        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, -1);
        ArrayList<GraphValue>expenses = new ArrayList<>();
        Calendar tempCalendar=Calendar.getInstance();
        tempCalendar.setTime(date);
        int month=0;
        double amount=0;


        for(int j=0;j<5;j++){
            month=tempCalendar.get(Calendar.MONTH)+1;


            amount= contentProvider.getAmountMonth(month,Integer.valueOf(tempCalendar.get(Calendar.YEAR)),null);
            GraphValue gv=new GraphValue(getMonths(month),String.valueOf(tempCalendar.get(Calendar.YEAR)),(float)amount);
            expenses.add(gv);
            tempCalendar.add(Calendar.MONTH, -1);


        }



        return expenses;

    }

    public Date substractOneMonthFromDate(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,-1);
        return new Date(calendar.getTimeInMillis());
    }


    public void openCategoryList(Activity activity) {
        ArrayList<Category> dbList=contentProvider().getCategories(activity);
        ArrayList<Category> categoryList = new ArrayList<Category>();
        Dialog categoryDialog = new Dialog(activity);
        MyListViewAdapter adapter = new MyListViewAdapter(activity,categoryList,categoryDialog);

        categoryDialog.setContentView(R.layout.category_list);

        ListView categories = (ListView) categoryDialog.findViewById(R.id.lv);
        categoryDialog.setCancelable(true);

        categoryDialog.setTitle("Categories");

        categories.setAdapter(adapter);

        for (int i = 0; i < dbList.size(); i++) {
            Category cat = dbList.get(i);
            categoryList.add(cat);
            adapter.notifyDataSetChanged();

        }

        categoryDialog.show();

    }

    private class MyListViewAdapter extends BaseAdapter {

        private Context context;
        private Activity activity;
        private ArrayList<Category> listData;
        private LayoutInflater inflater = null;
        private Dialog categoryDialog;


        protected MyListViewAdapter(Activity activity, ArrayList<Category> data,Dialog categoryDialog) {
            this.activity=activity;
            context=activity;
            this.listData = data;
            this.categoryDialog=categoryDialog;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {

            return listData.size();
        }

        @Override
        public Object getItem(int position) {

            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {

            return listData.get(position).getCategory_id();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View returningView = convertView;
            if (returningView == null) {
                returningView = inflater.inflate(R.layout.list_row_settings, null);

            }
            TextView text = (TextView) returningView
                    .findViewById(R.id.cat_text);
            String textString = listData.get(position).getName();
            text.setText(textString);

            Button imgBtn = (Button) returningView.findViewById(R.id.img_btn);
            imgBtn.setText(listData.get(position).getName().substring(0, 1));
            imgBtn.setBackgroundColor(Color.parseColor(
                    listData.get(position).getColor()));
            returningView.setTag(listData.get(position).getCategory_id());

            returningView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    initiateChart(activity, contentProvider().getCategoryForId((Long) v.getTag()), null, null);

                    categoryDialog.dismiss();


                }
            });

            return returningView;
        }

    }


}
