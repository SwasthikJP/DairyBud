package com.example.dairymanagement;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    TextInputEditText dateField, quantityField, rateField, amountField;
   private int cDate,cMonth,cYear;
   Spinner spinner,pidField;
    Button submitBut;
    public DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    MenuItem sell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NavigationView navView = findViewById(R.id.nav_view);

        drawerLayout = findViewById(R.id.my_drawer_layout);

        appdrawerListener aL=new appdrawerListener();
       actionBarDrawerToggle= aL.adddrawerOpener(actionBarDrawerToggle,drawerLayout,this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        dateField=findViewById(R.id.dateField);
        datePicker dp=new datePicker();

        dp.dateonClick(dateField,MainActivity.this);





        spinner=findViewById(R.id.spinner);
 spinnerDropdown sD=new spinnerDropdown();
 sD.spinnerListener(spinner,MainActivity.this);

          pidField=findViewById(R.id.spinner2);
         sD.spinnerListenerFirebase(pidField,MainActivity.this,"producer");
        Toast toast= Toast.makeText(getApplicationContext(),pidField.getSelectedItem().toString(),Toast.LENGTH_SHORT);
        toast.setMargin(50,50);
        toast.show();





       submitBut=findViewById(R.id.submit);
        quantityField=findViewById(R.id.quantityField);
       rateField=findViewById(R.id.rateField);
        amountField=findViewById(R.id.contactField);



        submitBut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
              if( buttonPress(dateField.getText().toString(),pidField.getSelectedItem().toString(),
                        quantityField.getText().toString(),spinner.getSelectedItem().toString(),
                        rateField.getText().toString(),amountField.getText().toString()))
              {
                  FirebaseFirestore db=FirebaseFirestore.getInstance();

                  Map<String, Object> data = new HashMap<>();
                  data.put("userID",pidField.getSelectedItem().toString());
                  data.put("type","buy");
                  data.put("milktype",spinner.getSelectedItem().toString());
                  data.put("quantity",quantityField.getText().toString());
                  data.put("rate",rateField.getText().toString());
                  data.put("created", FieldValue.serverTimestamp());
                  data.put("amount",amountField.getText().toString());


                  db.collection("transaction")
                          .add(data)
                          .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                              @Override
                              public void onSuccess(DocumentReference documentReference) {
                                  Toast toast= Toast.makeText(getApplicationContext(),"Transaction Successful",Toast.LENGTH_SHORT);
                                  toast.setMargin(50,50);
                                  toast.show();
                              }
                          })
                          .addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {

                                  Toast toast= Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_SHORT);
                                  toast.setMargin(50,50);
                                  toast.show();
                              }
                          });
              }
            }
        });


       quantityField.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


           }

           @Override
           public void afterTextChanged(Editable editable) {
               String r=rateField.getText().toString();
               if(editable.toString().length()!=0 && r.length()!=0) {
                   amountField.setText("" + Float.parseFloat(editable.toString()) *
                           Float.parseFloat(r));
               }

           }
       });


       rateField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String q=quantityField.getText().toString();
                if(editable.toString().length()!=0 && q.length()!=0) {
                    amountField.setText("" + Float.parseFloat(editable.toString()) *
                            Float.parseFloat(q));
                }

            }
        });


//        quantityField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                String q=quantityField.getText().toString(),r=rateField.getText().toString();
//                    if(q.length()!=0 && r.length()!=0) {
//                        amountField.setText("" + Float.parseFloat(q) *
//                                Float.parseFloat(r));
//                    }
//            }
//        });










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
         else if(pid.equals("No selection")){
//             pidField.requestFocus();

             Toast    toast= Toast.makeText(getApplicationContext(),"Select appropriate PID",Toast.LENGTH_SHORT);
             toast.setMargin(50,50);
             toast.show();
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