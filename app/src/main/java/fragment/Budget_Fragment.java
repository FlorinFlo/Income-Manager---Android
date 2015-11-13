package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.example.incomemanager.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        Button btn= (Button) getActivity().findViewById(R.id.today_expenses);
        Button btnBalance= (Button) getActivity().findViewById(R.id.current_balance);
        btn.append(service.appendTextColor("5000","#ff0000"));
        Date date=new Date();
        //contentProvider.getExpenses();

        contentProvider.updateBalance(10,date);
        contentProvider.getSavedBalance(getActivity(), date);





    }
    public void updateTime(){
        calendar= Calendar.getInstance();
        curentMonth=calendar.get(Calendar.MONTH)+1;
    }
}

