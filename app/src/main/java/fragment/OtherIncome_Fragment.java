package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.incomemanager.R;

import incomemanager.Income_Activity;
import model.Category;
import service.Service;

public class OtherIncome_Fragment extends Fragment {
	
	private EditText dateField;
	private Spinner spinnerCat;
	private Service service=Service.getInstance();
	
	

	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
		 getActivity().getWindow().setSoftInputMode(
		            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	        return inflater.inflate(R.layout.other_income_fragment,container,false);
	    }
	 @Override
	public void onStart() {
		 spinnerCat=(Spinner)getView().findViewById(R.id.spinner_category_income);
		 service.populateSpinnerCategory(spinnerCat,getActivity());

		 spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			 Category cat;

			 @Override
			 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				 cat = (Category) parent.getItemAtPosition(position);
				 ((Income_Activity) getActivity()).setCategory((cat.getCategory_id()));
			 }

			 @Override
			 public void onNothingSelected(AdapterView<?> parent) {
				 cat = (Category) parent.getItemAtPosition(0);
				 ((Income_Activity) getActivity()).setCategory((cat.getCategory_id()));

			 }
		 });
		dateField=(EditText)getView().findViewById(R.id.date);

		service.initiateDate(dateField, getActivity());				
		super.onStart();
	}





	 
	 
}
