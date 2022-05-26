package com.example.dairymanagement;

import android.app.Activity;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

public class appdrawerListener {

    public ActionBarDrawerToggle adddrawerOpener( ActionBarDrawerToggle actionBarDrawerToggle,DrawerLayout drawerLayout, Activity activity){


        actionBarDrawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, R.string.nav_open, R.string.nav_close);
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        return actionBarDrawerToggle;

        // to make the Navigation drawer icon always appear on the action bar

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//    @NonNull MenuItem item


}


