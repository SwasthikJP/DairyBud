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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

                                delete.setOnClickListener(fun(db,document,row,tableLayout));

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
                             TableLayout tableLayout){

        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                alertDialog(db,document,row,tableLayout);
            }
        };

    }

    void alertDialog(FirebaseFirestore db, QueryDocumentSnapshot document,TableRow row,
                     TableLayout tableLayout) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure?");
//        dialog.setTitle("Dialog Box");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {


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
}