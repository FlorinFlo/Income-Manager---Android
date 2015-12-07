package service;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.incomemanager.R;

import java.util.ArrayList;

import model.Money;

/**
 * Created by Florea on 10/20/2015.
 */
public class ListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Money> moneyList;
    private Service service = Service.getInstance();
    private static LayoutInflater inflater = null;


    public ListAdapter(Activity activity, ArrayList<Money> list) {
        context = activity;
        moneyList = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.w("Hello", "Adapter" + moneyList.size());


    }

    @Override
    public int getCount() {

        return moneyList.size();


    }

    @Override
    public Object getItem(int position) {

        return moneyList.get(position);

    }

    @Override
    public long getItemId(int position) {

        return moneyList.get(position).getMoney_id();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            String categoryName;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.w("getView", "View");

            convertView = inflater.inflate(R.layout.list_row, null);

            TextView catName = (TextView) convertView.findViewById(R.id.note);
            TextView catDate = (TextView) convertView.findViewById(R.id.date);
            TextView catSum = (TextView) convertView.findViewById(R.id.amount);
            if (moneyList.get(position).getCategory() == 0) {
                categoryName = "Salary";
            } else {
                categoryName = service.contentProvider().getCategoryForId(moneyList.get(position).getCategory()).getName();
            }
            Log.w("CATEGORY NAME", categoryName + "");


            String[] name = categoryName.split("\\s+");
            if (name.length == 2) {
                catName.setText(name[0] + "\n" + name[1]);

            } else if (name.length == 3) {
                catName.setText(name[0] + "\n" + name[1] + "\n" + name[2]);
            } else {
                catName.setText(categoryName);
            }

            String date = service.getStringFromDate(moneyList.get(position).getDate());
            catDate.setText(date);
            String amount=String.valueOf(moneyList.get(position).getAmount());

            catSum.setText(amount);


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "You clicked", Toast.LENGTH_LONG).show();
                }
            });

        }


        return convertView;
    }
}
