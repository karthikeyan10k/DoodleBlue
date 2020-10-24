package com.example.doodleblue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

        BottomNavigationView navigation;
        View notificationBadge;
        private ActionBar mActionBar;
        private String TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        switch (item.getItemId()) {


            case R.id.navigation_home:
                fragment = new PriceFragment();
                //mActionBar.setTitle(Html.fromHtml("<font color='#000000'>Prices </font>"));
                View viewActionBar = getLayoutInflater().inflate(R.layout.actionbar, null);
                ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER);
                TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbartext);
                textviewTitle.setText("Prices");
                mActionBar.setCustomView(viewActionBar, params);
                mActionBar.setDisplayShowCustomEnabled(true);
                mActionBar.setDisplayShowTitleEnabled(false);
                mActionBar.setDisplayHomeAsUpEnabled(true);
                mActionBar.setHomeButtonEnabled(true);
                TITLE="Prices";
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public void onBackPressed() {

     super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();



        loadFragment(new PriceFragment());
        navigation.setSelectedItemId(R.id.navigation_home);

    }



}
