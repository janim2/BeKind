package com.example.bekind;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

    private TextView mTextMessage;
    Accessories mainAccessor;
    BottomNavigationView navigation;

    FragmentManager themanager = getSupportFragmentManager();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("savedLayout",navigation.getSelectedItemId()+"");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String thevlaue = savedInstanceState.getString("savedLayout");
        if(thevlaue.equals("2131230846")){
            navigation.setSelectedItemId(R.id.navigation_home);
            themanager.beginTransaction().replace(R.id.container,new Home()).commit();
        }else if(thevlaue.equals("2131230847")){
            navigation.setSelectedItemId(R.id.navigation_profile);
            themanager.beginTransaction().replace(R.id.container,new Profile()).commit();
        }else{
            navigation.setSelectedItemId(R.id.navigation_settings);
            themanager.beginTransaction().replace(R.id.container,new Settings()).commit();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    themanager.beginTransaction().replace(R.id.container,new Home()).commit();
                    return true;

                case R.id.navigation_profile:
                    themanager.beginTransaction().replace(R.id.container,new Profile()).commit();
                    return true;

                case R.id.navigation_settings:
                    themanager.beginTransaction().replace(R.id.container,new Settings()).commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        if(!mainAccessor.getBoolean("login_checker")){
            startActivity(new Intent(MainActivity.this,Login.class));
        }
        themanager.beginTransaction().replace(R.id.container,new Home()).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainAccessor = new Accessories(MainActivity.this);
        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
