package com.example.dairymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.FirebaseError;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;



public class updateProducer extends AppCompatActivity {


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

        appdrawerListener aL = new appdrawerListener();
        actionBarDrawerToggle = aL.adddrawerOpener(actionBarDrawerToggle, drawerLayout, this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppDrawer ad = new AppDrawer();
        ad.navitemClick(navView, this);


        submitBut = findViewById(R.id.submit);
        pidField = findViewById(R.id.pidField);
        nameField = findViewById(R.id.nameField);
        addressField = findViewById(R.id.addressField);
        contactField = findViewById(R.id.contactField);

        submitBut.setText("Update Producer");
        pidField.setFocusable(false);

        String docID = getIntent().getStringExtra("docID");

        getData(docID);


        submitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonPress(pidField.getText().toString(), nameField.getText().toString(),
                        addressField.getText().toString(), contactField.getText().toString())) {
//                    Toast toast;
//                 toast= Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT);
//                  toast.setMargin(50,50);
//                  toast.show();


                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    Map<String, Object> data = new HashMap<>();
                    data.put("pid", pidField.getText().toString().toUpperCase());
                    data.put("name", nameField.getText().toString());
                    data.put("address", addressField.getText().toString());
                    data.put("contact", contactField.getText().toString());


                    db.collection("producer")
                            .document(docID).update(data)

                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast toast = Toast.makeText(getApplicationContext(), pidField.getText().toString().toUpperCase() + " is updated", Toast.LENGTH_SHORT);
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

    }

      public  void getData(String docID){
            FirebaseFirestore db=FirebaseFirestore.getInstance();

            db.collection("producer").document(docID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                DocumentSnapshot document= task.getResult();
                                if (document.exists()) {
                                    pidField=findViewById(R.id.pidField);
                                    nameField=findViewById(R.id.nameField);
                                    addressField=findViewById(R.id.addressField);
                                    contactField=findViewById(R.id.contactField);

                                    pidField.setText(document.getData().get("pid").toString());
                                    nameField.setText(document.getData().get("name").toString());
                                    addressField.setText(document.getData().get("address").toString());
                                    contactField.setText(document.getData().get("contact").toString());



                                } else {
                                    Toast toast= Toast.makeText(updateProducer.this,"no such document",Toast.LENGTH_SHORT);
                                    toast.setMargin(50,50);
                                    toast.show();
                                }
                            } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                                Toast toast= Toast.makeText(updateProducer.this,"errorrrr",Toast.LENGTH_SHORT);
                                toast.setMargin(50,50);
                                toast.show();
                            }
                        }
                    });
        }






    Boolean buttonPress(String pid,String name,String address,String contact){
        Log.d("t", "123");
        if(pid.length()==0){
            pidField.requestFocus();
            pidField.setError("Pid should not be empty");
            return false;
        }
        else if( name.length()<=2 ){
            nameField.requestFocus();
            nameField.setError("Name should not be more than 2 characters");
            return false;
        }
        else if(address.length()<=3 ){
            addressField.requestFocus();
            addressField.setError("Enter valid address");
            return false;
        }
        else if(contact.length()==0 || !contact.matches("[5-9][0-9]{9}")){
            contactField.requestFocus();
            contactField.setError("Enter valid mobile number");
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