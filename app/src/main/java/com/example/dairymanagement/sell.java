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
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class sell extends AppCompatActivity {

    TextInputEditText dateField, pidField, quantityField, rateField, amountField;
    private int cDate,cMonth,cYear;
    Spinner spinner;
    Button submitBut;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    MenuItem sell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        NavigationView navView = findViewById(R.id.nav_view);

        drawerLayout = findViewById(R.id.my_drawer_layout);

        appdrawerListener aL=new appdrawerListener();
        actionBarDrawerToggle= aL.adddrawerOpener(actionBarDrawerToggle,drawerLayout,this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        dateField=findViewById(R.id.dateField);
        datePicker dp=new datePicker();

        dp.dateonClick(dateField,this);





        spinner=findViewById(R.id.spinner);
        spinnerDropdown sD=new spinnerDropdown();
        sD.spinnerListener(spinner,this);







        submitBut=findViewById(R.id.submit);
        pidField=findViewById(R.id.nameField);
        quantityField=findViewById(R.id.quantityField);
        rateField=findViewById(R.id.rateField);
        amountField=findViewById(R.id.contactField);



        submitBut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                buttonPress(dateField.getText().toString(),pidField.getText().toString(),
                        quantityField.getText().toString(),spinner.getSelectedItem().toString(),
                        rateField.getText().toString(),amountField.getText().toString());
            }
        });










//
        AppDrawer ad=new AppDrawer();
        ad.navitemClick(navView,this);



    }


    Boolean buttonPress(String date,String pid,String quantity,String milkType,String rate,String amount){
        Log.d("t", "123");
        if(date.length()==0){
            dateField.requestFocus();
            dateField.setError("Field cannot be empty");
            return false;
        }
        else if( quantity.length()==0 || quantity.equals("0")){
            quantityField.requestFocus();
            quantityField.setError("Value has to be greater than 0");
            return false;
        }
        else if(rate.length()==0 || rate.equals("0")){
            rateField.requestFocus();
            rateField.setError("Value has to be greater than 0");
        }
        else if(amount.length()==0 || amount.equals("0")){
            amountField.requestFocus();
            amountField.setError("Value has to be greater than 0");
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
