package com.example.pbike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pbike.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Collection;

import static com.google.firebase.database.FirebaseDatabase.getInstance;


public class MapActivity extends AppCompatActivity {
    ImageButton btnBerth,btnVictory,btnYunost,btnYoungHeroes;
    TextView username,rentTime,bikeNumbers;
    ImageView btnInfo;
    RelativeLayout rootMap;
    FirebaseAuth auth;
    FirebaseFirestore db;
    CollectionReference users;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //btnPark
        btnBerth=findViewById(R.id.btn_berth);
        btnVictory=findViewById(R.id.btn_victory);
        btnYunost=findViewById(R.id.btn_yunost);
        btnYoungHeroes=findViewById(R.id.btn_young_heroes);

        //btnProfile
        btnInfo=findViewById(R.id.user_profile);

        //Profile
        username=findViewById(R.id.user_name_info);
        rentTime=findViewById(R.id.user_rentTime_info);
        bikeNumbers=findViewById(R.id.user_rentBike_info);

        rootMap=findViewById(R.id.map_activity_root);

        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        users=db.collection("users");

        btnBerth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(MapActivity.this, OrderActivity.class));
                finish();
            }
        });
        btnYunost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapActivity.this, OrderActivity.class));
                finish();
            }
        });
        btnVictory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapActivity.this, OrderActivity.class));
                finish();
            }
        });btnYoungHeroes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapActivity.this, OrderActivity.class));
                finish();
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoPage();
            }
        });

    }

    private void userdata(){
        // Get a reference to your user
       

    }

    private void showInfoPage() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        LayoutInflater inflater=LayoutInflater.from(this);
        View login_window=inflater.inflate(R.layout.order_page,null);
        dialog.setView(login_window);

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();

    }

    }