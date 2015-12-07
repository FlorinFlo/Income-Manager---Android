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

import java.util.Date;

import incomemanager.Expenses_Activity;
import model.Category;
import service.Service;

public class Expenses_Fragment extends Fragment {

	private  EditText dateField;
	private Spinner spinnerCategory;

	private Service service = Service.getInstance();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		return inflater.inflate(R.layout.expenses_fragment, container, false);
	}

	@Override
	public void onStart() {
		spinnerCategory=(Spinner)getView().findViewById(R.id.spinner_category_expenses);
		service.populateSpinnerCategory(spinnerCategory,getActivity());
		spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			Category cat;

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				cat = (Category) parent.getItemAtPosition(position);
				((Expenses_Activity) getActivity()).setCategory((cat.getCategory_id()));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				cat = (Category) parent.getItemAtPosition(0);
				((Expenses_Activity) getActivity()).setCategory((cat.getCategory_id()));

			}
		});



		dateField = (EditText) getView().findViewById(R.id.expense_date);
		service.initiateDate(dateField, getActivity(),new Date());

		super.onStart();
	}
}
