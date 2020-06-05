package com.example.bekind;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    TextView fullname, phone_number, email, homeaddress;
    Accessories profileAccessor;
    String fullnameString;
    CircleImageView profileImage;
    CardView editCard, notifyCard, donationsCard, deleteCard, logoutCard;
    ProgressBar loading;
    String userID;

    //    initialzing the iamgeloader
    ImageLoader profileloader = ImageLoader.getInstance();

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profile = inflater.inflate(R.layout.fragment_profile, container, false);
        profileAccessor = new Accessories(getActivity());
        fullname = (TextView) profile.findViewById(R.id.fullname);
        phone_number = (TextView) profile.findViewById(R.id.phonenumber);
        email = (TextView) profile.findViewById(R.id.email);
        homeaddress = (TextView) profile.findViewById(R.id.address);
        profileImage = (CircleImageView) profile.findViewById(R.id.profilepic);
        editCard = (CardView) profile.findViewById(R.id.editCard);
        notifyCard = (CardView) profile.findViewById(R.id.notificationCard);
        donationsCard = (CardView) profile.findViewById(R.id.donationsCard);
        deleteCard = (CardView) profile.findViewById(R.id.deleteCard);
        logoutCard = (CardView) profile.findViewById(R.id.logoutCard);
        loading = (ProgressBar) profile.findViewById(R.id.loading);

        userID = profileAccessor.getString("theUserID");


        if(profileAccessor.getString("theprofileImage").equals("nothing")){
            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.profile));
        }else{
            //prep work before image is loaded is to load it into the cache
        DisplayImageOptions theImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).
                cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext()).
                defaultDisplayImageOptions(theImageOptions).build();
        ImageLoader.getInstance().init(config);
//
        String profileimagelink = "http://iamjesse75.000webhostapp.com/BeKind/pictures/profilepics/" + profileAccessor.getString("theprofileImage");
        profileloader.displayImage(profileimagelink,profileImage);
        }

        fullnameString = profileAccessor.getString("Firstname") + " " + profileAccessor.getString("Lastname");

        fullname.setText(fullnameString);
        phone_number.setText(profileAccessor.getString("phone"));
        email.setText(profileAccessor.getString("Email"));
        homeaddress.setText(profileAccessor.getString("Home_Address"));

//        loginAccessor.put("theprofileImage",profileImage);

//        onclicks

        editCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfile = new Intent(getActivity(), EditProfile.class);
                startActivity(editProfile);
            }
        });

        notifyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Notitications.class));
            }
        });

        donationsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyDonations.class));
            }
        });

        deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder delete = new AlertDialog.Builder(getActivity(), R.style.Myalertred);
                delete.setTitle("Delete Account");
                delete.setMessage("Whats happening? Many children cannot make it without your help. Please reconsider.");
                delete.setNegativeButton("Delete Account", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new LogoutOrDeleteAccount("https://myanim.000webhostapp.com/BeKind/deleteAccount.php",userID).execute();
                    }
                });

                delete.setPositiveButton("Stay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                delete.show();
            }
        });

        logoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder logout = new AlertDialog.Builder(getActivity(), R.style.Myalert);
                logout.setTitle("Logging Out?");
                logout.setMessage("Leaving us? Who would help these kids now?");
                logout.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new LogoutOrDeleteAccount("https://myanim.000webhostapp.com/BeKind/logout.php",userID).execute();
                    }
                });

                logout.setPositiveButton("Stay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                logout.show();
            }
        });

        return profile;
    }

    class LogoutOrDeleteAccount extends AsyncTask<Void, Void, String> {

        String url_location, user_id;

        public LogoutOrDeleteAccount(String url_location, String user_id) {
            this.url_location = url_location;
            this.user_id = user_id;
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

                String data = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");

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
            if (s.equals("You are out Now") || s.equals("Account Deleted")) {
                profileAccessor.put("login_checker", false);
                profileAccessor.clearStore();
                startActivity(new Intent(getActivity(), Login.class));
            }
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }

    }
}