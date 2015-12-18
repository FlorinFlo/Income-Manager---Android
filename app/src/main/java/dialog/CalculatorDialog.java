package dialog;

import android.app.Dialog;
import android.content.Context;

import com.example.incomemanager.R;

/**
 * Created by Florea on 12/16/2015.
 */
public class CalculatorDialog extends Dialog {

    public CalculatorDialog(Context context){
        super(context);
        this.setContentView(R.layout.calculator_fragment);
        this.setTitle("Calculator");
        this.show();

    }



}
