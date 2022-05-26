package com.example.dairymanagement;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.navigation.NavigationView;

public class AppDrawer {



    public void navitemClick(NavigationView navView,Context context){


         navView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Toast toast;
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.buy:
                        toast=Toast.makeText(context,"buy",Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                         intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        break;

                    case R.id.sell:
                         toast=Toast.makeText(context,"sell",Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                        intent = new Intent(context, sell.class);
                        context.startActivity(intent);
                        break;

                    case R.id.Producer:
                        toast=Toast.makeText(context,"prodc",Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                         intent = new Intent(context,producer.class);
                        context.startActivity(intent);
                        break;

                    case R.id.Consumer:
                         toast=Toast.makeText(context,"cons",Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                         intent = new Intent(context, consumer.class);
                        context.startActivity(intent);
                        break;

                }
                return true;
            }
        });
    }
}
