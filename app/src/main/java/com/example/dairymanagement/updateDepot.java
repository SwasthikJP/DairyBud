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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class updateDepot extends AppCompatActivity {

    TextInputEditText  nameField, addressField, emailField, maxcowField,maxbuffaloField,curcowField,curbuffaloField;

    Button submitBut;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
String uid;
    MenuItem sell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_depot);

        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        uid=firebaseUser.getUid();

        NavigationView navView = findViewById(R.id.nav_view);

        drawerLayout = findViewById(R.id.my_drawer_layout);

        appdrawerListener aL = new appdrawerListener();
        actionBarDrawerToggle = aL.adddrawerOpener(actionBarDrawerToggle, drawerLayout, this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppDrawer ad = new AppDrawer();
        ad.navitemClick(navView, this);


        submitBut = findViewById(R.id.submit);

        nameField = findViewById(R.id.nameField);
        addressField = findViewById(R.id.addressField);
        emailField = findViewById(R.id.emailField);
        maxcowField=findViewById(R.id.maxcowField);
        maxbuffaloField=findViewById(R.id.maxbuffaloField);
        curcowField=findViewById(R.id.curcowField);
        curbuffaloField=findViewById(R.id.curbuffaloField);




//        String docID = getIntent().getStringExtra("docID");

        getData("docID");




        submitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  if (buttonPress( nameField.getText().toString(),addressField.getText().toString(), emailField.getText().toString(),
      maxcowField.getText().toString(),maxbuffaloField.getText().toString(),curcowField.getText().toString(),
        curbuffaloField.getText().toString() )) {



                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    Map<String, Object> data = new HashMap<>();

                    data.put("name", nameField.getText().toString());
                    data.put("address", addressField.getText().toString());
                    data.put("email", emailField.getText().toString());
      data.put("maxcow", maxcowField.getText().toString());
      data.put("maxbuffalo", maxbuffaloField.getText().toString());
      data.put("curcow", curcowField.getText().toString());
      data.put("curbuffalo", curbuffaloField.getText().toString());

      db.collection("users")
                            .document(uid).update(data)

                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Data is updated", Toast.LENGTH_SHORT);
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

        db.collection("users").document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot document= task.getResult();
                            if (document.exists()) {
                                nameField=findViewById(R.id.nameField);
                                addressField=findViewById(R.id.addressField);
                                emailField=findViewById(R.id.emailField);

                                nameField.setText(document.getData().get("name").toString());
                                addressField.setText(document.getData().get("address").toString());
                                emailField.setText(document.getData().get("email").toString());
                                maxcowField.setText(document.getData().get("maxcow").toString());
                                maxbuffaloField.setText(document.getData().get("maxbuffalo").toString());
                                curcowField.setText(document.getData().get("curcow").toString());
                                curbuffaloField.setText(document.getData().get("curbuffalo").toString());



                            } else {
                                Toast toast= Toast.makeText(updateDepot.this,"no such document",Toast.LENGTH_SHORT);
                                toast.setMargin(50,50);
                                toast.show();
                            }
                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast toast= Toast.makeText(updateDepot.this,"errorrrr",Toast.LENGTH_SHORT);
                            toast.setMargin(50,50);
                            toast.show();
                        }
                    }
                });
    }






    Boolean buttonPress(String name,String address,String contact,String maxcow,String maxbuf,
                        String curcow,String curbuf){
        Log.d("t", "123");
        if( name.length()<=2 ){
            nameField.requestFocus();
            nameField.setError("Name should not be more than 2 characters");
            return false;
        }
        else if(address.length()<=3 ){
            addressField.requestFocus();
            addressField.setError("Enter valid address");
            return false;
        }
        else if(contact.length()==0 || !contact.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")){
            emailField.requestFocus();
            emailField.setError("Enter valid email");
            return false;
        }  else if( maxcow.length()==0 || maxcow.equals("0")){
            maxcowField.requestFocus();
            maxcowField.setError("Value has to be greater than 0");
            return false;
        }  else if( maxbuf.length()==0 || maxbuf.equals("0")){
            maxbuffaloField.requestFocus();
            maxbuffaloField.setError("Value has to be greater than 0");
            return false;
        }  else if( curcow.length()==0 ){
            curcowField.requestFocus();
            curcowField.setError("Value has to be greater than 0");
            return false;
        }  else if( curbuf.length()==0 ){
            curbuffaloField.requestFocus();
            curbuffaloField.setError("Value has to be greater than 0");
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