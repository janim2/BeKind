package com.example.bekind;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Login extends AppCompatActivity {

    EditText email, password;
    String semail,spassword;
    Accessories loginAccessor;
    ProgressBar loading;
    TextView welcomeMessage,donthaveacount;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        loginAccessor =  new Accessories(Login.this);
        loading = (ProgressBar) findViewById(R.id.loading);
        welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        donthaveacount = (TextView) findViewById(R.id.dontHaveAccount);
        loginButton = (Button) findViewById(R.id.cirLoginButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semail = email.getText().toString().trim();
                spassword = password.getText().toString().trim();
                int editnumbers[] = {1,5};
                EditText alledits[] = {email,password};
                String allstrings[] = {semail,spassword};

                Boolean isval = loginAccessor.validateFields(editnumbers,alledits,allstrings);

                if(isval){
                    new LoggingIn("http://myanim.000webhostapp.com/BeKind/login.php",
                            semail,spassword).execute();
                }
            }
        });

        donthaveacount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });

    }

    class LoggingIn extends AsyncTask<Void, Void, String> {

        String url_location, email, password;

        public LoggingIn(String url_location, String email, String password) {
            this.url_location = url_location;
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(VISIBLE);
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

                String data =
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
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
            loading.setVisibility(GONE);
            if (s.equals("login Successful")){
                loginAccessor.put("login_checker",true);
                finish();
                startActivity(new Intent(Login.this,MainActivity.class));
                new Get_User_Information("https://myanim.000webhostapp.com/BeKind/FetchUserInfo.php",semail,spassword).execute();

            }else{
            welcomeMessage.setTextColor(getResources().getColor(R.color.red));
            welcomeMessage.setText(s);
            welcomeMessage.setVisibility(VISIBLE);
            }
        }
    }

//    method to get user information
    public class Get_User_Information extends AsyncTask<Void, Void, String> {
        String urlLocation;
        String email,password;

        public Get_User_Information(String urlLocation, String email, String password) {
//            this.task = task;
            this.urlLocation = urlLocation;
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            alertDialog = new ProgressDialog(Login.this);
//            alertDialog.setMessage(task);
//            alertDialog.setCancelable(false);
//            alertDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

            try {
                URL url = new URL(urlLocation);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String fetch = "";
                while ((fetch = bufferedReader.readLine()) != null) {
                    stringBuffer.append(fetch);
                }
                String string = stringBuffer.toString();
                inputStream.close();
                return string;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Please Check Your Internet Connection";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (!s.equals("Unable to Find User") || !s.equals("Please Check Your Internet Connection")) {
//                String userdetails[] = s.split("-");
                String userID = "",profileImage = "",firstname = "", lastname = "",
                        phone = "",gender = "",email = "",homeaddress = "",password = "";


                // getting the individual values based json in php using the keys there
                try {
                    JSONObject userobject = new JSONObject(s);
                    userID = userobject.getString("UserID");
                    profileImage = userobject.getString("ProfileImage");
                    firstname = userobject.getString("Firstname");
                    lastname = userobject.getString("Lastname");
                    phone = userobject.getString("phone");
                    gender = userobject.getString("Gender");
                    email = userobject.getString("Email");
                    homeaddress = userobject.getString("Home_Address");
                    password = userobject.getString("Password");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //storing the info in the shared preferences class to future usage
                loginAccessor.put("theUserID",userID);
                loginAccessor.put("theprofileImage",profileImage);
                loginAccessor.put("Firstname",firstname);
                loginAccessor.put("Lastname",lastname);
                loginAccessor.put("phone",phone);
                loginAccessor.put("Gender",gender);
                loginAccessor.put("Email",email);
                loginAccessor.put("Home_Address",homeaddress);
                loginAccessor.put("Password",password);
//
//                Toast.makeText(Login.this,userID,Toast.LENGTH_LONG).show();
//                Toast.makeText(Login.this,profileImage,Toast.LENGTH_LONG).show();
//                Toast.makeText(Login.this,firstname,Toast.LENGTH_LONG).show();
//                Toast.makeText(Login.this,lastname,Toast.LENGTH_LONG).show();
//                Toast.makeText(Login.this,phone,Toast.LENGTH_LONG).show();
//                Toast.makeText(Login.this,email,Toast.LENGTH_LONG).show();
//                Toast.makeText(Login.this,homeaddress,Toast.LENGTH_LONG).show();
//                Toast.makeText(Login.this,password,Toast.LENGTH_LONG).show();

            } else {
                android.app.AlertDialog.Builder getS = new android.app.AlertDialog.Builder(Login.this);
                getS.setMessage(s);
                getS.show();

            }
//            alertDialog.dismiss();
        }

    }
}
