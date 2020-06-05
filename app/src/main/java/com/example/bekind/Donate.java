package com.example.bekind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Donate extends AppCompatActivity {
    ExpandableListView donationsExpandible;
    ExpandablelistAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String,List<String>> listHash;
    int PAYPAL_REQUEST_CODE = 1;
    String selectedOrg;
    Accessories donateaccessories;


    //    configure paypal in sandbox for development;
    static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalconfig.PAYPAL_CLIENT_Id);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        getSupportActionBar().setTitle("Donate");
        donateaccessories = new Accessories(Donate.this);
        donationsExpandible = (ExpandableListView) findViewById(R.id.donationsExpandible);
        setItems();
        listAdapter = new ExpandablelistAdapter(Donate.this,listDataHeader,listHash);
        donationsExpandible.setAdapter(listAdapter);
        selectedOrg = donateaccessories.getString("selectedOrgID");

        donationsExpandible.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int cp = (int)listAdapter.getChildId(groupPosition,childPosition);

                if(groupPosition == 0 && childPosition == 0){
//                    materials here
//                    Toast.makeText(Donate.this,"Materials here",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Donate.this,DonateMaterial.class));
                }

                if(groupPosition == 1 && childPosition == 0){
//                    services here
//                    Toast.makeText(Donate.this,"Services",Toast.LENGTH_LONG).show();
                      startActivity(new Intent(Donate.this,DonateService.class));

                }

                if(groupPosition == 2 && childPosition == 0){
//                    Mobile money activity goes here
                    Toast.makeText(Donate.this,"Mobile Money",Toast.LENGTH_LONG).show();

                }

                if(groupPosition == 2 && childPosition == 1){
                    //visa and master card goes here
                    Toast.makeText(Donate.this,"Visa",Toast.LENGTH_LONG).show();
                }

                if(groupPosition == 2 && childPosition == 2){
                    //Paypal goes here
                    payPalPayment();
                }
                return false;
            }
        });
    }


    private void payPalPayment() {
//        1 here is the Prize to be donated
//        prize variable changed to 1 for testing purposes
        PayPalPayment payment = new PayPalPayment(new BigDecimal("1"), "USD", "Ride Prize",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }


    private void setItems() {

        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("MATERIALS");
        listDataHeader.add("SERVICES");
        listDataHeader.add("MONEY");

        List<String> materials = new ArrayList<>();
        materials.add("Donate item");

        List<String> services = new ArrayList<>();
        services.add("Volunteer");

        List<String> money = new ArrayList<>();
        money.add("MTN Mobile Money");
        money.add("Mastercard");
        money.add("PayPal");

        listHash.put(listDataHeader.get(0),materials);
        listHash.put(listDataHeader.get(1),services);
        listHash.put(listDataHeader.get(2),money);

    }
}
