package com.example.bekind;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

import static android.widget.Toast.LENGTH_LONG;

public class Register extends AppCompatActivity {

    TextView alreadyHaveAccount,registrationcomplete;
    EditText firstname,lastname,phone_number,email, homeAddress, password,confirmPassword;
    String userID,sfirstname, slastname, sphonenumber, semail, shomeAddress, spassword, sconfirmpassword;
    Button register;
    Accessories registerAccessories;
    ProgressBar loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        registerAccessories = new Accessories(Register.this);
        registrationcomplete = (TextView) findViewById(R.id.completeRegistration);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        phone_number = (EditText) findViewById(R.id.mobilenumber);
        email = (EditText) findViewById(R.id.email);
        homeAddress = (EditText) findViewById(R.id.homeAddress);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        alreadyHaveAccount = (TextView) findViewById(R.id.alreadyHaveAccount);
        loading = (ProgressBar) findViewById(R.id.loading);

        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sfirstname = firstname.getText().toString().trim();
                slastname = lastname.getText().toString().trim();
                sphonenumber = phone_number.getText().toString().trim();
                semail = email.getText().toString().trim();
                shomeAddress = homeAddress.getText().toString().trim();
                spassword = password.getText().toString().trim();
                sconfirmpassword = confirmPassword.getText().toString().trim();

                //validating fields
                int inputArray[] = {3,3,2,1,5,5};
                EditText allEditexts[] = {firstname,lastname,phone_number,email,password,confirmPassword};
                String allStrings[] = {sfirstname,slastname,sphonenumber,semail,spassword,sconfirmpassword};

                Boolean validate = registerAccessories.validateFields(inputArray,allEditexts,allStrings);

                if(validate){
                    if(spassword.equals(sconfirmpassword)){
                        Random theRemainder = new Random();
                        int theSegin = 100000 + theRemainder.nextInt(899999);

                        userID = "Bk" + theSegin+"" + "Sa";
                        new Registerusers("http://iamjesse75.000webhostapp.com/BeKind/signup.php",
                                userID,sfirstname,slastname,sphonenumber,semail,shomeAddress,spassword).execute();
//                        Toast.makeText(Register.this,"Registration Complete",Toast.LENGTH_LONG).show();
                    }else{
                        confirmPassword.setError("Password Mismatch");
                    }
                }
            }
        });

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
    }

    class Registerusers extends AsyncTask<Void, Void, String> {

        String url_location, user_id, first_name, last_name, phone_number, email,home_address, password;

        public Registerusers(String url_location, String user_id, String first_name, String last_name,
                         String phone_number, String email, String home_address, String password) {
            this.url_location = url_location;
            this.user_id = user_id;
            this.first_name = first_name;
            this.last_name = last_name;
            this.phone_number = phone_number;
            this.email = email;
            this.home_address = home_address;
            this.password = password;
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
                        URLEncoder.encode("firstname", "UTF-8") + "=" + URLEncoder.encode(first_name, "UTF-8") + "&" +
                        URLEncoder.encode("lastname", "UTF-8") + "=" + URLEncoder.encode(last_name, "UTF-8") + "&" +
                        URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone_number, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("homeAddress", "UTF-8") + "=" + URLEncoder.encode(home_address, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

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
            if(s.equals("Registration Complete")){
                registrationcomplete.setText(s);
                registrationcomplete.setVisibility(View.VISIBLE);
                Toast.makeText(Register.this,s,Toast.LENGTH_LONG).show();
            }
            registrationcomplete.setTextColor(getResources().getColor(R.color.red));
            registrationcomplete.setText(s);
            registrationcomplete.setVisibility(View.VISIBLE);
            Toast.makeText(Register.this,s,Toast.LENGTH_LONG).show();
        }

    }

}
