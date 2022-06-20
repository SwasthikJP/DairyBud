package com.example.dairymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class getTransactions extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_transactions);

        NavigationView navView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        appdrawerListener aL=new appdrawerListener();
        actionBarDrawerToggle= aL.adddrawerOpener(actionBarDrawerToggle,drawerLayout,this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AppDrawer ad=new AppDrawer();
        ad.navitemClick(navView,this);


        init();
    }


    public void init(){

        TableLayout tableLayout=findViewById(R.id.table);



        FirebaseFirestore db=FirebaseFirestore.getInstance();

        db.collection("transaction")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TableRow row= new TableRow(getTransactions.this);
                                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,100);

                                TextView userId = new TextView(getTransactions.this);
                                userId.setPadding(100,100,100,100);
                                userId.setTypeface(Typeface.DEFAULT_BOLD);
                                userId.setTextColor(Color.parseColor("#0F2851"));
                                userId.setTextSize(16);
//
                                TextView milkType = new TextView(getTransactions.this);
                                milkType.setPadding(100,100,100,100);
                                milkType.setTypeface(Typeface.DEFAULT_BOLD);
                                milkType.setTextColor(Color.parseColor("#0F2851"));
                                milkType.setTextSize(16);
//
                                TextView quantity = new TextView(getTransactions.this);
                                quantity.setPadding(100,100,100,100);
                                quantity.setTypeface(Typeface.DEFAULT_BOLD);
                                quantity.setTextColor(Color.parseColor("#0F2851"));
                                quantity.setTextSize(16);

                                TextView date = new TextView(getTransactions.this);
                                date.setPadding(100,100,100,100);
                                date.setTypeface(Typeface.DEFAULT_BOLD);
                                date.setTextColor(Color.parseColor("#0F2851"));
                                date.setTextSize(16);
//
                                TextView rate = new TextView(getTransactions.this);
                                rate.setPadding(100,100,100,100);
                                rate.setTypeface(Typeface.DEFAULT_BOLD);
                                rate.setTextColor(Color.parseColor("#0F2851"));
                                rate.setTextSize(16);

                                TextView amount = new TextView(getTransactions.this);
                                amount.setPadding(100,100,100,100);
                                amount.setTypeface(Typeface.DEFAULT_BOLD);
                                amount.setTextColor(Color.parseColor("#0F2851"));
                                amount.setTextSize(16);
//
                                TextView type = new TextView(getTransactions.this);
                                type.setPadding(100,100,100,100);
                                type.setTypeface(Typeface.DEFAULT_BOLD);
                                type.setTextColor(Color.parseColor("#0F2851"));
                                type.setTextSize(16);

//

                                row.setLayoutParams(lp);
//
                                userId.setText(document.getData().get("userID").toString());
                                row.addView(userId);
//
                                type.setText(document.getData().get("type").toString());
                                  row.addView(type);
//
                                Timestamp timestamp=(Timestamp) document.getData().get("created");
                                DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
                                Date ddate=timestamp.toDate();
                                date.setText(dateFormat.format(ddate));
                                row.addView(date);
//
                                milkType.setText(document.getData().get("milktype").toString());
                                row.addView(milkType);

                                quantity.setText(document.getData().get("quantity").toString());
                                row.addView(quantity);



                                rate.setText(document.getData().get("rate").toString());
                                row.addView(rate);

                                amount.setText(document.getData().get("amount").toString());
                                row.addView(amount);

                                Button update=new Button(getTransactions.this);

                                Button delete=new Button(getTransactions.this);

                                update.setText("Update");
                                delete.setText("Delete");

                                update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {




                                        Intent intent = new Intent(getTransactions.this, updateTransaction.class);
                                        intent.putExtra("docID",document.getId());

                                        getTransactions.this.startActivity(intent);
                                    }
                                });

                                delete.setOnClickListener(fun(db,document,row,tableLayout,milkType.getText().toString(),quantity.getText().toString(),type.getText().toString()));

                                row.addView(update);
                                row.addView(delete);



                                tableLayout.addView(row);

                            }
                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast toast= Toast.makeText(getTransactions.this,"errorrrr",Toast.LENGTH_SHORT);
                            toast.setMargin(50,50);
                            toast.show();
                        }
                    }
                });






    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    View.OnClickListener fun(FirebaseFirestore db, QueryDocumentSnapshot document, TableRow row,
                             TableLayout tableLayout,String milkType,String quantity,String type){

        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                alertDialog(db,document,row,tableLayout,milkType,quantity,type);
            }
        };

    }

    void alertDialog(FirebaseFirestore db, QueryDocumentSnapshot document2,TableRow row,
                     TableLayout tableLayout,String milkType,String quantity,String type) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure?");
