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
public class ListAdapterExpense extends BaseAdapter  {
    private Context context;
    private Money[] listArray;
    private Service service= Service.getInstance();
    private  static LayoutInflater inflater=null;


    public ListAdapterExpense(Activity activity, ArrayList<Money> list){
             context=activity;
            listArray =service.populateArray(listArray,list);




    }

    @Override
    public int getCount() {

            return listArray.length;


    }

    @Override
    public Object getItem(int position) {

            return listArray[position];

    }

    @Override
    public long getItemId(int position) {

        return listArray[position].getMoney_id();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);




                convertView=inflater.inflate(R.layout.list_row_expenses,parent,false);

                TextView catName= (TextView) convertView.findViewById(R.id.category_expense_name);
                TextView catDate= (TextView)convertView.findViewById(R.id.category_expense_date);
                TextView catSum= (TextView) convertView.findViewById(R.id.category_expense_sum);

                String categoryName=service.contentProvider().getCategoryForId(listArray[position].getCategory()).getName();
                Log.w("CATEGORY NAME",categoryName+"");
                String []name=categoryName.split("\\s+");
                if(name.length==2){
                    catName.setText(name[0]+"\n"+name[1]);

                }else if(name.length==3){
                    catName.setText(name[0]+"\n"+name[1]+"\n"+name[2]);
                }
                else {
                    catName.setText(categoryName);
                }

                String date=service.getStringFromDate(listArray[position].getDate());
                catDate.setText(date);
                catSum.setText(String.valueOf(listArray[position].getAmount()));

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
