package dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.incomemanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import model.Money;
import service.Service;

/**
 * Created by Florea on 1/13/2016.
 */
public class NotificationDialog extends DialogFragment implements View.OnClickListener {

    private Money money = null;
    private TextView title;
    private EditText amount;
    private EditText notes;
    private Button post;
    private Button paid;
    private Button delete;
    private Service service = Service.getInstance();
    private DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private int hour = -1;
    private int minutes = 0;

    public NotificationDialog() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_dialog, container);
        getDialog().setTitle("Notification");
        money = getArguments().getParcelable("Money");
        initiate(view);

        return view;
    }

    private void initiate(View view) {
        title = (TextView) view.findViewById(R.id.not_info);
        amount = (EditText) view.findViewById(R.id.not_amount);
        notes = (EditText) view.findViewById(R.id.text_not_notes);
        post = (Button) view.findViewById(R.id.post);
        paid = (Button) view.findViewById(R.id.paid);
        delete = (Button) view.findViewById(R.id.delete);
        title.setText("You have an " + money.getType() + " due");
        amount.setText(String.valueOf(money.getAmount()));
        notes.setText(money.getNotes());
        try {
            post.setOnClickListener(this);
            paid.setOnClickListener(this);
            delete.setOnClickListener(this);
        } catch (Exception ex) {
            Log.w("Exception", ex);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post:
                final Calendar myCalendar = Calendar.getInstance();

                money.setNotes(notes.getText().toString());
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Update text


                        money.setDate(new Date(myCalendar.getTimeInMillis()));

                        pickTime();

                    }

                };


                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                break;
            case R.id.paid:

                if (service.contentProvider().updateStatus(money, getActivity())) {
                    Log.w("Paid", "Yes");
                    Toast toast = Toast.makeText(getActivity(), "The record of this amount has been marked as paid",
                            Toast.LENGTH_LONG);
                    toast.show();
                    getDialog().dismiss();


                } else {
                    Log.w("Paid", "No");
                    Toast toast = Toast.makeText(getActivity(), "There has been a problem in deleting the record",
                            Toast.LENGTH_LONG);
                    toast.show();

                }

                break;
            case R.id.delete:
                if (service.contentProvider().deleteIncExp(money)) {
                    Toast toast = Toast
                            .makeText(getActivity(), "The record of this amount has been deleted",
                                    Toast.LENGTH_LONG);
                    toast.show();
                    getDialog().dismiss();
                } else {
                    Toast toast = Toast
                            .makeText(getActivity(), "There has been a problem with the deletion of the record",
                                    Toast.LENGTH_LONG);
                    toast.show();
                }

                break;
        }
    }

    private void pickTime() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                minutes = minute;
                if (hour != -1) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    if (minutes < 0) {
                        alertDialog.setMessage("You have postponed the notification on the " + money.getDate().toString() + " at the hour " + hour + ":0" + minutes);
                    } else {
                        alertDialog.setMessage("You have postponed the notification on the " + money.getDate().toString() + " at the hour " + hour + ":" + minutes);
                    }

                    alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            service.contentProvider().updateOnPostpone(money);

                            service.postponeAlarm(getActivity(), money, hour, minutes);
                            dialog.cancel();
                            getDialog().dismiss();

                        }
                    });
                    alertDialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            getDialog().dismiss();
                        }
                    });

                    AlertDialog alert = alertDialog.create();
                    alert.show();
                }

            }
        }, service.getTimeNow(calendar)[0], service.getTimeNow(calendar)[1], true);
        timePickerDialog.show();

    }
}
