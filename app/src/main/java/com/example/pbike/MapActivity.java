package com.example.pbike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;




public class MapActivity extends AppCompatActivity {
    ImageButton btnBerth, btnVictory, btnYunost, btnYoungHeroes;
    TextView username, rentTime, bikeNumbers, parkSelected,rentCost;
    ImageView btnInfo;
    Button btnLogout;
    RelativeLayout rootMap;
    FirebaseAuth auth;
    FirestoreService firestoreService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //btnPark
        btnBerth = findViewById(R.id.btn_berth);
        btnVictory = findViewById(R.id.btn_victory);
        btnYunost = findViewById(R.id.btn_yunost);
        btnYoungHeroes = findViewById(R.id.btn_young_heroes);

        //btnProfile
        btnInfo = findViewById(R.id.user_profile);

        btnLogout=findViewById(R.id.btn_log_out);
        //Profile

        rootMap = findViewById(R.id.map_activity_root);

        auth = FirebaseAuth.getInstance();
        firestoreService = new FirestoreService(auth.getUid());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(MapActivity.this, MainActivity.class));
                finish();
            }
        });

        btnBerth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, OrderActivity.class);
                i.putExtra("Selected", "Prichal");
                startActivity(i);
                finish();
            }
        });
        btnYunost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, OrderActivity.class);
                i.putExtra("Selected", "Yunost");
                startActivity(i);
                finish();
            }
        });
        btnVictory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, OrderActivity.class);
                i.putExtra("Selected", "Victory");
                startActivity(i);
                finish();
            }
        });
        btnYoungHeroes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, OrderActivity.class);
                i.putExtra("Selected", "YoungHeroes");
                startActivity(i);
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

    private void userdata() {
        // Get a reference to your user


    }

    void updateFields(String name, String park, String time, String bike,String costRent) {
        username.setText(name);
        bikeNumbers.setText(bike);
        parkSelected.setText(park);
        rentTime.setText(time);
        rentCost.setText(costRent);
    }

    private void showInfoPage() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View login_window = inflater.inflate(R.layout.order_page, null);
        dialog.setView(login_window);

        username = login_window.findViewById(R.id.user_name_info);
        rentTime = login_window.findViewById(R.id.user_rentTime_info);
        bikeNumbers = login_window.findViewById(R.id.user_rentBike_info);
        parkSelected = login_window.findViewById(R.id.user_park_info);
        rentCost=login_window.findViewById(R.id.user_orderPrice_info);

        firestoreService.getUser().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> data = document.getData();
                        if (data == null) {
                            Log.d("TAG", "No data");
                            return;
                        }
                        Log.d("TAG", "DocumentSnapshot data: " + data);
                        String name = (String) data.get("name");
                        String park = (String) data.get("park");
                        String rentCost = data.get("costRent").toString();
                        String bike = data.get("bikeNumbers").toString();
                        String time = data.get("timeOrder").toString();
                        updateFields(name, park, time, bike, rentCost);

                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();

    }

}