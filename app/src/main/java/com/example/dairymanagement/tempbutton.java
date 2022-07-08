package com.example.dairymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class tempbutton extends AppCompatActivity {

    private LinearLayout linear;
    private Bitmap bitmap;
    public DrawerLayout drawerLayout;

TextView address,incharge,printdate,uid,type,milktype,quantity,rate,amount;
String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempbutton);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linear=findViewById(R.id.lineard2);
        Button btn=findViewById(R.id.buttonrrr);
        address=findViewById( R.id. address2);
        incharge=findViewById( R.id.incharge );
        printdate=findViewById( R.id.printdate );
        uid=findViewById( R.id.textView13 );
        type=findViewById( R.id.textView15 );
        milktype=findViewById( R.id.textView17 );
        quantity=findViewById( R.id.textView19 );
        rate=findViewById( R.id.textView20 );
        amount=findViewById( R.id.textView22 );

        String docID = getIntent().getStringExtra("docID");

        getData(docID);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = LoadBitmap(linear, linear.getWidth(), linear.getHeight());
                createPdf();
            }
        });

    }

    private Bitmap LoadBitmap(View v, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);

        return bitmap;
    }

    private void createPdf() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        float hight = displaymetrics.heightPixels;
//        float width = displaymetrics.widthPixels;
float hight=linear.getHeight();
float width=linear.getWidth();
        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // write the document content
        String targetPdf = fileName+".pdf";
        File filePath;
        File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

        filePath = new File(path,targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }////////////////////

        // close the document
        document.close();
        Toast.makeText(this, "successfully pdf created", Toast.LENGTH_SHORT).show();

//        openPdf();

    }

    private void openPdf() {
        File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

        File file = new File(path,fileName+".pdf");
        if (file.exists()) {

//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            Uri uri = Uri.fromFile(file);
//            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);


//            File file=new File(mFilePath);
            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);


        }else{
            Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }



    public  void getData(String docID){
        FirebaseFirestore db=FirebaseFirestore.getInstance();

        db.collection("transaction").document(docID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot document1= task.getResult();
                            if (document1.exists()) {

                                FirebaseAuth mAuth=FirebaseAuth.getInstance();
                                FirebaseUser firebaseUser=mAuth.getCurrentUser();
                              String userid=firebaseUser.getUid();

                                db.collection("users").document(userid)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {

                                                    DocumentSnapshot document= task.getResult();
                                                    if (document.exists()) {
//                                                        nameField=findViewById(R.id.nameField);
//                                                        addressField=findViewById(R.id.addressField);
//                                                        emailField=findViewById(R.id.emailField);

                                                        incharge.setText("Incharge: "+document.getData().get("name").toString());
                                                        address.setText("Address: "+document.getData().get("address").toString());
//                                                        emailField.setText(document.getData().get("email").toString());
//                                                        maxcowField.setText(document.getData().get("maxcow").toString());
//                                                        maxbuffaloField.setText(document.getData().get("maxbuffalo").toString());
//                                                        curcowField.setText(document.getData().get("curcow").toString());
//                                                        curbuffaloField.setText(document.getData().get("curbuffalo").toString());

                                                        ////
//                                                        idField=findViewById(R.id.spinner2);
                                                        uid.setText(document1.getData().get("userID").toString());

                                                        Timestamp timestamp=(Timestamp) document1.getData().get("created");
                                                        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
                                                        Date ddate=timestamp.toDate();
                                                        printdate.setText("Date: "+dateFormat.format(ddate));
                                                             fileName=document1.getData().get("userID").toString()+dateFormat.format(ddate);


                                                        String ttype=document1.getData().get("type").toString();
                                                        if(ttype.equals("buy")){
                                                            ttype="Sell";
                                                        }else{
                                                            ttype="Buy";
                                                        }
                                                        type.setText(ttype);
//                                                        spinner.setSelection(document1.getData().get("milktype").toString().equals("Cow")?0:1);
                                                        milktype.setText(document1.getData().get("milktype").toString());
                                                        quantity.setText(document1.getData().get("quantity").toString()+" litre");
                                                        rate.setText("Rs."+document1.getData().get("rate").toString());
                                                        amount.setText("Rs."+document1.getData().get("amount").toString());


                                                    } else {
                                                        Toast toast= Toast.makeText(tempbutton.this,"no such document",Toast.LENGTH_SHORT);
                                                        toast.setMargin(50,50);
                                                        toast.show();
                                                    }
                                                } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                                                    Toast toast= Toast.makeText(tempbutton.this,"errorrrr",Toast.LENGTH_SHORT);
                                                    toast.setMargin(50,50);
                                                    toast.show();
                                                }
                                            }
                                        });







                            } else {
                                Toast toast= Toast.makeText(tempbutton.this,"no such document",Toast.LENGTH_SHORT);
                                toast.setMargin(50,50);
                                toast.show();
                            }
                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast toast= Toast.makeText(tempbutton.this,"errorrrr",Toast.LENGTH_SHORT);
                            toast.setMargin(50,50);
                            toast.show();
                        }
                    }
                });
    }

}