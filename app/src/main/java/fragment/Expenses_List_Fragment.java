package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.incomemanager.R;

import java.util.Date;

import model.Category;
import service.Service;

;

/**
 * Created by Florea on 10/21/2015.
 */
public class Expenses_List_Fragment extends Fragment {

    private Service service = Service.getInstance();
    private Category category;
    private String periodChart = "week";
    private Date date1;
    private Date date2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        return inflater.inflate(R.layout.expenses_list_fragment, container, false);


    }

    @Override
    public void onStart() {

        final Spinner cat = (Spinner) getActivity().findViewById(R.id.spinner_category);
        final EditText dateBefore = (EditText) getActivity().findViewById(R.id.date_before);
        final EditText dateAfter = (EditText) getActivity().findViewById(R.id.date_after);
        final Spinner period = (Spinner) getActivity().findViewById(R.id.spinner_period);
        final Date date = new Date();


        service.initiateDate(dateAfter, getActivity(), date);
        service.initiateDate(dateBefore, getActivity(), date);

        service.populateSpinnerCategory(cat, getActivity());
        category = (Category) cat.getSelectedItem();

        service.initiateChart(getActivity(), category, date, date);


        dateBefore.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {




            }

            @Override
            public void afterTextChanged(Editable s) {
                String stringPeriod = service.getPeriod(service.getDateFromString(dateBefore.getText().toString()), service.getDateFromString(dateAfter.getText().toString()));
                setSpinner(stringPeriod, period);
                updateDates(dateBefore, dateAfter);
                service.initiateChart(getActivity(), category, date1, date2);
            }
        });

        dateAfter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String stringPeriod = service.getPeriod(service.getDateFromString(dateBefore.getText().toString()), service.getDateFromString(dateAfter.getText().toString()));
                setSpinner(stringPeriod, period);
                updateDates(dateBefore, dateAfter);
                service.initiateChart(getActivity(), category, date1, date2);
            }
        });


        period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (period.getItemAtPosition(position).equals("Week")) {
                    periodChart = "week";

                    service.initiateDate(dateBefore, getActivity(), service.removePeriodFromDate(date, periodChart));
                    updateDates(dateBefore, dateAfter);
                    service.initiateChart(getActivity(), category, date1, date2);
                } else if (period.getItemAtPosition(position).equals("Month")) {
                    periodChart = "month";

                    service.initiateDate(dateBefore, getActivity(), service.removePeriodFromDate(date, periodChart));
                    updateDates(dateBefore, dateAfter);
                    service.initiateChart(getActivity(), category, date1, date2);
                } else if (period.getItemAtPosition(position).equals("Year")) {
                    periodChart = "year";

                    service.initiateDate(dateBefore, getActivity(), service.removePeriodFromDate(date, periodChart));
                    updateDates(dateBefore, dateAfter);
                    service.initiateChart(getActivity(), category, date1, date2);
                } else if (period.getItemAtPosition(position).equals("Period")) {

                    updateDates(dateBefore, dateAfter);
                    service.initiateChart(getActivity(), category, date1, date2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.w("Hello", "Other0");

            }
        });
        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = (Category) cat.getItemAtPosition(position);
                updateDates(dateBefore, dateAfter);
                service.initiateChart(getActivity(), category, date1, date2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        super.onStart();
    }

    public void setSpinner(String stringPeriod, Spinner spinner) {

        if (stringPeriod.equals("week")) {
            Log.v("Week", "Weeek???");
            spinner.setSelection(1);
        } else if (stringPeriod.equals("month")) {
            Log.v("Month", "Month???");
            spinner.setSelection(2);
        } else {
            Log.v("Else", "Else???");
            spinner.setSelection(0);
        }

    }

    public void updateDates(EditText dateBefore, EditText dateAfter) {
        date1 = service.getDateFromString(dateBefore.getText().toString());
        date2 = service.getDateFromString(dateAfter.getText().toString());
    }
}
