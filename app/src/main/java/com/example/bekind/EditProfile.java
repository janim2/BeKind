package com.example.bekind;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    EditText firstname, lastname, phone, email, address;
    String userIDstring, profileImage,firstnameString, lastnameString, phoneString ,emailString, addressString;
    Accessories editProfileaccessor;
    String fromfirstname, fromlastname, fromphone, fromemail, fromaddress;
    CardView done;
    CircleImageView profileImageCircle;
    ProgressBar loading;
    ImageLoader editprofileloader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        editProfileaccessor = new Accessories(EditProfile.this);
        getSupportActionBar().setTitle("Edit Profile");

        firstname = (EditText)findViewById(R.id.firstname);
        lastname = (EditText)findViewById(R.id.lastname);
        phone = (EditText)findViewById(R.id.phonenumber);
        email = (EditText)findViewById(R.id.email);
        address = (EditText)findViewById(R.id.address);
        done = (CardView) findViewById(R.id.doneedit);
        profileImageCircle = (CircleImageView) findViewById(R.id.profilepic);
        loading = (ProgressBar) findViewById(R.id.loading);

        userIDstring = editProfileaccessor.getString("theUserID");
        profileImage = editProfileaccessor.getString("theprofileImage");
        firstnameString = editProfileaccessor.getString("Firstname");
        lastnameString = editProfileaccessor.getString("Lastname");
        phoneString = editProfileaccessor.getString("phone");
        emailString = editProfileaccessor.getString("Email");
        addressString = editProfileaccessor.getString("Home_Address");


        if(editProfileaccessor.getString("theprofileImage").equals("nothing")){
            profileImageCircle.setImageDrawable(getResources().getDrawable(R.drawable.profile));
        }else{
            //prep work before image is loaded is to load it into the cache
            DisplayImageOptions theImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).
                    cacheOnDisk(true).build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).
                    defaultDisplayImageOptions(theImageOptions).build();
            ImageLoader.getInstance().init(config);
//
            String profileimagelink = "http://iamjesse75.000webhostapp.com/BeKind/pictures/profilepics/" + editProfileaccessor.getString("theprofileImage");
            editprofileloader.displayImage(profileimagelink,profileImageCircle);
        }

        firstname.setText(firstnameString);
        lastname.setText(lastnameString);
        phone.setText(phoneString);
        email.setText(emailString);
        address.setText(addressString);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromfirstname = firstname.getText().toString().trim();
                fromlastname = lastname.getText().toString().trim();
                fromphone = phone.getText().toString().trim();
                fromemail = email.getText().toString().trim();
                fromaddress = address.getText().toString().trim();
                new UpdateProfile("https://iamjesse75.000webhostapp.com/BeKind/UpdateInfo.php",userIDstring,getBitmap(profileImageCircle),fromfirstname,fromlastname,fromphone,fromemail,fromaddress).execute();
            }
        });


        profileImageCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editProfileaccessor.checkPermission("android.permission.READ_EXTERNAL_STORAGE",
                        "External Storage", 20))
                    editProfileaccessor.galleryAction(2000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri uri = data.getData();
            profileImageCircle.setImageURI(uri);
        }
    }

    public Bitmap getBitmap(CircleImageView theimageview){
        try{
            Bitmap encodingImage = Bitmap.createBitmap(theimageview.getDrawable().getIntrinsicWidth(),theimageview.getDrawable().getIntrinsicHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(encodingImage);
            theimageview.getDrawable().setBounds(0,0,canvas.getWidth(), canvas.getHeight());
            theimageview.getDrawable().draw(canvas);
            return encodingImage;
        }

        catch (OutOfMemoryError e){
            return null;
        }
    }

    class UpdateProfile extends AsyncTask<Void, Void, String> {

        String url_location, user_id, first_name, last_name, phone_number, email,home_address;
        Bitmap theprofileImage;

        public UpdateProfile(String url_location, String user_id,Bitmap theprofileImage, String first_name, String last_name,
                             String phone_number, String email, String home_address) {
            this.url_location = url_location;
            this.user_id = user_id;
            this.theprofileImage = theprofileImage;
            this.first_name = first_name;
            this.last_name = last_name;
            this.phone_number = phone_number;
            this.email = email;
            this.home_address = home_address;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

            String encodedImage = "";
            if(theprofileImage != null){
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                theprofileImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            }

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
                        URLEncoder.encode("profileImage", "UTF-8") + "=" + URLEncoder.encode(encodedImage, "UTF-8") + "&" +
                        URLEncoder.encode("firstname", "UTF-8") + "=" + URLEncoder.encode(first_name, "UTF-8") + "&" +
                        URLEncoder.encode("lastname", "UTF-8") + "=" + URLEncoder.encode(last_name, "UTF-8") + "&" +
                        URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone_number, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("homeAddress", "UTF-8") + "=" + URLEncoder.encode(home_address, "UTF-8");

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
            if(s.equals("Update Complete")){
                loading.setVisibility(View.GONE);
                editProfileaccessor.put("theprofileImage",user_id+".jpg");
                editProfileaccessor.put("Firstname",first_name);
                editProfileaccessor.put("Lastname",last_name);
                editProfileaccessor.put("phone",phone_number);
                editProfileaccessor.put("Email",email);
                editProfileaccessor.put("Home_Address",home_address);
                Toast.makeText(EditProfile.this,s,Toast.LENGTH_LONG).show();
            }
            Toast.makeText(EditProfile.this,s,Toast.LENGTH_LONG).show();
        }

    }
}
