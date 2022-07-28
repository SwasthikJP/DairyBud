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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class getproducers extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getproducers);




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

            db.collection("producer")
                    .orderBy("pid")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    TableRow row= new TableRow(getproducers.this);
                                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,100);
                                    TextView pid = new TextView(getproducers.this);
                                    pid.setPadding(100,100,100,100);
                                    pid.setTypeface(Typeface.DEFAULT_BOLD);
                                    pid.setTextColor(Color.parseColor("#0F2851"));
                                    pid.setTextSize(16);
                                    TextView name = new TextView(getproducers.this);
                                    name.setPadding(100,100,100,100);
                                    name.setTypeface(Typeface.DEFAULT_BOLD);
                                    name.setTextColor(Color.parseColor("#0F2851"));
                                    name.setTextSize(16);
                                    TextView address = new TextView(getproducers.this);
                                    address.setPadding(100,100,100,100);
                                    address.setTypeface(Typeface.DEFAULT_BOLD);
                                    address.setTextColor(Color.parseColor("#0F2851"));
                                    address.setTextSize(16);

                                    TextView date = new TextView(getproducers.this);
                                    date.setPadding(100,100,100,100);
                                    date.setTypeface(Typeface.DEFAULT_BOLD);
                                    date.setTextColor(Color.parseColor("#0F2851"));
                                    date.setTextSize(16);

                                    TextView contact = new TextView(getproducers.this);
                                    contact.setPadding(100,100,100,100);
                                    contact.setTypeface(Typeface.DEFAULT_BOLD);
                                    contact.setTextColor(Color.parseColor("#0F2851"));
                                    contact.setTextSize(16);
//


//

                                    row.setLayoutParams(lp);

                                    pid.setText(document.getData().get("pid").toString());
                                    row.addView(pid);

                                    name.setText(document.getData().get("name").toString());
                                    row.addView(name);

                                    address.setText(document.getData().get("address").toString());
                                    row.addView(address);

                                    Timestamp timestamp=(Timestamp) document.getData().get("created");
                                    DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
                                    Date ddate=timestamp.toDate();
                                    date.setText(dateFormat.format(ddate));
                                    row.addView(date);

                                    contact.setText(document.getData().get("contact").toString());
                                    row.addView(contact);

                                    Button update=new Button(getproducers.this);

                                    Button delete=new Button(getproducers.this);

                                    update.setText("Update");
                                    delete.setText("Delete");

                                    update.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {




                                            Intent intent = new Intent(getproducers.this, updateProducer.class);
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.putExtra("docID",document.getId());

                                            getproducers.this.startActivity(intent);
                                           finish();
                                        }
                                    });

                                    delete.setOnClickListener(fun(db,document,row,tableLayout));

                                    row.addView(update);
                                    row.addView(delete);



                                    tableLayout.addView(row);

                                }
                            } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                                Toast toast= Toast.makeText(getproducers.this,"errorrrr",Toast.LENGTH_SHORT);
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


                        db.collection("producer").document(document.getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT);
                                        toast.setMargin(50, 50);
                                        toast.show();
                                        tableLayout.removeView(row);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT);
                                        toast.setMargin(50, 50);
                                        toast.show();
                                    }
                                });





//                        Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();

                    }
                });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_LONG).show();

            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();

    }
}