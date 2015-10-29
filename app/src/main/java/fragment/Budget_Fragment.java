package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.incomemanager.R;

import java.util.ArrayList;
import java.util.Calendar;

import contentprovider.MyMonetaryContentProvider;
import model.Money;
import service.Service;

/**
 * Created by Florea on 10/21/2015.
 */
public class Budget_Fragment extends Fragment {

    private int curentMonth=0;
    private Calendar calendar;
    private ArrayList<Money> expenses;
    private Service service = Service.getInstance();
    private MyMonetaryContentProvider contentProvider=service.contentProvider();
    private String [] budgetArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        return inflater.inflate(R.layout.budget_fragment,container,false);
    }

    @Override
    public void onStart(){
    super.onStart();
        updateTime();
        expenses=contentProvider.getExpensesGivingMonth(curentMonth);






    }
    public void updateTime(){
        calendar= Calendar.getInstance();
        curentMonth=calendar.get(Calendar.MONTH)+1;

    }
}

