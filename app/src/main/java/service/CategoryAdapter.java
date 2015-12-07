package service;

import android.R;
import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;


import model.Category;

/**
 * Created by Florea on 11/24/2015.
 */
public class CategoryAdapter extends ArrayAdapter<Category> {


    public CategoryAdapter(Context context, List<Category> objects) {
        super(context, R.id.text1, R.id.text1,objects);
    }

    public CategoryAdapter(Context context,List<Category> objects,int layoutResId){
        super(context,layoutResId,R.id.text1,objects);
    }
    @Override
    public int getCount(){
        int count=super.getCount();
        return count>0 ? count-1:count;
    }


}
