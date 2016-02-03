package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.incomemanager.R;

import java.util.Date;

import model.Category;
import service.Service;


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
        Date date = new Date();

        final EditText dateBefore = (EditText) getActivity().findViewById(R.id.date_before);
        final EditText dateAfter = (EditText) getActivity().findViewById(R.id.date_after);
        final Spinner period = (Spinner) getActivity().findViewById(R.id.spinner_period);


        // Set selection to month
        period.setSelection(1);


        final RadioButton radioCategory = (RadioButton) getActivity().findViewById(R.id.stats_category);
        final RadioButton radioPeriod = (RadioButton) getActivity().findViewById(R.id.stats_per_period);


        RadioGroup radioGroup = (RadioGroup) getActivity().findViewById(R.id.radio_group);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioPeriod.getId() == checkedId)
                    service.initiateChart(getActivity(), null, date1, date2);

            }
        });
        View.OnClickListener first = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.openCategoryList(getActivity());
            }
        };


        radioCategory.setOnClickListener(first);
        service.initiateDate(dateAfter, getActivity(), date);
        service.initiateDate(dateBefore, getActivity(), service.substractOneMonthFromDate(date));

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
                    updateDates(dateBefore, dateAfter);
                    service.initiateDate(dateBefore, getActivity(), service.removePeriodFromDate(date2, periodChart));

                    service.initiateChart(getActivity(), category, date1, date2);
                } else if (period.getItemAtPosition(position).equals("Month")) {
                    periodChart = "month";
                    updateDates(dateBefore, dateAfter);
                    service.initiateDate(dateBefore, getActivity(), service.removePeriodFromDate(date2, periodChart));

                    service.initiateChart(getActivity(), category, date1, date2);
                } else if (period.getItemAtPosition(position).equals("Year")) {
                    periodChart = "year";
                    updateDates(dateBefore, dateAfter);
                    service.initiateDate(dateBefore, getActivity(), service.removePeriodFromDate(date2, periodChart));


                } else if (period.getItemAtPosition(position).equals("Period")) {

                    updateDates(dateBefore, dateAfter);

                }
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

            spinner.setSelection(0);
        } else if (stringPeriod.equals("month")) {
            spinner.setSelection(1);
        } else if (stringPeriod.equals("year")) {
            spinner.setSelection(2);
        } else {
            spinner.setSelection(3);
        }

    }

    public void updateDates(EditText dateBefore, EditText dateAfter) {
        date1 = service.getDateFromString(dateBefore.getText().toString());
        date2 = service.getDateFromString(dateAfter.getText().toString());
    }


}
