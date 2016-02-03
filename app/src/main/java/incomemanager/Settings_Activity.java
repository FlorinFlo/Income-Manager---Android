package incomemanager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.incomemanager.R;

import java.util.ArrayList;

import contentprovider.MyMonetaryContentProvider;
import dialog.NewCategoryDialog;
import model.Category;
import service.Service;

public class Settings_Activity extends ActionBarActivity {

    private ActionBar actionBar;
    private Dialog categoryDialog;

    private Service service = Service.getInstance();
    private MyMonetaryContentProvider contentProvider = service.contentProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#606060")));
        actionBar.setDisplayHomeAsUpEnabled(true);

        return super.onCreateOptionsMenu(menu);
    }

    public void openEditCat(View v) {
        ArrayList<Category> dbList = contentProvider.getCategories(this);
        ArrayList<Category> categoryList = new ArrayList<Category>();
        MyListViewAdapter adapter = new MyListViewAdapter(this, categoryList);

        categoryDialog = new Dialog(this);
        categoryDialog.setContentView(R.layout.category_list);

        ListView categories = (ListView) categoryDialog.findViewById(R.id.lv);
        categoryDialog.setCancelable(true);

        categoryDialog.setTitle("Categories");

        categories.setAdapter(adapter);
        Category newCategory = new Category(0, "New category",
                "#FFFFFF");
        categoryList.add(newCategory);


        for (int i = 0; i < dbList.size(); i++) {
            Category cat = dbList.get(i);
            categoryList.add(cat);
            adapter.notifyDataSetChanged();

        }

        categoryDialog.show();

    }

    public void onClick(AdapterView<?> parent, View view, int position, long id) {

        CharSequence text = "Item clicked" + view.getTag();
    }

    private class MyListViewAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<Category> listData;
        private LayoutInflater inflater = null;

        protected MyListViewAdapter(Context context, ArrayList<Category> data) {

            this.context = context;
            this.listData = data;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {

            return listData.size();
        }

        @Override
        public Object getItem(int position) {

            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {

            return listData.get(position).getCategory_id();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View returningView = convertView;
            if (returningView == null) {
                returningView = inflater.inflate(R.layout.list_row_settings, null);

            }
            TextView text = (TextView) returningView
                    .findViewById(R.id.cat_text);
            String textString = listData.get(position).getName();
            text.setText(textString);

            Button imgBtn = (Button) returningView.findViewById(R.id.img_btn);
            imgBtn.setText(listData.get(position).getName().substring(0, 1));
            imgBtn.setBackgroundColor(Color.parseColor(
                    listData.get(position).getColor()));
            returningView.setTag(listData.get(position).getCategory_id());

            returningView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (v.getTag().toString().equals("0")) {

                        categoryDialog.dismiss();
                        NewCategoryDialog d = new NewCategoryDialog(context, null);

                    }


                }
            });

            return returningView;
        }

    }
}
