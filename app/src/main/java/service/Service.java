package service;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
import database.MonetaryDatabaseHelper;
import dialog.DialogNumberPicker;
import incomemanager.RescheduleAlarm_Activity;
import model.Category;
import model.Money;

public class Service {

    private static Service instance = null;

    private Uri uri = null;

    private SimpleDateFormat dateFormater;

    private  MyMonetaryContentProvider contentProvider ;

    private Date date1;

    private MonetaryDatabaseHelper database;

    private ArrayList<Money> expensesCurentMonth = new ArrayList<Money>();



    private ArrayList<Money> expensesForChart = new ArrayList<Money>();

    private String[] code = new String[]{"Eclair & Older", "Froyo",
            "Gingerbread", "Honeycomb", "IceCream Sandwich", "Jelly Bean"};



    private Calendar myCalendar = Calendar.getInstance();

    int val;

    public Service() {

    }

    public static Service getInstance() {

        if (instance == null) {

            instance = new Service();
        }

        return instance;

    }

    // Initiate a Date Picker on EditText , with Context given

    public void initiateDate(final EditText dateField, final Context context) {

        dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        date1 = new Date();

        dateField.setText(dateFormater.format(date1));

        dateField.setOnClickListener(new OnClickListener() {

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

    public void showNumberPicker(final EditText repeat, final Context context) {

         DialogNumberPicker dialog = new DialogNumberPicker(context,repeat);
    }

    public void updateTextFromDatePicker(EditText text, int val) {
          
        text.setText(String.valueOf(val));

    }

    //check if edittext is empty
    public Boolean emptyText(EditText text) {
        if (text.getText().toString().matches(""))
            return true;

        return false;

    }



    public Date returnDateFromString(String date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date returnedDate = null;
        try {
            returnedDate = format.parse(date);

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return returnedDate;

    }

    //Return hex color from int
    public String returnHexColorFromInt(int color){
        return String.format("#%06X",(0xFFFFFF & color));
    }

    //Returns int color
    public int getColorFromHex(String hexColor){
        return Color.parseColor(hexColor);
    }

    public String getMonths(int month) {

        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "Octomber";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                break;
        }

        return null;

    }

    public MyMonetaryContentProvider contentProvider(){
        contentProvider=new MyMonetaryContentProvider();
        return contentProvider;
    }

    public void populateSpinnerCategory(Spinner spiner,Context context){
        ArrayList<Category> categories_list=this.contentProvider.getCategories(context);
        List<Category> arraySpinner=new ArrayList<Category>();

        for(Category c: categories_list){
            arraySpinner.add(c);
         }
         ArrayAdapter<Category>adapter=new ArrayAdapter<Category>(context,android.R.layout.simple_spinner_item,arraySpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             spiner.setAdapter(adapter);



}

    public void createAlarm(Context context,String rule,String date,String note,int hour,int minute){

            Date dat=this.returnDateFromString(date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.setTime(dat);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);


            AlarmManager alarmMgr= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent=new Intent(context,AlarmReceiver.class);
            intent.putExtra("message",note);
            intent.putExtra("rule", rule);
            intent.putExtra("hour", hour);
            intent.putExtra("minute", minute);
            intent.putExtra("date", this.getStringFromCalendar(calendar));
            PendingIntent alarmIntent=PendingIntent.getBroadcast(context,0,intent,0);
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);




    }

    public void createNotification(Context context,Intent intent){
        Bundle extra=intent.getExtras();
        String message=extra.getString("message");
        String rule=extra.getString("rule");
        int hour=extra.getInt("hour");
        int minute=extra.getInt("minute");
        String date=extra.getString("date");
        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent nextIntent=new Intent(context, RescheduleAlarm_Activity.class);
        nextIntent.putExtra("rule", rule);
        nextIntent.putExtra("message",message);
        nextIntent.putExtra("hour", hour);
        nextIntent.putExtra("minute", minute);
        nextIntent.putExtra("date", date);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pIntent=PendingIntent.getActivity(context, (int) System.currentTimeMillis(),nextIntent,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setContentTitle("Income Manager");
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentText(message);
        builder.setContentIntent(pIntent);
        builder.setAutoCancel(true);
        builder.setSound(sound);


        NotificationManager nm= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, builder.build());






    }

    public void setTime(TextView textTime, int hour, int minute){


        String hours="";
        String minutes="";

        if(minute<10){
            minutes="0"+minute;
        }else{
            minutes=""+minute;
        }

            if(hour>0 && hour<10){

                hours="0"+hour;
            }else if(hour==0){

                hours="00";
            }else{
                hours=""+hour;

            }


        textTime.setText(hours + ":" + minutes);


    }

    public int[] getTimeNow(Calendar calendar){
        int[]time=new int[2];

        calendar=Calendar.getInstance();
        time[0]=calendar.get(Calendar.HOUR_OF_DAY);
        time[1]=calendar.get(Calendar.MINUTE);

        return time;
    }

    public String getStringFromCalendar(Calendar calendar){
        String date=null;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if(calendar!=null){
            date=sdf.format(calendar.getTime());
        }
        return date;
    }

    public void getExpensesCurentMonth(){

        contentProvider().getExpenses();
    }

//    public Category setListenerOnSpinner(Spinner spinner){
//        final Category[] returnedCategory = {null};
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                returnedCategory[0] = (Category) parent.getItemAtPosition(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

    public Map<Long,Double> getExpenseWithSameCategory(ArrayList<Money> expenses){

        Date date=new Date();
        Map<Long,Double> list=new HashMap<Long, Double>();


        for(Money exp:expenses){

            if(exp.getDate().before(date) || exp.getDate().equals(date)){
                if(list.containsKey(exp.getCategory())){

                    double amount=list.get(exp.getCategory());

                    amount+=exp.getAmount();
                    list.put(exp.getCategory(),amount);

                }else{
                    list.put(exp.getCategory(),exp.getAmount());
                }


            }
        }

        return list;
    }

    public Money[] populateArray(Money[] returnedArray,ArrayList<Money> list){
        returnedArray=new Money[list.size()];
//        for(int i=0;i<list.size();i++){
//            returnedArray[i]=list.get(i);
//        }
        list.toArray(returnedArray);
        return returnedArray;
    }


    public String getStringFromDate(Date date){
        String returnString=null;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if(date!=null){
            returnString=sdf.format(date);
        }
        return returnString;


    }

    public Double getExpensesToday(Calendar calendar,ArrayList<Money> expenses){
            double returnedAmount=0;
        for(Money exp:expenses){
            Calendar cal=Calendar.getInstance();
            cal.setTime(exp.getDate());
            if(cal.get(Calendar.DAY_OF_YEAR)==calendar.get(Calendar.DAY_OF_YEAR)){
                returnedAmount+=exp.getAmount();
            }
        }
        return returnedAmount;
    }

}
