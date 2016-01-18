package fragment;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.example.incomemanager.R;

import java.util.Calendar;
import java.util.Date;

import incomemanager.Expenses_Activity;
import model.Category;
import service.Service;

public class Bills_Fragment extends Fragment {
	
	private EditText dateField;
	private TextView timeNow;
	private Spinner spinnerCat;
	private int counter = 0;
	private ToggleButton togggle;
	private int hour;
	private int minute;
	private Calendar calendar;
	private boolean repeatAlarm=false;
	
	private Service service=Service.getInstance();
	private NumberPicker no;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
		getActivity().getWindow().setSoftInputMode(
	            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return inflater.inflate(R.layout.bills_fragment,container,false);
    }

	@Override
	public void onStart() {

		counter =0;
		timeNow= (TextView) getView().findViewById(R.id.time_now);
		service.setTime(timeNow,service.getTimeNow(calendar)[0],service.getTimeNow(calendar)[1]);
		spinnerCat=(Spinner)getView().findViewById(R.id.bill_category);
		service.populateSpinnerCategory(spinnerCat,getActivity());

		spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			Category cat;

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				cat = (Category) parent.getItemAtPosition(position);
				((Expenses_Activity) getActivity()).setCategory(cat.getCategory_id());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				cat = (Category) parent.getItemAtPosition(0);
				((Expenses_Activity) getActivity()).setCategory((cat.getCategory_id()));

			}
		});
		dateField=(EditText) getView().findViewById(R.id.bill_date);
		service.initiateDate(dateField, getActivity(),new Date());

		togggle= (ToggleButton) getView().findViewById(R.id.toggle_repeat);
		togggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				counter++;
				if(counter%2!=0){


					repeatAlarm=true;

					TimePickerDialog timePicker=new TimePickerDialog(getActivity(),new TimePickerDialog.OnTimeSetListener(){

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
							service.setTime(timeNow, hourOfDay, minute);
							((Expenses_Activity) getActivity()).setTime(hourOfDay,minute);


						}
					},service.getTimeNow(calendar)[0],service.getTimeNow(calendar)[1],true);
					timePicker.setTitle("Select time");
					timePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {
							togggle.setChecked(false);
						}
					});

					timePicker.show();
					((Expenses_Activity) getActivity()).setReapeatingAlarm(repeatAlarm);

				}else{
					repeatAlarm=false;

					((Expenses_Activity) getActivity()).setReapeatingAlarm(repeatAlarm);

					service.setTime(timeNow, service.getTimeNow(calendar)[0], service.getTimeNow(calendar)[1]);
				}


			}
		});
		
		
		super.onStart();
	}


}
