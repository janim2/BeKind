package com.example.bekind;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bekind.Models.DonationsModel;
import com.example.bekind.Models.NotifyModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;
import java.util.List;

public class MyDonations extends AppCompatActivity {

    ProgressBar loading;
    TextView noNotify;
    ListView donationsListView;
    List<DonationsModel> DonationsList = new ArrayList<>();
    Displayer adapter;
    Accessories donationsAccesssor;
    ImageLoader mydonationsImageLoader = ImageLoader.getInstance();
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donations);
        getSupportActionBar().setTitle("Donations");

        donationsAccesssor = new Accessories(MyDonations.this);
        noNotify = (TextView) findViewById(R.id.noNotify);
        loading = (ProgressBar) findViewById(R.id.loading);
        donationsListView = (ListView) findViewById(R.id.donationslistView);
        userID = donationsAccesssor.getString("theUserID");

        new GetDonations("https://myanim.000webhostapp.com/BeKind/getDonations.php",userID).execute();
    }

    public class GetDonations extends AsyncTask<Void, Void, List<DonationsModel>> {

        String task, urlLocation, userID;
//        ProgressDialog alertDialog;

        public GetDonations(String urlLocation, String userID) {
            this.urlLocation = urlLocation;
            this.userID = userID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            alertDialog = new ProgressDialog(MainActivity.this);
//            alertDialog.setMessage(task);
//            alertDialog.setCancelable(false);
//            alertDialog.show();
            noNotify.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<DonationsModel> doInBackground(Void... voids) {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

            try {
                URL url = new URL(urlLocation);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("userID", "UTF-8")+"="+URLEncoder.encode(userID, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String fetch = "";
                while((fetch = bufferedReader.readLine()) != null){
                    stringBuffer.append(fetch);
                }
                String string = stringBuffer.toString();
                inputStream.close();

                // getting the individual values based json in php using the keys there
                JSONObject userobject = new JSONObject(string);
                JSONArray getArray = userobject.getJSONArray("Server_response");
                DonationsList.clear();


                for(int a = 0;a < getArray.length();a++){
                    JSONObject thegetterObject = getArray.getJSONObject(a);
                    DonationsModel themodule = new DonationsModel();
                    themodule.setOrgID(thegetterObject.getString("OrgId"));
                    themodule.setMaterialID(thegetterObject.getString("MaterialId"));
                    themodule.setOrgname(thegetterObject.getString("OrgName"));
                    themodule.setOrgImage(thegetterObject.getString("OrgImage"));
                    themodule.setAddate(thegetterObject.getString("Addate"));
                    themodule.setAddtime(thegetterObject.getString("Addtime"));
                    DonationsList.add(themodule);
                }
                return DonationsList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return DonationsList;
        }


        @Override
        protected void onPostExecute(List <DonationsModel> onPostList) {
            super.onPostExecute(onPostList);
//            alertDialog.dismiss();
            loading.setVisibility(View.GONE);
            if(onPostList.size() == 0){
                noNotify.setVisibility(View.VISIBLE);
            }
            else{
                try {
                    adapter = new Displayer(MyDonations.this, R.layout.notification_attachment, onPostList);
                    donationsListView.setAdapter(adapter);
                    donationsAccesssor.setListHeight(donationsListView);
                }catch (Exception e){
                    return;
                }
            }

        }
    }
    //
    public class Displayer extends ArrayAdapter {
        List <DonationsModel> display;
        int thecon;
        LayoutInflater theInflater;
        public Displayer(@NonNull Context context, int resource, @NonNull List objects) {
            super(context, resource, objects);
            thecon = resource;
            display = objects;
            theInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        class Viewholder{
            TextView notifysubject, notifymessage, notifydate;
            ImageView notifyimage;
            LinearLayout notifylayout;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Displayer.Viewholder theViewHolder;
            //if(convertView == null){
            theViewHolder = new Displayer.Viewholder();
            convertView = theInflater.inflate(thecon,null);
            theViewHolder.notifyimage = (ImageView) convertView.findViewById(R.id.notifyImage);
            theViewHolder.notifysubject = (TextView)convertView.findViewById(R.id.notifyHeading);
//            theViewHolder.notifymessage = (TextView)convertView.findViewById(R.id.notifyMessage);
            theViewHolder.notifydate = (TextView)convertView.findViewById(R.id.notifydate);
            theViewHolder.notifylayout = (LinearLayout) convertView.findViewById(R.id.notifyLayout);


            DisplayImageOptions theImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).
                    cacheOnDisk(true).build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).
                    defaultDisplayImageOptions(theImageOptions).build();
            ImageLoader.getInstance().init(config);
//
            String profileimagelink = "http://iamjesse75.000webhostapp.com/BeKind/pictures/OrgImages/" + display.get(position).getOrgImage();
            mydonationsImageLoader.displayImage(profileimagelink,theViewHolder.notifyimage);
            theViewHolder.notifysubject.setText(display.get(position).getOrgname());
            theViewHolder.notifydate.setText(display.get(position).getAddate() + ",  " + display.get(position).getAddtime());

//            theViewHolder.notifylayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder notifyMe = new AlertDialog.Builder(Notitications.this,R.style.Myalert);
//                    notifyMe.setMessage(display.get(position).getNotifyMessage());
//                    notifyMe.show();
//                }
//            });

            return convertView;

        }
    }
}