//        dialog.setTitle("Dialog Box");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
///////


                        DocumentReference documentReference = db.collection("users").document("a2NvkEuPdMi7K0g6uPcI");

                        documentReference.get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                                    @Override

                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {

                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {

                                                if (milkType.equals("Cow")) {
                                                    float maxcow = Float.parseFloat(document.getData().get("maxcow").toString());
                                                    float curcow = Float.parseFloat(document.getData().get("curcow").toString());
                                                    float temp = Float.parseFloat(quantity);
                                                    float temp2;
                                                    if(type.equals("buy")){
                                                        temp2=curcow-temp;
                                                    }else{
                                                        temp2=curcow+temp;
                                                    }
                                                    if (maxcow > (temp2)) {
                                                        curcow =temp2;
                                                        Map<String, Object> tempdata = new HashMap<>();
                                                        tempdata.put("curcow", String.valueOf(curcow));

                                                        documentReference.update(tempdata)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Toast toast = Toast.makeText(getApplicationContext(), "curcow updated", Toast.LENGTH_SHORT);
                                                                        toast.setMargin(50, 50);
                                                                        toast.show();
                                                                        deleteDoc(db,document2, row,tableLayout);
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


//                                                  throw new ArithmeticException("error");
                                                    }
                                                } else {
                                                    float maxbuffalo = Float.parseFloat(document.getData().get("maxbuffalo").toString());
                                                    float curbuffalo = Float.parseFloat(document.getData().get("curbuffalo").toString());
                                                    float temp = Float.parseFloat(quantity);
                                                    float temp2;
                                                    if(type=="buy"){
                                                        temp2=curbuffalo-temp;
                                                    }else{
                                                        temp2=curbuffalo+temp;
                                                    }
                                                    if (maxbuffalo > (temp2)) {
                                                        curbuffalo =temp2;
                                                        Map<String, Object> tempdata = new HashMap<>();
                                                        tempdata.put("curbuffalo", String.valueOf(curbuffalo));

                                                        documentReference.update(tempdata)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Toast toast = Toast.makeText(getApplicationContext(), "curbuffalo updated", Toast.LENGTH_SHORT);
                                                                        toast.setMargin(50, 50);
                                                                        toast.show();
                                                                       deleteDoc(db,document2,row,tableLayout);
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

//                                                  throw new ArithmeticException("error");
                                                    }
                                                }

                                            } else {
                                                Toast toast = Toast.makeText(getTransactions.this, "no such document", Toast.LENGTH_SHORT);
                                                toast.setMargin(50, 50);
                                                toast.show();
                                            }
                                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                                            Toast toast = Toast.makeText(getTransactions.this, "errorrrr", Toast.LENGTH_SHORT);
                                            toast.setMargin(50, 50);
                                            toast.show();
                                        }

                                    }


                                });

                        //////






                        Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();

                    }
                });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();

            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();

    }

    void deleteDoc(FirebaseFirestore db,QueryDocumentSnapshot document,TableRow row,
                   TableLayout tableLayout){
        db.collection("transaction").document(document.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast toast = Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT);
                        toast.setMargin(50, 50);
                        toast.show();
                        tableLayout.removeView(row);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT);
                        toast.setMargin(50, 50);
                        toast.show();
                    }
                });
    }
}