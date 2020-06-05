package com.example.bekind;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class DonateMaterial extends AppCompatActivity {
    ImageView uploaddonationImage;
    EditText description, prize;
    Button submitDonationButton;
    ProgressBar loading;
    String userid,orgID,materialID, materialDescription, materialprize;
    Accessories donationMaterialAccessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_material);
        getSupportActionBar().setTitle("Donate | Material");
        donationMaterialAccessor = new Accessories(DonateMaterial.this);
        uploaddonationImage = (ImageView) findViewById(R.id.item_image);
        description = (EditText) findViewById(R.id.item_description);
        prize = (EditText) findViewById(R.id.prize);
        submitDonationButton = (Button) findViewById(R.id.submitdonationButton);
        loading = (ProgressBar) findViewById(R.id.loading);

        submitDonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random nuber = new Random();
                int materialIDint = 45 + nuber.nextInt(2776666);
                materialID = "BeKindMa" + materialIDint+"";

                materialDescription = description.getText().toString().trim();
                materialprize = prize.getText().toString().trim();
                userid = donationMaterialAccessor.getString("theUserID");
                orgID = donationMaterialAccessor.getString("selectedOrgID");


                if(!materialDescription.equals("")){
                    if(!materialprize.equals("")){
                        new UploadMaterial("https://myanim.000webhostapp.com/BeKind/UploadMaterial.php",orgID,userid,getBitmap(uploaddonationImage),materialID,materialDescription,materialprize).execute();
                    }else{
                        prize.setError("Required");
                    }
                }else{
                    description.setError("Required");
                }
            }
        });

        uploaddonationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(donationMaterialAccessor.checkPermission("android.permission.READ_EXTERNAL_STORAGE",
                        "External Storage", 20))
                    donationMaterialAccessor.galleryAction(2000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri uri = data.getData();
            uploaddonationImage.setImageURI(uri);
        }
    }

    public Bitmap getBitmap(ImageView theimageview){
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

    class UploadMaterial extends AsyncTask<Void, Void, String> {

        String url_location, orgID,user_id, material_id ,material_description, material_prize;
        Bitmap materialImage;

        public UploadMaterial(String url_location, String orgID,String user_id,Bitmap materialImage, String materialID, String material_description,
                              String material_prize) {
            this.url_location = url_location;

            this.user_id = user_id;
            this.orgID = orgID;
            this.materialImage = materialImage;
            this.material_id = materialID;
            this.material_description = material_description;
            this.material_prize = material_prize;
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
            if(materialImage != null){
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                materialImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
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
                        URLEncoder.encode("materialID", "UTF-8") + "=" + URLEncoder.encode(material_id, "UTF-8") + "&" +
                        URLEncoder.encode("organizationID", "UTF-8") + "=" + URLEncoder.encode(orgID, "UTF-8") + "&" +
                        URLEncoder.encode("materialImage", "UTF-8") + "=" + URLEncoder.encode(encodedImage, "UTF-8") + "&" +
                        URLEncoder.encode("material_description", "UTF-8") + "=" + URLEncoder.encode(material_description, "UTF-8") + "&" +
                        URLEncoder.encode("material_prize_worth", "UTF-8") + "=" + URLEncoder.encode(material_prize, "UTF-8");

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
            if(s.equals("Thanks. A child would be glad")){
                loading.setVisibility(View.GONE);
                Toast.makeText(DonateMaterial.this,s,Toast.LENGTH_LONG).show();
            }
            Toast.makeText(DonateMaterial.this,s,Toast.LENGTH_LONG).show();
        }
    }
}
