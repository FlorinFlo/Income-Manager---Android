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

import contentprovider.MyMonetaryContentProvider;
import incomemanager.MainActivity;
import model.Money;
import service.Service;

/**
 * Created by Florea on 10/21/2015.
 */
public class Budget_Fragment extends Fragment {

    private int curentMonth = 0;
    private Calendar calendar;
    private ArrayList<Money> expenses;
    private Service service = Service.getInstance();
    private MyMonetaryContentProvider contentProvider = service.contentProvider();
    private String[] budgetArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        return inflater.inflate(R.layout.budget_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();




    }

    private void updateTime() {
        calendar = Calendar.getInstance();
        curentMonth = calendar.get(Calendar.MONTH) + 1;
    }

    private void updateBtnWeek(Button button, String type) {
        String amount = String.valueOf(service.getAmountWeek(type));
        service.cleanButton(button, amount);

    }

    private void updateBtnMonth(Button button, String type) {

        String amount = String.valueOf(service.getAmountMonth(type));
        service.cleanButton(button, amount);
    }

    public void onResume(){
        super.onResume();
        updateTime();
        Button btnTExpense = (Button) getActivity().findViewById(R.id.today_expenses);

        Button btnBalance = (Button) getActivity().findViewById(R.id.current_balance);

        Button btnWIncomes = (Button) getActivity().findViewById(R.id.week_incomes);

        Button btnMIncomes = (Button) getActivity().findViewById(R.id.month_incomes);

        Button btnWExpense = (Button) getActivity().findViewById(R.id.week_expenses);

        Button btnMExpense = (Button) getActivity().findViewById(R.id.month_expenses);



        String balance = String.valueOf(((MainActivity) getActivity()).getBalance().getAmount());

        service.cleanButton(btnBalance, balance);

        updateBtnWeek(btnWIncomes, "Income");
        updateBtnMonth(btnMIncomes, "Income");
        updateBtnWeek(btnWExpense, "Expense");
        updateBtnMonth(btnMExpense, "Expense");


        String amountToday = String.valueOf(service.getAmountExpToday());
        service.cleanButton(btnTExpense, amountToday);
    }

}

