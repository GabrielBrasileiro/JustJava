package next.android.gabriel.com.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.support.v7.app.ActionBarActivity;
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
public class MainActivity extends ActionBarActivity {

    private int quantity=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
            EditText nameField = (EditText) findViewById(R.id.name_field);
            String name = nameField.getText().toString();

            CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
            boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

            CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
            boolean hasChocolate = ChocolateCheckBox.isChecked();

            int price = calculatePrice(hasWhippedCream, hasChocolate);
            String priceMessage = creatOrderSummary(name, price, hasWhippedCream, hasChocolate);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java pedido para " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private void calculatePrice(int quantity) {
        int price = quantity * 5;
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int baseprice = 5;

        if(addWhippedCream) {
            baseprice = baseprice + 1;
        }

        if(addChocolate){
            baseprice = baseprice + 2;
        }

        int price = quantity * baseprice;
        return price;
    }



    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }

    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(this, "Você não pode pedir mais de 100 cafés", Toast.LENGTH_SHORT).show();
            return;
        }

        displayQuantity(++quantity);
    }

    public void decrement(View view) {
        if(quantity == 1) {
            Toast.makeText(this, "Você não pode pedir menos que um café", Toast.LENGTH_SHORT).show();
            return;
        }
            quantity = quantity - 1;
            displayQuantity(quantity);
    }

    private String creatOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        return "Nome: " + name +
                "\nAdicionou Chantily ? " + addWhippedCream +
                "\nAdicionou Chocolate ? " + addChocolate +
                "\nQuantidade: " + quantity +
                "\nPreço: " + price + "R$" +
                "\nObrigado!";

    }

}