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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class consumer extends AppCompatActivity {

    TextInputEditText  cidField, nameField, addressField, contactField;

    Button submitBut;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    MenuItem sell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer);


        NavigationView navView = findViewById(R.id.nav_view);

        drawerLayout = findViewById(R.id.my_drawer_layout);

        appdrawerListener aL=new appdrawerListener();
        actionBarDrawerToggle= aL.adddrawerOpener(actionBarDrawerToggle,drawerLayout,this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);









        submitBut=findViewById(R.id.submit);
        cidField=findViewById(R.id.pidField);
        nameField=findViewById(R.id.nameField);
        addressField=findViewById(R.id.addressField);
        contactField=findViewById(R.id.contactField);



        submitBut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if( buttonPress(cidField.getText().toString(),nameField.getText().toString(),
                        addressField.getText().toString(),contactField.getText().toString())){
//                    Toast toast;
//                 toast= Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT);
//                  toast.setMargin(50,50);
//                  toast.show();


                    FirebaseFirestore db=FirebaseFirestore.getInstance();

                    Map<String, Object> data = new HashMap<>();
                    data.put("cid",cidField.getText().toString().toUpperCase());
                    data.put("name",nameField.getText().toString());
                    data.put("address",addressField.getText().toString());
                    data.put("contact",contactField.getText().toString());
                    data.put("created", FieldValue.serverTimestamp());


                    db.collection("consumer")
                            .whereEqualTo("cid",cidField.getText().toString().toUpperCase() )
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    if (task.getResult().getDocuments().size() ==1){
                                        Toast toast= Toast.makeText(getApplicationContext(),cidField.getText().toString().toUpperCase() +" already exists",Toast.LENGTH_SHORT);
                                        toast.setMargin(50,50);
                                        toast.show();
                                    }else{

                                        db.collection("consumer")
                                                .add(data)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast toast= Toast.makeText(getApplicationContext(),"Consumer is added",Toast.LENGTH_SHORT);
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






                }
            }
        });



//
        AppDrawer ad=new AppDrawer();
        ad.navitemClick(navView,this);



    }


    Boolean buttonPress(String cid,String name,String address,String contact){
        Log.d("t", "123");
        if(cid.length()==0){
            cidField.requestFocus();
            cidField.setError("Cid should not be empty");
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