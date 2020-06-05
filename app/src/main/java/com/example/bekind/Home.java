package com.example.bekind;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bekind.Models.OrgModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    ProgressBar loading;
    List<OrgModel> orgList = new ArrayList<>();
    Displayer adapter;
    Accessories homeAccessor;
    ListView eventlistView;
    ImageLoader homeloader = ImageLoader.getInstance();
    TextView refresh;


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View home =  inflater.inflate(R.layout.fragment_home, container, false);

        homeAccessor = new Accessories(getActivity());
        loading = (ProgressBar) home.findViewById(R.id.loading);
        eventlistView = (ListView) home.findViewById(R.id.orgListView);
        refresh = (TextView) home.findViewById(R.id.refresh);
        new GetEvents().execute();


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetEvents().execute();
            }
        });
        return home;
    }


    public class GetEvents extends AsyncTask<Void, Void, List<OrgModel>> {

        String task, urlLocation;
//        ProgressDialog alertDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            alertDialog = new ProgressDialog(MainActivity.this);
//            alertDialog.setMessage(task);
//            alertDialog.setCancelable(false);
//            alertDialog.show();
            loading.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            urlLocation = "https://myanim.000webhostapp.com/BeKind/getOrgInfo.php";
        }

        @Override
        protected List<OrgModel> doInBackground(Void... voids) {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

            try {
                URL url = new URL(urlLocation);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String fetch = "";
                while((fetch = bufferedReader.readLine()) != null){
                    stringBuffer.append(fetch);
                }
                String string = stringBuffer.toString();
                inputStream.close();

                JSONObject getDetials = new JSONObject(string);
                JSONArray getArray = getDetials.getJSONArray("Server_response");
                orgList.clear();


                for(int a = 0;a < getArray.length();a++){
                    JSONObject thegetterObject = getArray.getJSONObject(a);
                    OrgModel themodule = new OrgModel();
                    themodule.setOrgId(thegetterObject.getString("Orgid"));
                    themodule.setOrgname(thegetterObject.getString("Orgname"));
                    themodule.setOrgimage(thegetterObject.getString("orgImage"));
                    themodule.setOrgInfo(thegetterObject.getString("OrgInfo"));
                    themodule.setOrgPhone(thegetterObject.getString("OrgPhone"));
                    themodule.setOrgEmail(thegetterObject.getString("OrgEmail"));
                    themodule.setOrgAddress(thegetterObject.getString("OrgAddress"));
                    orgList.add(themodule);

                }

                return orgList;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return orgList;
        }

        @Override
        protected void onPostExecute(List <OrgModel> onPostList) {
            super.onPostExecute(onPostList);
//            alertDialog.dismiss();
            loading.setVisibility(View.GONE);
            if(onPostList.size() == 0){
                refresh.setVisibility(View.VISIBLE);
            }
            else{
                try {
                    adapter = new Displayer(getActivity(), R.layout.home_org_attachment, onPostList);
                    eventlistView.setAdapter(adapter);
                    homeAccessor.setListHeight(eventlistView);
                }catch (Exception e){
                    return;
                }
            }

        }
    }
//
    public class Displayer extends ArrayAdapter {
        List <OrgModel> display;
        int thecon;
        LayoutInflater theInflater;
        public Displayer(@NonNull Context context, int resource, @NonNull List objects) {
            super(context, resource, objects);
            thecon = resource;
            display = objects;
            theInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        class Viewholder{
            TextView orgname;
            ImageView orgImage;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Viewholder theViewHolder;
            //if(convertView == null){
            theViewHolder = new Viewholder();
            convertView = theInflater.inflate(thecon,null);
            theViewHolder.orgImage = (ImageView) convertView.findViewById(R.id.orgImage);
            theViewHolder.orgname = (TextView)convertView.findViewById(R.id.orgname);

            DisplayImageOptions theImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).
                    cacheOnDisk(true).build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext()).
                    defaultDisplayImageOptions(theImageOptions).build();
            ImageLoader.getInstance().init(config);

            String orgImage = "https://myanim.000webhostapp.com/BeKind/pictures/OrgImages/" + display.get(position).getOrgId() + ".jpg";
            homeloader.displayImage(orgImage,theViewHolder.orgImage);

            theViewHolder.orgname.setText(display.get(position).getOrgname());

            theViewHolder.orgImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orgInfo = new Intent(getActivity(),OrgDetailsActivity.class);
                    orgInfo.putExtra("orgID",display.get(position).getOrgId());
                    orgInfo.putExtra("OrgImage",display.get(position).getOrgimage());
                    orgInfo.putExtra("OrgName",display.get(position).getOrgname());
                    orgInfo.putExtra("OrgInfo",display.get(position).getOrgInfo());
                    orgInfo.putExtra("OrgPhone",display.get(position).getOrgPhone());
                    orgInfo.putExtra("OrgEmail",display.get(position).getOrgEmail());
                    orgInfo.putExtra("OrgAddress",display.get(position).getOrgAddress());
                    startActivity(orgInfo);
                }
            });
            return convertView;

        }
    }
}


