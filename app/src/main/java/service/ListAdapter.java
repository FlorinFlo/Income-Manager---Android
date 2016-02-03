package service;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        String categoryName = "";


        if (convertView == null) {


            convertView = this.inflater.inflate(R.layout.list_row, null);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);


        } else {

            viewHolder = (MyViewHolder) convertView.getTag();
        }


        if (moneyList.get(position).getCategory() == 0) {
            categoryName = "Salary";
        } else {
            categoryName = service.contentProvider().getCategoryForId(moneyList.get(position).getCategory()).getName();
        }
        viewHolder.catName.setText(categoryName);

        String date = service.getStringFromDate(moneyList.get(position).getDate());
        viewHolder.catDate.setText(date);

        String amount = String.valueOf(moneyList.get(position).getAmount());

        viewHolder.catSum.setText(amount);


        return convertView;
    }

    private class MyViewHolder {

        TextView catName;
        TextView catDate;
        TextView catSum;


        public MyViewHolder(View item) {

            catName = (TextView) item.findViewById(R.id.note);
            catDate = (TextView) item.findViewById(R.id.date);
            catSum = (TextView) item.findViewById(R.id.amount);
        }

    }
}
