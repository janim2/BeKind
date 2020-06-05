package com.example.bekind;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.preference.PreferenceGroup;
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

public class Notitications extends AppCompatActivity {
    ProgressBar loading;
    List<NotifyModel> NotificationsList = new ArrayList<>();
    Displayer adapter;
    Accessories notificationsAccessor;
    ListView notfifyListView;
    String userID;
    TextView noNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notitications);
        getSupportActionBar().setTitle("Notifications");

        notificationsAccessor = new Accessories(Notitications.this);

        loading = (ProgressBar) findViewById(R.id.loading);
        notfifyListView = (ListView) findViewById(R.id.notifylist);
        noNotify = (TextView) findViewById(R.id.noNotify);
        userID = notificationsAccessor.getString("theUserID");

        new GetNotifications("https://myanim.000webhostapp.com/BeKind/BeKind/getNotifications.php",userID).execute();

        noNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetNotifications("https://myanim.000webhostapp.com/BeKind/getNotifications.php",userID).execute();
            }
        });
    }

    public class GetNotifications extends AsyncTask<Void, Void, List<NotifyModel>> {

        String task, urlLocation, userID;
//        ProgressDialog alertDialog;

        public GetNotifications(String urlLocation, String userID) {
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
        protected List<NotifyModel> doInBackground(Void... voids) {
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
                NotificationsList.clear();


                for(int a = 0;a < getArray.length();a++){
                    JSONObject thegetterObject = getArray.getJSONObject(a);
                    NotifyModel themodule = new NotifyModel();
                    themodule.setNotifyID(thegetterObject.getString("NotifyID"));
                    themodule.setNotifyImage(thegetterObject.getString("NotifyImage"));
                    themodule.setNotifySubject(thegetterObject.getString("NotifySubject"));
                    themodule.setNotifyMessage(thegetterObject.getString("NotifyMessage"));
                    themodule.setAddate(thegetterObject.getString("Addate"));
                    themodule.setAddtime(thegetterObject.getString("Addtime"));
                    NotificationsList.add(themodule);
                }
                return NotificationsList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return NotificationsList;
        }


        @Override
        protected void onPostExecute(List <NotifyModel> onPostList) {
            super.onPostExecute(onPostList);
//            alertDialog.dismiss();
            loading.setVisibility(View.GONE);
            if(onPostList.size() == 0){
                noNotify.setVisibility(View.VISIBLE);
            }
            else{
                try {
                    adapter = new Displayer(Notitications.this, R.layout.notification_attachment, onPostList);
                    notfifyListView.setAdapter(adapter);
                    notificationsAccessor.setListHeight(notfifyListView);
                }catch (Exception e){
                    return;
                }
            }

        }
    }
    //
    public class Displayer extends ArrayAdapter {
        List <NotifyModel> display;
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

            if(display.get(position).getNotifyImage().equals("Re")){
                theViewHolder.notifyimage.setImageDrawable(getResources().getDrawable(R.drawable.helpinghand));
                theViewHolder.notifysubject.setText(display.get(position).getNotifySubject());
//                theViewHolder.notifymessage.setText(display.get(position).getNotifyMessage());
                theViewHolder.notifydate.setText(display.get(position).getAddate() + ",  " + display.get(position).getAddtime());
            }

            if(display.get(position).getNotifyImage().equals("UpS")){
                theViewHolder.notifyimage.setImageDrawable(getResources().getDrawable(R.drawable.notifyre));
                theViewHolder.notifysubject.setText(display.get(position).getNotifySubject());
//                theViewHolder.notifymessage.setText(display.get(position).getNotifyMessage());
                theViewHolder.notifydate.setText(display.get(position).getAddate() + ",  " + display.get(position).getAddtime());
            }

            if(display.get(position).getNotifyImage().equals("UpSF")){
                theViewHolder.notifyimage.setImageDrawable(getResources().getDrawable(R.drawable.delete));
                theViewHolder.notifysubject.setText(display.get(position).getNotifySubject());
//                theViewHolder.notifymessage.setText(display.get(position).getNotifyMessage());
                theViewHolder.notifydate.setText(display.get(position).getAddate() + ",  " + display.get(position).getAddtime());
            }

            if(display.get(position).getNotifyImage().equals("DoS")){
                theViewHolder.notifyimage.setImageDrawable(getResources().getDrawable(R.drawable.donations));
                theViewHolder.notifysubject.setText(display.get(position).getNotifySubject());
//                theViewHolder.notifymessage.setText(display.get(position).getNotifyMessage());
                theViewHolder.notifydate.setText(display.get(position).getAddate() + ",  " + display.get(position).getAddtime());
            }

            if(display.get(position).getNotifyImage().equals("DoF")){
                theViewHolder.notifyimage.setImageDrawable(getResources().getDrawable(R.drawable.sad_24dp));
                theViewHolder.notifysubject.setText(display.get(position).getNotifySubject());
//                theViewHolder.notifymessage.setText(display.get(position).getNotifyMessage());
                theViewHolder.notifydate.setText(display.get(position).getAddate() + ",  " + display.get(position).getAddtime());
            }

            if(display.get(position).getNotifyImage().equals("Ss")){
                theViewHolder.notifyimage.setImageDrawable(getResources().getDrawable(R.drawable.donations));
                theViewHolder.notifysubject.setText(display.get(position).getNotifySubject());
//                theViewHolder.notifymessage.setText(display.get(position).getNotifyMessage());
                theViewHolder.notifydate.setText(display.get(position).getAddate() + ",  " + display.get(position).getAddtime());
            }

            if(display.get(position).getNotifyImage().equals("Sf")){
                theViewHolder.notifyimage.setImageDrawable(getResources().getDrawable(R.drawable.sad_24dp));
                theViewHolder.notifysubject.setText(display.get(position).getNotifySubject());
//                theViewHolder.notifymessage.setText(display.get(position).getNotifyMessage());
                theViewHolder.notifydate.setText(display.get(position).getAddate() + ",  " + display.get(position).getAddtime());
            }



            theViewHolder.notifylayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder notifyMe = new AlertDialog.Builder(Notitications.this,R.style.Myalert);
                    notifyMe.setMessage(display.get(position).getNotifyMessage());
                    notifyMe.show();
                }
            });

            return convertView;

        }
    }


}
