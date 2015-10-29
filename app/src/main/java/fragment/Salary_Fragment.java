package fragment;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.example.incomemanager.R;

import java.util.Calendar;

import service.Service;

public class Salary_Fragment extends Fragment {
	
	
	private EditText dateField;
	private EditText repeat;
	private Switch repeatSwitch;
	private ToggleButton togggle;
	private TextView timeNow;
	private int counter = 0;
	private int hour;
	private int minute;
	private Calendar calendar;
	private boolean repeatAlarm=false;
	private OnDataPass dataPass;
	
	private Service service=Service.getInstance();
	//private NumberPicker no;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return inflater.inflate(R.layout.salary_fragment,container,false);
    }
	
	@Override
	public void onStart() {
		counter=0;
		timeNow= (TextView) getView().findViewById(R.id.time_now);
		service.setTime(timeNow,service.getTimeNow(calendar)[0], service.getTimeNow(calendar)[1]);
		dateField=(EditText)getView().findViewById(R.id.salary_date);
		service.initiateDate(dateField, getActivity());
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
							dataPass.onTimePass(hourOfDay,minute);

						}
					},hour,minute,true);
					timePicker.setTitle("Select time");
					timePicker.show();
					dataPass.onDataPass(repeatAlarm);


				}else{
					repeatAlarm=false;
					dataPass.onDataPass(repeatAlarm);

					service.setTime(timeNow, service.getTimeNow(calendar)[0], service.getTimeNow(calendar)[1]);
				}


			}
		});



		
		
		super.onStart();
	}


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		dataPass= (OnDataPass) activity;
	}

	public interface OnDataPass
	{
		public void onDataPass(boolean data);

		public void onTimePass(int hour,int minute);

	}
}
