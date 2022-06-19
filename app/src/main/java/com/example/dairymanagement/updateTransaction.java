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
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class updateTransaction extends AppCompatActivity {



    TextInputEditText dateField, quantityField, rateField, amountField,typeField,idField;
    private int cDate,cMonth,cYear;
    Spinner spinner;

    Button submitBut;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_transaction);

        NavigationView navView = findViewById(R.id.nav_view);

        drawerLayout = findViewById(R.id.my_drawer_layout);

        appdrawerListener aL=new appdrawerListener();
        actionBarDrawerToggle= aL.adddrawerOpener(actionBarDrawerToggle,drawerLayout,this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        dateField=findViewById(R.id.dateField);
//        datePicker dp=new datePicker();
//
//        dp.dateonClick(dateField,this);
        dateField.setFocusable(false);




        spinner=findViewById(R.id.spinner);
        spinnerDropdown sD=new spinnerDropdown();
        sD.spinnerListener(spinner,this);

        idField=findViewById(R.id.spinner2);
//        sD.spinnerListenerFirebase(idField,sell.this,"consumer");
//        Toast toast= Toast.makeText(getApplicationContext(),idField.getSelectedItem().toString(),Toast.LENGTH_SHORT);
//        toast.setMargin(50,50);
//        toast.show();




        submitBut=findViewById(R.id.submit);
        quantityField=findViewById(R.id.quantityField);
        rateField=findViewById(R.id.rateField);
        amountField=findViewById(R.id.contactField);
        typeField=findViewById(R.id.typeField);


        idField.setFocusable(false);
        typeField.setFocusable(false);


        String docID = getIntent().getStringExtra("docID");

        getData(docID);



        submitBut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(buttonPress(dateField.getText().toString(),idField.getText().toString(),
                        quantityField.getText().toString(),spinner.getSelectedItem().toString(),
                        rateField.getText().toString(),amountField.getText().toString()))
                {
                    FirebaseFirestore db=FirebaseFirestore.getInstance();

                    Map<String, Object> data = new HashMap<>();
//                    data.put("userID",idField.getText().toString());
//                    data.put("type",typeField.getText().toString());
                    data.put("milktype",spinner.getSelectedItem().toString());
                    data.put("quantity",quantityField.getText().toString());
                    data.put("rate",rateField.getText().toString());
//                    data.put("created", dateField.getText().toString());
                    data.put("amount",amountField.getText().toString());


                    db.collection("transaction")
                            .document(docID).update(data)

                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast toast = Toast.makeText(getApplicationContext(), idField.getText().toString().toUpperCase() + " is updated", Toast.LENGTH_SHORT);
                                    toast.setMargin(50, 50);
                                    toast.show();
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


    public  void getData(String docID){
        FirebaseFirestore db=FirebaseFirestore.getInstance();

        db.collection("transaction").document(docID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot document= task.getResult();
                            if (document.exists()) {
                                idField=findViewById(R.id.spinner2);
//                                nameField=findViewById(R.id.nameField);
//                                addressField=findViewById(R.id.addressField);
//                                contactField=findViewById(R.id.contactField);

                                idField.setText(document.getData().get("userID").toString());

                                Timestamp timestamp=(Timestamp) document.getData().get("created");
                                DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
                                Date ddate=timestamp.toDate();
                                dateField.setText(dateFormat.format(ddate));



                                typeField.setText(document.getData().get("type").toString());
                                spinner.setSelection(document.getData().get("milktype").toString().equals("Cow")?0:1);
                                quantityField.setText(document.getData().get("quantity").toString());
                                rateField.setText(document.getData().get("rate").toString());



                            } else {
                                Toast toast= Toast.makeText(updateTransaction.this,"no such document",Toast.LENGTH_SHORT);
                                toast.setMargin(50,50);
                                toast.show();
                            }
                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast toast= Toast.makeText(updateTransaction.this,"errorrrr",Toast.LENGTH_SHORT);
                            toast.setMargin(50,50);
                            toast.show();
                        }
                    }
                });
    }


    Boolean buttonPress(String date,String id,String quantity,String milkType,String rate,String amount){
        Log.d("t", "123");
        if(date.length()==0){
            dateField.requestFocus();
            dateField.setError("Field cannot be empty");
            return false;
        }
        else if(id.equals("No selection")){
//             idField.requestFocus();

            Toast    toast= Toast.makeText(getApplicationContext(),"Select appropriate ID",Toast.LENGTH_SHORT);
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
