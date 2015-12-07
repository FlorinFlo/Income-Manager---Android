package dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.incomemanager.R;

import graphics.ColorPicker;
import graphics.OpacityBar;
import graphics.SVBar;
import service.Service;

/**
 * Created by Florea on 10/3/2015.
 */
public class NewCategoryDialog extends Dialog implements ColorPicker.OnColorChangedListener {

    private final EditText newCategoryText;
    private SVBar svBar;
    private ColorPicker picker;
    private OpacityBar opacityBar;
    private Button btnOk,btnCancel;
    private Service service=Service.getInstance();
   // private MyMonetaryContentProvider contentProvider=new MyMonetaryContentProvider();
    private String categoryColor;

    public NewCategoryDialog(final Context context, final Spinner spinner) {
        super(context);
        this.setContentView(R.layout.new_cat_dialog);
        this.setTitle("New Category");
        newCategoryText = (EditText) findViewById(R.id.categoryName);
        picker= (ColorPicker) findViewById(R.id.picker);
        svBar= (SVBar) findViewById(R.id.svbar);
        opacityBar= (OpacityBar) findViewById(R.id.opacityBar);
        btnOk= (Button) findViewById(R.id.cat_OK);
        btnCancel = (Button) findViewById(R.id.cat_Cancel);
        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.setOnColorChangedListener(this);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner==null){
                    boolean newCategory = service.contentProvider().createNewCategory(newCategoryText.getText().toString(), categoryColor,context);
                }else{
                    boolean newCategory = service.contentProvider().createNewCategory(newCategoryText.getText().toString(), categoryColor,context);
                    Log.w("",""+newCategory);
                    service.populateSpinnerCategory(spinner,context);
                }

                dismiss();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        this.show();


    }

    @Override
    public void onColorChanged(int color) {
        newCategoryText.setHintTextColor(color);
        newCategoryText.setTextColor(color);
        categoryColor=service.returnHexColorFromInt(color);
    }
}
