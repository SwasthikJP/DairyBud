package com.example.dairymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class producer extends AppCompatActivity {


    TextInputEditText  pidField, nameField, addressField, contactField;

    Button submitBut;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    MenuItem sell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer);


        NavigationView navView = findViewById(R.id.nav_view);

        drawerLayout = findViewById(R.id.my_drawer_layout);

        appdrawerListener aL=new appdrawerListener();
        actionBarDrawerToggle= aL.adddrawerOpener(actionBarDrawerToggle,drawerLayout,this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);









        submitBut=findViewById(R.id.submit);
        pidField=findViewById(R.id.pidField);
        nameField=findViewById(R.id.nameField);
        addressField=findViewById(R.id.addressField);
        contactField=findViewById(R.id.contactField);



        submitBut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
              if( buttonPress(pidField.getText().toString(),nameField.getText().toString(),
                        addressField.getText().toString(),contactField.getText().toString())){
                 Toast toast= Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT);
                  toast.setMargin(50,50);
                  toast.show();
              }
            }
        });



//
        AppDrawer ad=new AppDrawer();
        ad.navitemClick(navView,this);



    }


    Boolean buttonPress(String pid,String name,String address,String contact){
        Log.d("t", "123");
        if(pid.length()==0){
            pidField.requestFocus();
            pidField.setError("Pid cannot be empty");
            return false;
        }
        else if( name.length()==0 ){
            nameField.requestFocus();
            nameField.setError("Name should not be empty");
            return false;
        }
        else if(address.length()==0 ){
            addressField.requestFocus();
            addressField.setError("Address should not be empty");
            return false;
        }
        else if(contact.length()==0){
            contactField.requestFocus();
            contactField.setError("Contact should not be empty");
            return false;
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}