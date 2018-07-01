package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */


    public void increment(View view){
        if(quantity == 100){
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
           return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view){
        if(quantity == 1){
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
        Log.v("MainActivity", "This is your error log");
    }

    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice();
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate);

        Toast.makeText(this, "Order placed", Toast.LENGTH_SHORT).show();
//        displayMessage(priceMessage);

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:47.6, -122.3"));
//        if(intent.resolveActivity(getPackageManager()) != null){
//            startActivity(intent);
//        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, "chiazokamecheta@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if(intent.resolveActivity(getPackageManager()) != null) {    //Checks if there's an app to run the intent
            startActivity(intent);
        }

    }

    /**
     * This method summarizes the order
     */

    private String createOrderSummary(int price, boolean checkboxState1, boolean checkboxState2){
        EditText editTextInput = (EditText) findViewById(R.id.name_input);
        Editable nameInput = editTextInput.getText();

        String priceMessage = "Name: " + nameInput;
        priceMessage = priceMessage + "\nAdd whipped cream?: " + checkboxState1;
        priceMessage = priceMessage + "\nAdd Chocolate?: " + checkboxState2;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: $" + price;
        priceMessage = priceMessage + "\nThank You!";

        return priceMessage;
    }

    /**
     * This method defines the calculatePrice method
     */

    private int calculatePrice(){
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = quantity * 5;

        if(hasWhippedCream == true){
            price = quantity * (5 + 1);
        }

        if(hasChocolate == true){
            price = quantity * (5 + 2);
        }

        return price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}
