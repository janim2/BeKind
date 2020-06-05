package com.example.bekind;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

public class DonateService extends AppCompatActivity {
    MaterialSpinner serviceSpinner;
    Button submitdonationButton;
    EditText othereditText;
    ProgressBar loading;
    String orgID, userID, serviceID, serviceSelected = "";


    Accessories donateserviceAccessor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_service);
        getSupportActionBar().setTitle("Donate | Service");
        othereditText = (EditText) findViewById(R.id.specifyService);
        loading = (ProgressBar) findViewById(R.id.loading);

        donateserviceAccessor = new Accessories(DonateService.this);

        serviceSpinner = (MaterialSpinner) findViewById(R.id.serviceSpinner);
        serviceSpinner.setItems("Teaching", "Cleaning", "Counselling", "Doctor", "Nurse", "Caterer","Other");

        serviceSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
//                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                if(item.equals("Other")){
                    othereditText.setVisibility(View.VISIBLE);
                    serviceSelected = othereditText.getText().toString().trim();
                }else{
                    serviceSelected = item+"";
                }
            }
        });

        submitdonationButton = (Button) findViewById(R.id.submitdonationButton);

        submitdonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orgID = donateserviceAccessor.getString("selectedOrgID");
                userID = donateserviceAccessor.getString("theUserID");

                Random nuber = new Random();
                int serviceIDint = 45 + nuber.nextInt(2776666);
                serviceID = "BeKindSe" + serviceIDint+"";

                new UploadService("https://iamjesse75.000webhostapp.com/BeKind/UploadService.php",orgID,userID,serviceID,serviceSelected).execute();
            }
        });
    }

    class UploadService extends AsyncTask<Void, Void, String> {

        String url_location, orgID,user_id, serviceID ,service;

        public UploadService(String url_location, String orgID,String user_id, String serviceID,
                              String service) {
            this.url_location = url_location;
            this.user_id = user_id;
            this.orgID = orgID;
            this.serviceID = serviceID;
            this.service = service;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

            try {
                URL url = new URL(url_location);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&" +
                        URLEncoder.encode("orgId", "UTF-8") + "=" + URLEncoder.encode(orgID, "UTF-8") + "&" +
                        URLEncoder.encode("serviceId", "UTF-8") + "=" + URLEncoder.encode(serviceID, "UTF-8") + "&" +
                        URLEncoder.encode("service", "UTF-8") + "=" + URLEncoder.encode(service, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String fetch;
                while ((fetch = bufferedReader.readLine()) != null) {
                    stringBuffer.append(fetch);
                }
                String string = stringBuffer.toString();
                inputStream.close();
                return string;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return "please check internet connection";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.setVisibility(View.GONE);
            if(s.equals("Offer has been received")){
                loading.setVisibility(View.GONE);
                Toast.makeText(DonateService.this,s,Toast.LENGTH_LONG).show();
            }
            Toast.makeText(DonateService.this,s,Toast.LENGTH_LONG).show();
        }
    }
}
