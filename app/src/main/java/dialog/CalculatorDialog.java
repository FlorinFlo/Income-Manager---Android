package dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.incomemanager.R;

/**
 * Created by Florea on 12/16/2015.
 */
public class CalculatorDialog extends DialogFragment implements View.OnClickListener {

    private Button one, two,
            three, four, five, six, seven,
            eight, nine, zero, clear, div,
            mul, add, sub, equal, dot;
    private EditText display;
    private String operator1 = null;
    private Double operand1 = 0.0;
    private Double operand2 = 0.0;


    public CalculatorDialog() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calculator_fragment, container);
        getDialog().setTitle("Calculator");
        initiateCalculator(view);
        return view;
    }

    @Override
    public void onClick(View v) {
        Double result = null;
        String displayText = display.getText().toString();
        switch (v.getId()) {
            case R.id.btn0:
                if (!displayText.equals("0")) {
                    display.append("0");
                }

                break;
            case R.id.btn1:
                display.append("1");
                break;
            case R.id.btn2:
                display.append("2");
                break;
            case R.id.btn3:
                display.append("3");
                break;
            case R.id.btn4:
                display.append("4");
                break;
            case R.id.btn5:
                display.append("5");
                break;
            case R.id.btn6:
                display.append("6");
                break;
            case R.id.btn7:
                display.append("7");
                break;
            case R.id.btn8:
                display.append("8");
                break;
            case R.id.btn9:
                display.append("9");
                break;
            case R.id.btn_clear:

                if (display.getText().length() > 0 && !displayText.equals("0.")) {


                    if (checkForSymbols(displayText)) {
                        operator1 = null;
                    }

                    display.setText(display.getText().
                            delete(display.getText().length() - 1, display.getText().length()));


                } else if (display.getText().length() == 2 && displayText.equals("0.")) {
                    display.setText("");
                    operator1 = null;
                    operand1 = null;
                    operand2 = null;
                } else if (display.getText().length() == 0) {
                    operand1 = null;
                    operator1 = null;
                    operand2 = null;
                }

                break;
            case R.id.btn_dot:
                if (display.getText().length() == 0) {
                    display.append("0.");
                } else if (countDot(displayText) == 0) {
                    display.append(".");

                } else if (operand1 != null && operator1 != null && countDot(displayText) == 1) {
                    if (displayText.substring(displayText.length() - 1, displayText.length()).matches("\\d")) {
                        display.append(".");
                    } else {
                        display.append("0.");
                    }

                }
                break;
            case R.id.btn_divide:
            case R.id.btn_substraction:
            case R.id.btn_multiply:
            case R.id.btn_addition:
            case R.id.btn_equal:


                if (operator1 == null) {


                    switch (v.getId()) {
                        case R.id.btn_divide:

                            if (displayText.matches(".*\\d+.*")) {
                                operator1 = "÷";
                                operand1 = Double.valueOf(displayText);
                                display.append("÷");

                            }
                            break;
                        case R.id.btn_substraction:

                            if (displayText.length() == 0) {
                                display.append("-");
                            } else {

                                operator1 = "-";
                                operand1 = Double.valueOf(displayText);
                                display.append("-");

                            }

                            break;
                        case R.id.btn_multiply:

                            if (displayText.matches(".*\\d+.*")) {
                                operator1 = "*";
                                operand1 = Double.valueOf(displayText);
                                display.append("*");

                            }

                            break;
                        case R.id.btn_addition:
                            if (displayText.matches(".*\\d+.*")) {
                                operator1 = "+";
                                operand1 = Double.valueOf(displayText);
                                display.append("+");
                            }
                            break;

                    }


                } else if (operator1 != null && operand1 != null) {


                    try {

                        operand2 = Double.valueOf(displayText.
                                substring(displayText.lastIndexOf(operator1) + 1, displayText.length()));
                    } catch (NumberFormatException ex) {
                        Toast toast = Toast.makeText(getActivity(), ex + " ", Toast.LENGTH_LONG);
                        operand2 = null;
                    }

                    if (operand2 != null) {
                        result = operate(operator1, operand1, operand2);
                        switch (v.getId()) {
                            case R.id.btn_divide:
                                operator1 = "÷";
                                display.append("÷");
                                break;
                            case R.id.btn_substraction:
                                operator1 = "-";
                                display.append("-");
                                break;
                            case R.id.btn_multiply:
                                operator1 = "*";
                                display.append("*");
                                break;
                            case R.id.btn_addition:
                                operator1 = "+";
                                display.append("+");
                                break;
                            case R.id.btn_equal:
                                operator1 = null;
                                break;

                        }
                        if (operator1 != null) {
                            display.setText(String.format("%.1f", result) + operator1);
                        } else {
                            display.setText(String.format("%.1f", result));
                        }

                        operand1 = result;
                        operand2 = null;

                    } else {
                        display.setText(display.getText().
                                delete(display.getText().length() - 1, display.getText().length()));
                        switch (v.getId()) {
                            case R.id.btn_divide:
                                operator1 = "÷";
                                display.append("÷");
                                break;
                            case R.id.btn_substraction:
                                operator1 = "-";
                                display.append("-");
                                break;
                            case R.id.btn_multiply:
                                operator1 = "*";
                                display.append("*");
                                break;
                            case R.id.btn_addition:
                                operator1 = "+";
                                display.append("+");
                                break;
                            case R.id.btn_equal:
                                operator1 = null;
                                break;

                        }

                    }
                }

        }


    }

    private void initiateCalculator(View view) {
        one = (Button) view.findViewById(R.id.btn1);
        two = (Button) view.findViewById(R.id.btn2);
        three = (Button) view.findViewById(R.id.btn3);
        four = (Button) view.findViewById(R.id.btn4);
        five = (Button) view.findViewById(R.id.btn5);
        six = (Button) view.findViewById(R.id.btn6);
        seven = (Button) view.findViewById(R.id.btn7);
        eight = (Button) view.findViewById(R.id.btn8);
        nine = (Button) view.findViewById(R.id.btn9);
        zero = (Button) view.findViewById(R.id.btn0);
        clear = (Button) view.findViewById(R.id.btn_clear);
        div = (Button) view.findViewById(R.id.btn_divide);
        mul = (Button) view.findViewById(R.id.btn_multiply);
        add = (Button) view.findViewById(R.id.btn_addition);
        sub = (Button) view.findViewById(R.id.btn_substraction);
        dot = (Button) view.findViewById(R.id.btn_dot);
        equal = (Button) view.findViewById(R.id.btn_equal);
        display = (EditText) view.findViewById(R.id.display);
        try {

            one.setOnClickListener(this);
            two.setOnClickListener(this);
            three.setOnClickListener(this);
            four.setOnClickListener(this);
            five.setOnClickListener(this);
            six.setOnClickListener(this);
            seven.setOnClickListener(this);
            eight.setOnClickListener(this);
            nine.setOnClickListener(this);
            zero.setOnClickListener(this);
            clear.setOnClickListener(this);
            div.setOnClickListener(this);
            mul.setOnClickListener(this);
            add.setOnClickListener(this);
            sub.setOnClickListener(this);
            dot.setOnClickListener(this);
            equal.setOnClickListener(this);
            clear.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    display.setText("");
                    return true;
                }
            });

        } catch (Exception ex) {
            Toast toast = Toast.makeText(getActivity(), ex + " ", Toast.LENGTH_LONG);
        }
    }

    private double operate(String operator, double operand1, double operand2) {
        if (operator.equals("+")) {
            return operand1 + operand2;
        } else if (operator.equals("-")) {
            return operand1 - operand2;
        } else if (operator.equals("÷")) {
            return operand1 / operand2;
        } else if (operator.equals("*")) {
            return operand1 * operand2;
        }
        return 0;
    }

    private boolean checkForSymbols(String text) {
        if (text.substring(text.length() - 1, text.length()).equals("+")) {
            return true;
        } else if (text.substring(text.length() - 1, text.length()).equals("-")) {
            return true;
        } else if (text.substring(text.length() - 1, text.length()).equals("*")) {
            return true;
        } else if (text.substring(text.length() - 1, text.length()).equals("÷")) {
            return true;
        }
        return false;
    }

    private int countDot(String text) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '.') {
                count++;
            }
        }
        return count;
    }


}