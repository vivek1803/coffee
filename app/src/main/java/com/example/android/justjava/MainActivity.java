/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;

import java.text.NumberFormat;

import static com.example.android.justjava.R.string.total;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        if (quantity == 0) {
            Toast.makeText(this, "Dont you want a coffee?", Toast.LENGTH_SHORT).show();
        } else {
            String whippedCream, chocolate;

            // Get name from edit text field
            EditText name = (EditText) findViewById(R.id.name);
            String Name = name.getText().toString().trim();
            if (Name.isEmpty() || Name.length() == 0 || Name.equals("")) {
                Toast.makeText(this, "Name is mandatory", Toast.LENGTH_SHORT).show();
                return;
            }

            //Test Commit

            //Get the coffee details from the radio button
            RadioGroup radioGrp = (RadioGroup) findViewById(R.id.radiogrp);
            int btnId = radioGrp.getCheckedRadioButtonId();
            Log.v("Radio Button:", "Value " + btnId);
            if (btnId <= 0) {
                Toast.makeText(this, "Select your coffee", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton radioBtn = (RadioButton) findViewById(btnId);
            String coffee = radioBtn.getText().toString();

            //Get the details from the check box
            boolean hasWhippedCream = getCheck(R.id.whipped_cream);
            if (hasWhippedCream) {
                whippedCream = "Yes";
            } else {
                whippedCream = "No";
            }
            boolean hasChocolate = getCheck(R.id.chocolate);

            if (hasChocolate) {
                chocolate = "Yes";
            } else {
                chocolate = "No";
            }
            String price = calculatePrice(hasWhippedCream, hasChocolate);

            // Create the order summary
            String priceMessage = createOrderSummary(Name, coffee, whippedCream, chocolate, price);

            // Send an email to Vivek
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto: v.vivek18@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for " + Name);
            intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
            startActivity(Intent.createChooser(intent, "Send Email"));
            finish();
        }
    }

    private boolean getCheck(int resource) {
        CheckBox checkBox = (CheckBox) findViewById(resource);
        if (checkBox.isChecked()) {
            return true;
        } else {
            return false;
        }

    }

    private String calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;
        if (hasWhippedCream) {
            price += 1;
        }
        if (hasChocolate) {
            price += 2;
        }
        return NumberFormat.getCurrencyInstance().format(quantity * price);
    }

    private String createOrderSummary(String Name, String coffee, String whippedCream, String chocolate, String total) {
        String priceMessage = getString(R.string.name) + ": " + Name;
        priceMessage += "\n" + getString(R.string.coffee) + ": " + coffee;
        priceMessage += "\n" + getString(R.string.add) + " " + getString(R.string.whipped_cream) + "? " + whippedCream;
        priceMessage += "\n" + getString(R.string.add) + " " + getString(R.string.chocolate) + "? " + chocolate;
        priceMessage += "\n" + getString(R.string.quantity) + ": " + quantity;
        priceMessage += "\n" + getString(R.string.total) + ": " + total;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    private void init() {

    }

    public void increment(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        if (quantity < 100) {
            quantity++;
        } else {
            Toast.makeText(this, "Quantity cannot exceed 100", Toast.LENGTH_SHORT).show();
        }
        quantityTextView.setText("" + quantity);
    }

    public void decrement(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        if (quantity > 1) {
            quantity--;
        } else {
            Toast.makeText(this, "Quantity cannot be less than 1", Toast.LENGTH_SHORT).show();
        }
        quantityTextView.setText("" + quantity);
    }
}