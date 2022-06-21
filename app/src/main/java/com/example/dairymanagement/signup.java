package com.example.dairymanagement;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class signup extends AppCompatActivity {
    TextInputLayout Name, Address, Email, PhoneNo, Password, Conpassword, Cmc, Bmc;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Button b6, b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        //Hooks to all xml elements in activity_sign_up.xml
        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        PhoneNo = findViewById(R.id.phoneno);
        Password = findViewById(R.id.password);
        Conpassword = findViewById(R.id.conpassword);
        Address = findViewById(R.id.address);
        Cmc = findViewById(R.id.cmc);
        Bmc = findViewById(R.id.bmc);
        b6 = findViewById(R.id.b6);
        b4 = findViewById(R.id.b4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);

            }
        });
    }
    private Boolean validateName() {
        String val = Name.getEditText().getText().toString();

        if (val.isEmpty()) {
            Name.setError("Field cannot be empty");
            return false;
        } else {
            Name.setError(null);
            Name.setErrorEnabled(false);
            return true;
        }
    }




    private Boolean validateAddress() {
        String val = Address.getEditText().getText().toString();
        if (val.isEmpty()) {
            Address.setError("Field cannot be empty");
            return false;
        } else {
            Address.setError(null);
            Address.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = Email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            Email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            Email.setError("Invalid email address");
            return false;
        } else {
            Email.setError(null);
            Email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = PhoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            PhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            PhoneNo.setError(null);
            PhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateCmc() {
        String val = Cmc.getEditText().getText().toString();

        if (val.isEmpty()) {
            Cmc.setError("enter 0 if none.");
            return false;
        } else {
            Cmc.setError(null);
            Cmc.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateBmc() {
        String val = Bmc.getEditText().getText().toString();

        if (val.isEmpty()) {
            Bmc.setError("enter 0 if none.");
            return false;
        } else {
            Bmc.setError(null);
            Bmc.setErrorEnabled(false);
            return true;
        }
    }


    private Boolean validatePassword() {
        String val = Password.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +
                //"(?=.*[a-z])" +
                //"(?=.*[A-Z])" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{4,}" +
                "$";

        if (val.isEmpty()) {
            Password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            Password.setError("Password is too weak");
            return false;
        } else {
            Password.setError(null);
            Password.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateConpassword() {
        String val = Conpassword.getEditText().getText().toString();


        if (val.isEmpty()) {
            Conpassword.setError("Field cannot be empty");
            return false;
        } else {
            Conpassword.setError(null);
            Conpassword.setErrorEnabled(false);
            return true;
        }
    }

    public void register(View view) {

        if (!validateName() | !validateAddress() | !validateConpassword() | !validateCmc() | !validateBmc() | !validatePassword() | !validatePhoneNo() | !validateEmail() ) {
            return;
        } else {
//                rootNode = FirebaseDatabase.getInstance();
//                reference = rootNode.getReference("users");
            String name = Name.getEditText().getText().toString();
            String email = Email.getEditText().getText().toString();
            String phoneNo = PhoneNo.getEditText().getText().toString();
            String address = Address.getEditText().getText().toString();
            String cmc = Cmc.getEditText().getText().toString();
            String bmc = Bmc.getEditText().getText().toString();
            String password = Password.getEditText().getText().toString();
            UserHelperClass helperClass = new UserHelperClass(name, email, phoneNo, password,"0", "0", address,cmc,bmc);
//            reference.child(phoneNo).setValue(helperClass);


             FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(signup.this, "signup successful",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();

                                ////
                                FirebaseFirestore db=FirebaseFirestore.getInstance();
                                db.collection("users").document(user.getUid())
                                        .set(helperClass)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
//                                                Toast.makeText(signup.this, "data added",
//                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(signup.this, MainActivity.class);

                                                signup.this.startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(signup.this, "error",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                ////
                            } else {

                                Toast.makeText(signup.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });




        }
    }
}