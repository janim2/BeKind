package com.example.bekind;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {
    LinearLayout parthnersLayout,contactLayout, helpLayout, aboutLayout, termsLayout,privacyLayout;
    public Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View settings =  inflater.inflate(R.layout.settings, container, false);
        parthnersLayout = (LinearLayout) settings.findViewById(R.id.partnersLayout);
        contactLayout = (LinearLayout)settings.findViewById(R.id.contactusLayout);
        helpLayout = (LinearLayout)settings.findViewById(R.id.helpLayout);
        aboutLayout = (LinearLayout)settings.findViewById(R.id.aboutlayout);
        termsLayout = (LinearLayout)settings.findViewById(R.id.termsLayout);
        privacyLayout = (LinearLayout)settings.findViewById(R.id.privacypolicyLayout);

//        clicking on the parthner
        parthnersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Pathners.class));

            }
        });

//        clicking on the contact us layout
        contactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), Contact.class));
            }
        });

//        clicking on the help
        helpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), Help.class));
            }
        });

//        clicking on the about
        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), About.class));
            }
        });

//        clicking on the terms
        termsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TermsAndConditions.class));
            }
        });

//        clicking on the privacy policy
        privacyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PrivacyPolicy.class));
            }
        });
        return settings;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
