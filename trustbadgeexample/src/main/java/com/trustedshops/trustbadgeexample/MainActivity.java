package com.trustedshops.trustbadgeexample;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.trustedshops.androidsdk.trustbadge.Product;
import com.trustedshops.androidsdk.trustbadge.Trustbadge;
import com.trustedshops.androidsdk.trustbadge.TrustbadgeException;
import com.trustedshops.androidsdk.trustbadge.TrustbadgeOrder;
import com.trustedshops.androidsdk.trustbadge.TrustedShopsCheckout;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    protected Context mContext;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageView testImageView = (ImageView) findViewById(R.id.trustbadgeTestImageView);
        /* Please insert your Trusted Shops ID */
        Trustbadge trustbadge = new Trustbadge("X330A2E7D449E31E467D2F53A55DDD070");

        try {
            //trustbadge.setIconColor("#F98222");
            //trustbadge.setLoggingActive(true);
            trustbadge.getTrustbadge(testImageView, this);

        } catch (IllegalArgumentException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        } catch (TrustbadgeException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        }


        Button checkoutButton = (Button) findViewById(R.id.checkout_button);
        if (checkoutButton != null) {
            checkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TrustbadgeOrder tsCheckoutTrustbadgeOrder = new TrustbadgeOrder();


                    //@TODO - remove random order id generation after testing
                    int orderNumber = new Random().nextInt((3000 - 1) + 1) + 1;
                    String checkoutOrderNumber = "SDKTEST1000" + orderNumber;

                    tsCheckoutTrustbadgeOrder.setTsCheckoutOrderNr(checkoutOrderNumber);
                    tsCheckoutTrustbadgeOrder.setTsCheckoutBuyerEmail("tester@example.com");
                    tsCheckoutTrustbadgeOrder.setTsCheckoutOrderAmount("150");
                    tsCheckoutTrustbadgeOrder.setTsCheckoutOrderCurrency("EUR");
                    tsCheckoutTrustbadgeOrder.setTsCheckoutOrderPaymentType("PAYPAL");

                    Product checkoutProduct1 = new Product();
                    checkoutProduct1.setTsCheckoutProductName("Brother TN-241C");
                    checkoutProduct1.setTsCheckoutProductSKU("4123123");
                    checkoutProduct1.setTsCheckoutProductUrl("http://www.brother.de/verbrauchsmaterial/laser/toner/tn/tn241c");
                    checkoutProduct1.setTsCheckoutProductBrand("Brother");
                    checkoutProduct1.setTsCheckoutProductGTIN("4977766718400");
                    checkoutProduct1.setTsCheckoutProductImageUrl("http://www.brother.de/~/media/Product%20Images/Supplies/Laser/Toner/TN/TN241C/TN241C_main.png");
                    checkoutProduct1.setTsCheckoutProductMPN("TN241C");

                    tsCheckoutTrustbadgeOrder.addCheckoutProductItem(checkoutProduct1);


                    Product checkoutProduct2 = new Product();
                    checkoutProduct2.setTsCheckoutProductName("Brother TN-261C");
                    checkoutProduct2.setTsCheckoutProductSKU("41231661");
                    checkoutProduct2.setTsCheckoutProductUrl("http://www.brother.de/verbrauchsmaterial/laser/toner/tn/tn241c");
                    tsCheckoutTrustbadgeOrder.addCheckoutProductItem(checkoutProduct2);

                    tsCheckoutTrustbadgeOrder.setTsId("X330A2E7D449E31E467D2F53A55DDD070");

                    TrustedShopsCheckout tsCheckout = new TrustedShopsCheckout(tsCheckoutTrustbadgeOrder);


                    /* Add callback for dialog dismiss */
                    Handler.Callback test = new Handler.Callback() {
                        @Override
                        public boolean handleMessage(Message test) {

                            switch (test.what) {
                                case TrustedShopsCheckout._dismissCallNumber:
                                    //Card closed
                                    Log.d("TSDEBUG","Case 1 called");

                                    break;
                                case TrustedShopsCheckout._errorCallNumber:
                                    //Failure
                                    Log.d("TSDEBUG","Case 2 called");
                                    break;
                            }

                            return true;
                        }
                    };

                    try {
                        tsCheckout.init(MainActivity.this, null);
                    } catch (TrustbadgeException exception) {
                        Log.d("TSDEBUG", "Checkout Exception: " + exception.getMessage());
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
