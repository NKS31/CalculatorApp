package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTV, solutionTV;
    MaterialButton buttonC, buttonBracketOpen, buttonBracketclose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEqual;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTV = findViewById(R.id.result_tv);
        solutionTV = findViewById(R.id.solution_tv);

        assignID(buttonC, R.id.button_c);
        assignID(buttonBracketOpen, R.id.button_open_parentheses);
        assignID(buttonBracketclose, R.id.button_closed_parentheses);
        assignID(buttonDivide, R.id.button_divide);
        assignID(buttonMultiply, R.id.button_multiply);
        assignID(buttonPlus, R.id.button_plus);
        assignID(buttonMinus, R.id.button_minus);
        assignID(buttonEqual, R.id.button_equal);
        assignID(button0, R.id.button_0);
        assignID(button1, R.id.button_1);
        assignID(button2, R.id.button_2);
        assignID(button3, R.id.button_3);
        assignID(button4, R.id.button_4);
        assignID(button5, R.id.button_5);
        assignID(button6, R.id.button_6);
        assignID(button7, R.id.button_7);
        assignID(button8, R.id.button_8);
        assignID(button9, R.id.button_9);
        assignID(buttonAC, R.id.button_ac);
        assignID(buttonPoint, R.id.button_point);
    }

    void assignID(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTV.getText().toString();

        if(buttonText.equals("AC")) {
            solutionTV.setText("");
            resultTV.setText("0");
            return;
        }

        if(buttonText.equals("=")) {
            solutionTV.setText(resultTV.getText());
            solutionTV.setText("");
            return;
        }

        if(buttonText.equals("C")) {
            if(dataToCalculate.isEmpty()) {
                return;
            } else if (dataToCalculate.length() == 1) {
                solutionTV.setText("");
                dataToCalculate = "";
            } else {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }
        solutionTV.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);
        if(!finalResult.equals("Err")) {
            resultTV.setText(finalResult);
        }
    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if(finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }
}