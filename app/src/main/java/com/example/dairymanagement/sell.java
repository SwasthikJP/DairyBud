package com.example.dairymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class sell extends AppCompatActivity {

    TextInputEditText dateField, quantityField, rateField, amountField;
    private int cDate,cMonth,cYear;
    Spinner spinner,cidField;
    Button submitBut;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
String uid;
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


        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        uid=firebaseUser.getUid();



        dateField=findViewById(R.id.dateField);
//        datePicker dp=new datePicker();
//
//        dp.dateonClick(dateField,this);

        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        dateField.setText(thisDate);
        dateField.setFocusable(false);
        dateField.setEnabled(false);





        spinner=findViewById(R.id.spinner);
        spinnerDropdown sD=new spinnerDropdown();
        sD.spinnerListener(spinner,this);

        cidField=findViewById(R.id.spinner2);
        sD.spinnerListenerFirebase(cidField,sell.this,"consumer");
//        Toast toast= Toast.makeText(getApplicationContext(),cidField.getSelectedItem().toString(),Toast.LENGTH_SHORT);
//        toast.setMargin(50,50);
//        toast.show();




        submitBut=findViewById(R.id.submit);
        quantityField=findViewById(R.id.quantityField);
        rateField=findViewById(R.id.rateField);
        amountField=findViewById(R.id.contactField);



        submitBut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               if(buttonPress(dateField.getText().toString(),cidField.getSelectedItem().toString(),
                        quantityField.getText().toString(),spinner.getSelectedItem().toString(),
                        rateField.getText().toString(),amountField.getText().toString()))
               {
//                   FirebaseFirestore db=FirebaseFirestore.getInstance();
//
//                   Map<String, Object> data = new HashMap<>();
//                   data.put("userID",cidField.getSelectedItem().toString());
//                   data.put("type","sell");
//                   data.put("milktype",spinner.getSelectedItem().toString());
//                   data.put("quantity",quantityField.getText().toString());
//                   data.put("rate",rateField.getText().toString());
//                   data.put("created", FieldValue.serverTimestamp());
//                   data.put("amount",amountField.getText().toString());
//
//
//                   db.collection("transaction")
//                           .add(data)
//                           .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                               @Override
//                               public void onSuccess(DocumentReference documentReference) {
//                                   Toast toast= Toast.makeText(getApplicationContext(),"Transaction Successful",Toast.LENGTH_SHORT);
//                                   toast.setMargin(50,50);
//                                   toast.show();
//                               }
//                           })
//                           .addOnFailureListener(new OnFailureListener() {
//                               @Override
//                               public void onFailure(@NonNull Exception e) {
//
//                                   Toast toast= Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_SHORT);
//                                   toast.setMargin(50,50);
//                                   toast.show();
//                               }
//                           });

                   /////


                   FirebaseFirestore db=FirebaseFirestore.getInstance();

                   DocumentReference documentReference = db.collection("users").document(uid);

                   documentReference.get()
                           .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                               @Override

                               public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                   if (task.isSuccessful()) {

                                       DocumentSnapshot document = task.getResult();
                                       if (document.exists()) {

                                           if (spinner.getSelectedItem().toString().equals("Cow")) {
                                               float maxcow = Float.parseFloat(document.getData().get("maxcow").toString());
                                               float curcow = Float.parseFloat(document.getData().get("curcow").toString());
                                               float temp = Float.parseFloat(quantityField.getText().toString());
                                               if ( (curcow - temp)>0) {
                                                   curcow -= temp;
                                                   Map<String, Object> tempdata = new HashMap<>();
                                                   tempdata.put("curcow", String.valueOf(curcow));

                                                   documentReference.update(tempdata)
                                                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                               @Override
                                                               public void onSuccess(Void aVoid) {
                                                                   Toast toast = Toast.makeText(getApplicationContext(), "curcow updated", Toast.LENGTH_SHORT);
                                                                   toast.setMargin(50, 50);
                                                                   toast.show();
                                                                   createData();
                                                               }
                                                           })
                                                           .addOnFailureListener(new OnFailureListener() {
                                                               @Override
                                                               public void onFailure(@NonNull Exception e) {
                                                                   Toast toast = Toast.makeText(getApplicationContext(), "Error occured", Toast.LENGTH_SHORT);
                                                                   toast.setMargin(50, 50);
                                                                   toast.show();
                                                               }
                                                           });
                                               } else {
                                                   quantityField.requestFocus();
                                                   quantityField.setError("Value has to be less than " + (curcow));

//                                                  throw new ArithmeticException("error");
                                               }
                                           } else {
                                               float maxbuffalo = Float.parseFloat(document.getData().get("maxbuffalo").toString());
                                               float curbuffalo = Float.parseFloat(document.getData().get("curbuffalo").toString());
                                               float temp = Float.parseFloat(quantityField.getText().toString());
                                               if ((curbuffalo - temp)>0) {
                                                   curbuffalo -= temp;
                                                   Map<String, Object> tempdata = new HashMap<>();
                                                   tempdata.put("curbuffalo", String.valueOf(curbuffalo));

                                                   documentReference.update(tempdata)
                                                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                               @Override
                                                               public void onSuccess(Void aVoid) {
                                                                   Toast toast = Toast.makeText(getApplicationContext(), "curbuffalo updated", Toast.LENGTH_SHORT);
                                                                   toast.setMargin(50, 50);
                                                                   toast.show();
                                                                   createData();
                                                               }
                                                           })
                                                           .addOnFailureListener(new OnFailureListener() {
                                                               @Override
                                                               public void onFailure(@NonNull Exception e) {
                                                                   Toast toast = Toast.makeText(getApplicationContext(), "Error occured", Toast.LENGTH_SHORT);
                                                                   toast.setMargin(50, 50);
                                                                   toast.show();
                                                               }
                                                           });
                                               } else {
                                                   quantityField.requestFocus();
                                                   quantityField.setError("Value has to be less than " + (curbuffalo));
//                                                  throw new ArithmeticException("error");
                                               }
                                           }

                                       } else {
                                           Toast toast = Toast.makeText(sell.this, "no such document", Toast.LENGTH_SHORT);
                                           toast.setMargin(50, 50);
                                           toast.show();
                                       }
                                   } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                                       Toast toast = Toast.makeText(sell.this, "errorrrr", Toast.LENGTH_SHORT);
                                       toast.setMargin(50, 50);
                                       toast.show();
                                   }

                               }


                           });

                   ////
               }
            }
        });



        //

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


        //







//
        AppDrawer ad=new AppDrawer();
        ad.navitemClick(navView,this);



    }


    void createData(){


        Map<String, Object> data = new HashMap<>();
        data.put("userID",cidField.getSelectedItem().toString());
        data.put("type","sell");
        data.put("milktype",spinner.getSelectedItem().toString());
        data.put("quantity",quantityField.getText().toString());
        data.put("rate",rateField.getText().toString());
        data.put("created", FieldValue.serverTimestamp());
        data.put("amount",amountField.getText().toString());
        data.put("uid",uid);

        FirebaseFirestore db=FirebaseFirestore.getInstance();
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


    Boolean buttonPress(String date,String cid,String quantity,String milkType,String rate,String amount){
        Log.d("t", "123");
        if(date.length()==0){
            dateField.requestFocus();
            dateField.setError("Field cannot be empty");
            return false;
        }
        else if(cid.equals("No selection")){
//             cidField.requestFocus();

            Toast    toast= Toast.makeText(getApplicationContext(),"Select appropriate CID",Toast.LENGTH_SHORT);
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
