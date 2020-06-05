package com.example.bekind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bekind.Models.OrgModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class OrgDetailsActivity extends AppCompatActivity {

    ImageView orgImage;
    TextView orgname, mainmessage,orgPhone, orgEmail, location;
    Accessories orgDetailAccessor;
    ImageLoader detailsLoader = ImageLoader.getInstance();
    Button donateButton;

    String orgIdString,orgImageString, orgnameString, aboutString, orgPhoneString, orgEmailString, locationString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_details);

        orgDetailAccessor = new Accessories(OrgDetailsActivity.this);
        orgImage = (ImageView) findViewById(R.id.orgImage);
        orgname = (TextView) findViewById(R.id.thename);
        mainmessage = (TextView) findViewById(R.id.mainmessage);
        orgPhone = (TextView) findViewById(R.id.phonenumber);
        orgEmail = (TextView) findViewById(R.id.email);
        location = (TextView) findViewById(R.id.location);
        donateButton = (Button) findViewById(R.id.donate);

        orgIdString = getIntent().getStringExtra("orgID");
        orgImageString = getIntent().getStringExtra("OrgImage");
        orgnameString = getIntent().getStringExtra("OrgName");
        aboutString = getIntent().getStringExtra("OrgInfo");
        orgPhoneString = getIntent().getStringExtra("OrgPhone");
        orgEmailString = getIntent().getStringExtra("OrgEmail");
        locationString = getIntent().getStringExtra("OrgAddress");

        DisplayImageOptions theImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).
                cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(OrgDetailsActivity.this.getApplicationContext()).
                defaultDisplayImageOptions(theImageOptions).build();
        ImageLoader.getInstance().init(config);

        String orgImageUrl = "https://myanim.000webhostapp.com/BeKind/pictures/OrgImages/" + orgImageString;
        detailsLoader.displayImage(orgImageUrl, orgImage);

        orgname.setText(orgnameString);
        mainmessage.setText(aboutString);
        orgPhone.setText(orgPhoneString);
        orgEmail.setText(orgEmailString);
        location.setText(locationString);

        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donateIntent = new Intent(OrgDetailsActivity.this,Donate.class);
                orgDetailAccessor.put("selectedOrgID",orgIdString);
                orgDetailAccessor.put("selectedOrgname",orgnameString);
                startActivity(donateIntent);
            }
        });
    }
}
