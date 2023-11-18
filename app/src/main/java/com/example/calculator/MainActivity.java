package com.example.calculator;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    StringBuilder currentNumber = new StringBuilder(); StringBuilder previousNumbers;
    StringBuilder history = new StringBuilder();
    double firstNumer; double secondNumber; double doublResult; boolean isMinusAdded = false;
    String operateValuw; Button operator;
    TextView result; TextView previousnum; TextView operate;
    boolean isButtonclicked; boolean isEqualsclicked;
    HorizontalScrollView horizont2; HorizontalScrollView horizont1;
    int currentScrollX; int scrollToX;
    View historyOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //-------------------------------DEKLARACJA ZMIENNYCH-------------------------------------//


        int orientation = getResources().getConfiguration().orientation;
        historyOption = findViewById(R.id.historyOption);
        horizont1 = findViewById(R.id.horizont1);
        horizont2= findViewById(R.id.horizontal);
        previousnum = findViewById(R.id.previousnum);
        operate = findViewById(R.id.operate);
        result = findViewById(R.id.result);
        Button zero = findViewById(R.id.button16);
        Button dot = findViewById(R.id.button17);
        Button one = findViewById(R.id.button12);
        Button two = findViewById(R.id.button13);
        Button three = findViewById(R.id.button14);
        Button four = findViewById(R.id.button8);
        Button five = findViewById(R.id.button9);
        Button six = findViewById(R.id.button10);
        Button seven = findViewById(R.id.button4);
        Button eight = findViewById(R.id.button5);
        Button nine = findViewById(R.id.button6);
        Button buttonDivide = findViewById(R.id.button3);
        Button buttonAdd = findViewById(R.id.button15);
        Button buttonSubstract = findViewById(R.id.button11);
        Button buttonMultiply = findViewById(R.id.button7);
        Button buttonprocent = findViewById(R.id.button2);
        Button ac = findViewById(R.id.button0);


        //----------------------------przenoszenie miedzy orientacja------------------------------//


        if (savedInstanceState != null) {
            String savedResult = savedInstanceState.getString("resultText");
            String savedCurrentNumber = savedInstanceState.getString("currentnumber");
            String savedPreviousNumber = savedInstanceState.getString("previousNumber");
            String savedOperator = savedInstanceState.getString("operate");
            String savedHistory = savedInstanceState.getString("history");

            if (savedOperator != null) {
                operate.setText(savedOperator);
                operateValuw = savedOperator;
            }
            if (savedResult != null) {
                result.setText(savedResult);
            }
            if(savedCurrentNumber !=null){
                currentNumber = new StringBuilder(savedCurrentNumber);
                result.setText(currentNumber.toString());
            }
            if(savedPreviousNumber !=null){
                previousNumbers = new StringBuilder(savedPreviousNumber);
                previousnum.setText(previousNumbers.toString());
            }else {
                previousNumbers.append("0");
            }
            if(currentNumber.toString().isEmpty()){
                result.setText("0");
            }
            if(!currentNumber.toString().isEmpty() && !previousNumbers.toString().isEmpty()){
                operate.setText(operateValuw);
            }else {
                operate.setText("");
            }
            if(savedHistory !=null){
                history = new StringBuilder(savedHistory);
            }
        }


        //------------------------------------------LICZBY----------------------------------------//


        ac.setText(R.string.AC);
        View.OnClickListener liczby = v -> {
//            System.out.println(Math.pow( 4, 2));
            ac.setText(R.string.C);
            getHorizont();
            if(isButtonclicked){
                currentNumber.delete(0, currentNumber.length());
            }
            isButtonclicked=false;
            Button cyfra = findViewById(v.getId());
            if (currentNumber.toString().contains(".") && currentNumber.toString().equals(".")) {
                result.setText("0.");
                currentNumber.delete(0, currentNumber.length());
                currentNumber.append("0.");
            }
            if (cyfra.getText().toString().equals(".") && currentNumber.toString().contains(".")) {
                return;
            }
            if (cyfra.getText().toString().equals("%") && currentNumber.toString().contains("%")) {
                return;
            }
            if(currentNumber.toString().endsWith("%")) return;
            if(currentNumber.toString().endsWith("π")) return;
            currentNumber.append(cyfra.getText().toString());
            result.setText(currentNumber.toString());
        };


        //--------------------------------------------MINUS---------------------------------------//


        Button minus = findViewById(R.id.button1);
        minus.setOnClickListener(v -> {
            if(currentNumber.toString().equals("%"))return;
            if(isButtonclicked) return;
            if (currentNumber.toString().isEmpty()) {
                return;
            }
            if (currentNumber.toString().equals(".")) {
                currentNumber.delete(0, currentNumber.length());
                currentNumber.append("0.");
            }
            if (isMinusAdded) {
                currentNumber.deleteCharAt(0);

            } else if (currentNumber.toString().equals("")) {
                currentNumber.insert(0, "-");

            } else {
                currentNumber.insert(0, "-");
            }
            isMinusAdded = !isMinusAdded;
            result.setText(currentNumber.toString());
        });


        //-------------------------------------------OPERACJE-------------------------------------//


        View.OnClickListener operacje = v -> {
            if(currentNumber.toString().equals("%")){
                resultonError();
                return;
            }
            isEqualsclicked = false;
            isButtonclicked=true;
            if(currentNumber.toString().equals("-%")) {
                resultonError();
                return;
            }
            if(currentNumber.toString().equals(".")) return;
            if (currentNumber.toString().isEmpty()) return;
            if (currentNumber.toString().charAt(0) == '-' && currentNumber.toString().length() < 2)
                return;

            if (currentNumber.toString().equals("π")) {
                currentNumber.delete(0, currentNumber.length());
                currentNumber.append(Math.PI);
            }
            if (currentNumber.toString().equals("-π")) {
                currentNumber.delete(0, currentNumber.length());
                currentNumber.append(-Math.PI);
            }
            if(currentNumber.toString().endsWith("π")){
                currentNumber.delete(currentNumber.length() - 1, currentNumber.length());
                double currentVal = Double.parseDouble(currentNumber.toString());
                currentVal *= Math.PI;
                currentNumber.setLength(0);
                currentNumber.append(currentVal);
            }
            operator = findViewById(v.getId());
            operate.setText(operator.getText());
            operateValuw = String.valueOf(operator.getText());
            if (currentNumber.toString().equals("")) return;
            previousNumbers = new StringBuilder(currentNumber);
            previousnum.setText(previousNumbers);
            result.setText("0");
            currentNumber.delete(0, currentNumber.length());
            currentNumber.append("0");
        };


        //-----------------------------------------WYNIK------------------------------------------//


        Button equals = findViewById(R.id.button18);
        equals.setOnClickListener(view -> {
            isEqualsclicked = true;
            isButtonclicked=true;

            if(currentNumber.toString().equals("π") && operateValuw==null){
                currentNumber.delete(0,currentNumber.length());
                doublResult = Math.PI;
                result.setText((String.valueOf(doublResult)));
                double currentVal = Math.PI;
                currentNumber.append(currentVal);
                return;
            }

            if(currentNumber.toString().equals("-π") && operateValuw == null){
                currentNumber.delete(0,currentNumber.length());
                doublResult = -Math.PI;
                result.setText((String.valueOf(doublResult)));
                double currentVal = -Math.PI;
                currentNumber.append(currentVal);
                return;
            }
            if(currentNumber.toString().endsWith("π")&& operateValuw == null){
                currentNumber.deleteCharAt( currentNumber.length() - 1 );
                ac.setText(R.string.AC);
                double currentVal = Double.parseDouble(currentNumber.toString());
                currentVal *= Math.PI;
                result.setText((String.valueOf(currentVal)));
                currentNumber.setLength(0);
                currentNumber.append(currentVal);
                return;
            }
            if(currentNumber.toString().equals("π")){
                currentNumber.delete(0,currentNumber.length());
                currentNumber.append(Double.parseDouble(String.valueOf(Math.PI)));
                return;
            }
            if(currentNumber.toString().equals("-π")){
                currentNumber.delete(0,currentNumber.length());
                currentNumber.append(Double.parseDouble(String.valueOf(-Math.PI)));
                return;
            }
            if(currentNumber.toString().endsWith("π")){
                currentNumber.delete(currentNumber.length() - 1, currentNumber.length());
                double currentVal = Double.parseDouble(currentNumber.toString());
                currentVal *= Math.PI;
                currentNumber.setLength(0);
                currentNumber.append(currentVal);
            }

            if(currentNumber.toString().equals(".") || currentNumber.toString().equals("%")){
                resultonError();
                return;
            }
            if(currentNumber.toString().equals("-%") && operateValuw == null) {
                resultonError();
                return;
            }
            if(currentNumber.toString().endsWith("%") && operateValuw == null){
                currentNumber.deleteCharAt( currentNumber.length() - 1 );
                doublResult = Double.parseDouble(currentNumber.toString())/100;
                result.setText((String.valueOf(doublResult)));
                ac.setText(R.string.AC);
                return;
            }

            if(previousNumbers.toString().isEmpty()) return;
            if(isButtonclicked && previousNumbers.toString().isEmpty() ){
                return;
            }
            if(previousNumbers.toString().charAt(previousNumbers.length()-1) == '%'){
                previousNumbers.deleteCharAt(previousNumbers.length()-1);
                firstNumer = Double.parseDouble(String.valueOf(previousNumbers));
                firstNumer = firstNumer/100;

            }else{
                firstNumer = Double.parseDouble(String.valueOf(previousNumbers));
            }

            if(currentNumber.toString().charAt(currentNumber.length()-1) == '%'){
                currentNumber.deleteCharAt(currentNumber.length()-1);
                secondNumber = Double.parseDouble(String.valueOf(currentNumber));
                secondNumber = secondNumber/100;
            }else{
                secondNumber = Double.parseDouble(currentNumber.toString());
            }
            result2();
            ac.setText(R.string.AC);
        });


        //-------------------------------------------USUŃ ----------------------------------------//


        ac.setOnClickListener(v -> {
            isEqualsclicked = false;
            getHorizont();
            currentNumber.delete(0, currentNumber.length());
            result.setText("0");
            operate.setText(null);
            operator =null;
            operateValuw = null;
            previousnum.setText("");
            doublResult = 0;
            ac.setText(R.string.AC);
        });


        //-----------------------------------------ZMIENNE----------------------------------------//


        zero.setOnClickListener(liczby);
        dot.setOnClickListener(liczby);
        one.setOnClickListener(liczby);
        two.setOnClickListener(liczby);
        three.setOnClickListener(liczby);
        four.setOnClickListener(liczby);
        five.setOnClickListener(liczby);
        six.setOnClickListener(liczby);
        seven.setOnClickListener(liczby);
        eight.setOnClickListener(liczby);
        nine.setOnClickListener(liczby);
        buttonDivide.setOnClickListener(operacje);
        buttonAdd.setOnClickListener(operacje);
        buttonSubstract.setOnClickListener(operacje);
        buttonMultiply.setOnClickListener(operacje);
        buttonprocent.setOnClickListener(liczby);


        if(orientation == 2){
            Button c1 = findViewById(R.id.c1);
            c1.setOnClickListener(liczby);
            Button c2 = findViewById(R.id.c2);
            c2.setOnClickListener(liczby);
            Button pi = findViewById(R.id.pi);
            pi.setOnClickListener(liczby);
            Button xy = findViewById(R.id.xy);
            xy.setOnClickListener(operacje);

            Button x2 = findViewById(R.id.x2);
            Button x3 = findViewById(R.id.x3);
            Button ex = findViewById(R.id.ex);
            Button o10x = findViewById(R.id.o10x);
            Button divide1 = findViewById(R.id.divide1);

            View.OnClickListener pow = v -> {
                Button operations = findViewById(v.getId());
                String operation = (String) operations.getText();
                if(currentNumber.toString().isEmpty()) return;
                double result2 = 0.0;
                double input = Double.parseDouble(currentNumber.toString());

                switch (operation) {
                    case "x²":
                        result2 = Math.pow(input, 2);
                        break;
                    case "x³":
                        result2 = Math.pow(input, 3);
                        break;
                    case "eˣ":
                        result2 = Math.pow(Math.E, input);
                        break;
                    case "10ˣ":
                        result2 = Math.pow(10, input);
                        break;
                    case "¹/ₓ":
                        result2 = 1/input;
                        break;
                }


                currentNumber.setLength(0);
                currentNumber.append(result2);
                if (Double.isInfinite(result2)) {
                    result.setText(R.string.value);
                    currentNumber.delete(0, currentNumber.length());
                    return;
                }
                result.setText(String.valueOf(result2));
            };

            x2.setOnClickListener(pow);
            x3.setOnClickListener(pow);
            ex.setOnClickListener(pow);
            o10x.setOnClickListener(pow);
            divide1.setOnClickListener(pow);
        }


        //--------------------------------------USUN_JEDEN----------------------------------------//


        result.setOnClickListener(v -> {
            if(currentNumber.toString().equals("")){
                result.setText("0");
                ac.setText(R.string.AC);
                return;
            }
            if(isEqualsclicked) return;
            if (currentNumber.length() == 0) {
                currentNumber.append("");
            } else {
                currentNumber.delete(currentNumber.length() - 1, currentNumber.length());
            }
            result.setText(currentNumber.toString());
        });


        //----------------------------------------HISTORIA----------------------------------------//


        historyOption.setOnClickListener(v -> {
            Intent historyView = new Intent(MainActivity.this , HistoryActivity.class);
            historyView.putExtra("key", history.toString());
            startActivity(historyView);
        });
    }


        //-------------------------------------METODA_WYNIK---------------------------------------//


     void result2(){
         double tolerancja = 1e-10;
         getHorizont();
         if(Math.abs(secondNumber) < tolerancja && String.valueOf(operator.getText()).equals("÷")){
             resultonError();
             return;
         }
        switch (operateValuw){
            case "+":
                doublResult = firstNumer+secondNumber;
                break;
            case "-":
                doublResult = firstNumer-secondNumber;
                break;
            case "×":
                doublResult = firstNumer*secondNumber;
                break;
            case "÷":
                doublResult = firstNumer/secondNumber;
                break;
            case "xʸ":
                doublResult = Math.pow(firstNumer, secondNumber);
                break;
        }
            history.append(previousNumbers.toString() + " " + operateValuw + " " + currentNumber.toString() + "\n" + doublResult +"\n\n" );
            previousNumbers.delete(0, previousNumbers.length());
            result.setText(String.valueOf(doublResult));
            currentNumber.delete(0,currentNumber.length());
            previousnum.setText("");
            operate.setText(null);
            currentNumber.append(doublResult);
    }


     //-------------------------------------------METODY------------------------------------------//


    void getHorizont(){
        currentScrollX = horizont1.getScrollX();
        scrollToX = result.getRight();
        horizont1.smoothScrollTo(currentScrollX + scrollToX, 0);

        currentScrollX = horizont2.getScrollX();
        scrollToX = result.getRight();
        horizont2.smoothScrollTo(currentScrollX + scrollToX, 0);
    }


    void resultonError(){
        result.setText("błąd");
        operate.setText("");
        previousnum.setText("");
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("currentnumber" , currentNumber.toString());
        if (previousNumbers == null) {
            bundle.putString("previousNumber", "");
        } else {
            bundle.putString("previousNumber", previousNumbers.toString());
        }
        bundle.putString("resultText", String.valueOf(doublResult));
        if (operateValuw == null) {
            bundle.putString("operate", "");
        } else {
            bundle.putString("operate", operateValuw);
        }
        bundle.putString("history" , history.toString());
    }
}