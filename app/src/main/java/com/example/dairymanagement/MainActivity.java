package com.example.dairymanagement;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.annotation.NonNull;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.view.MenuItem;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);


//        NavHostFragment navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
//        NavController navController = navHostFragment.getNavController();
//        NavigationView navView = findViewById(R.id.nav_view);
//        NavigationUI.setupWithNavController(navView, navController);
//        AppBarConfiguration appBarConfiguration =
//                new AppBarConfiguration.Builder(navController.getGraph())
//                        .setDrawerLayout(drawerLayout)
//                        .build();


        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        dateField=findViewById(R.id.dateField);

        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            final Calendar cal = Calendar.getInstance();
            cDate=cal.get(Calendar.DATE);
            cMonth=cal.get(Calendar.MONTH);
            cYear=cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                            dateField.setText(y+"-"+m+"-"+d);
                            }
                        },cYear,cMonth,cDate );
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });



        spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.milkType, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                dateField.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





       submitBut=findViewById(R.id.submit);
       pidField=findViewById(R.id.pidField);
        quantityField=findViewById(R.id.quantityField);
       rateField=findViewById(R.id.rateField);
        amountField=findViewById(R.id.amountField);



        submitBut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                buttonPress(dateField.getText().toString(),pidField.getText().toString(),
                        quantityField.getText().toString(),spinner.getSelectedItem().toString(),
                        rateField.getText().toString(),amountField.getText().toString());
            }
        });





//        sell=findViewById(R.id.sell);
//        sell.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                Intent intent =  Intent(menuItem.getActionView(), sell.class);
//                startActivity(intent);
//                return false;
//            }
//        });




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

//        if(item.getItemId()==R.id.sell){
//            Intent intent =new  Intent(this, sell.class);
//            startActivity(intent);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }




}