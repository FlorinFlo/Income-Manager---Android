package dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.incomemanager.R;

import service.Service;

/**
 * Created by Florea on 10/5/2015.
 */
public class DialogNumberPicker extends Dialog {
    private Service service=Service.getInstance();

    public DialogNumberPicker(Context context,final EditText repeat) {
        super(context);

        this.setTitle("Number Picker");
        this.setContentView(R.layout.number_picker);

        final Button ok = (Button) this.findViewById(R.id.btn_ok);
        Button cancel = (Button) this.findViewById(R.id.btn_cancel);

        final NumberPicker np1 = (NumberPicker) this.findViewById(R.id.np);
        np1.setMaxValue(100);
        np1.setMinValue(1);

        final EditText text = (EditText) np1.getChildAt(0);

        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {

                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                        || (actionId == EditorInfo.IME_ACTION_DONE)) {

                    service.updateTextFromDatePicker(repeat, Integer.parseInt(text.getText().toString()));

                    dismiss();

                    return true;
                }

                return false;
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                service.updateTextFromDatePicker(repeat, Integer.parseInt(text.getText().toString()));

                dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

       this.show();
    }

}
