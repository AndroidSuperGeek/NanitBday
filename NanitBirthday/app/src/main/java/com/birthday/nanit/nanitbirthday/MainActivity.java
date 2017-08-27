package com.birthday.nanit.nanitbirthday;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        showDetailsScreen();
    }

    /**
     * Start a fragment to show the Details Screen
     */
    private void showDetailsScreen() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add (R.id.contentFrame, new BirthdayDetailsFragment ())
                .addToBackStack (BirthdayDetailsFragment.class.getSimpleName ())
                .commit();
    }
}
