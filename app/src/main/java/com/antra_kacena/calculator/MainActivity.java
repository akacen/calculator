package com.antra_kacena.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.antra_kacena.calculator.databinding.ActivityMainBinding;
import com.antra_kacena.calculator.databinding.ListitemBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private OperatorType operatorType = OperatorType.NONE;
    private boolean isOperatorAssigned = false;
    private boolean clearDigitsBeforeTyping = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonOne.setOnClickListener(view ->
                appendDigit(getTextFromView(view))
        );
        binding.buttonTwo.setOnClickListener(view ->
                appendDigit(getTextFromView(view))
        );
        binding.buttonThree.setOnClickListener(view ->
                appendDigit(getTextFromView(view))
        );
        binding.buttonFour.setOnClickListener(view ->
                appendDigit(getTextFromView(view))
        );
        binding.buttonFive.setOnClickListener(view ->
                appendDigit(getTextFromView(view))
        );
        binding.buttonSix.setOnClickListener(view ->
                appendDigit(getTextFromView(view))
        );
        binding.buttonSeven.setOnClickListener(view ->
                appendDigit(getTextFromView(view))
        );
        binding.buttonEight.setOnClickListener(view ->
                appendDigit(getTextFromView(view))
        );
        binding.buttonNine.setOnClickListener(view ->
                appendDigit(getTextFromView(view))
        );
        binding.buttonZero.setOnClickListener(view ->
                appendDigit(getTextFromView(view))
        );
        binding.buttonEquals.setOnClickListener(view -> {
            double result;
            switch (operatorType) {
                case DIVIDE:
                    result = getLeftNum() / getRightNum();
                    break;
                case MULTIPLY:
                    result = getLeftNum() * getRightNum();
                    break;
                case PLUS:
                    result = getLeftNum() + getRightNum();
                    break;
                default:
                    result = getLeftNum() - getRightNum();
            }
            binding.result.setText(getString(R.string.result, String.format(Locale.US, "%.2f", result)));
            addHistoryItem(getLeftNum(), operatorType.character, getRightNum(), result);
            isOperatorAssigned = false;
            clearDigitsBeforeTyping = true;
        });

        binding.buttonDivide.setOnClickListener(view ->
                setOperator(OperatorType.DIVIDE)
        );
        binding.buttonMultiply.setOnClickListener(view ->
                setOperator(OperatorType.MULTIPLY)
        );
        binding.buttonMinus.setOnClickListener(view ->
                setOperator(OperatorType.MINUS)
        );
        binding.buttonPlus.setOnClickListener(view ->
                setOperator(OperatorType.PLUS)
        );
        binding.buttonClear.setOnClickListener(view -> {
            isOperatorAssigned = false;
            clearDigits();
        });
    }

    private void addHistoryItem(Double leftNum, String operator, Double rightNum, double result) {
        ListitemBinding item = ListitemBinding.inflate(getLayoutInflater());
        item.leftSide.setText(String.format(Locale.US, "%.2f", leftNum));
        item.rightSide.setText(String.format(Locale.US, "%.2f", rightNum));
        item.result.setText(String.format(Locale.US, "%.2f", result));
        item.operator.setText(operator);
        item.getRoot().setOnClickListener(view -> appendDigit(getTextFromView(item.result)));

        binding.history.addView(item.getRoot());
    }

    private String getTextFromView(View view) {
        try {
            return ((TextView) view).getText().toString();
        } catch (ClassCastException e) {
            return "";
        }
    }

    private void setOperator(OperatorType type) {
        if (clearDigitsBeforeTyping) clearDigits();
        operatorType = type;
        isOperatorAssigned = true;
        binding.operator.setText(type.character);
    }

    private void appendDigit(CharSequence digit) {
        if (clearDigitsBeforeTyping) clearDigits();
        String regex = "^0+(?!$)";
        if (isOperatorAssigned) {
            binding.rightSide.append(digit);
            binding.rightSide.setText(getTextFromView(binding.rightSide).replaceAll(regex, ""));
        } else {
            binding.leftSide.append(digit);
            binding.leftSide.setText(getTextFromView(binding.leftSide).replaceAll(regex, ""));
        }
    }

    private void clearDigits() {
        clearDigitsBeforeTyping = false;
        binding.result.setText("");
        binding.operator.setText("");
        binding.rightSide.setText("");
        binding.leftSide.setText("0");
    }

    private Double getLeftNum() {
        return Double.parseDouble(binding.leftSide.getText().toString());
    }

    private Double getRightNum() {
        return Double.parseDouble(binding.rightSide.getText().toString());
    }
}
