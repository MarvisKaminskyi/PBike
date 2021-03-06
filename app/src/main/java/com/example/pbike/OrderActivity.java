package com.example.pbike;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pbike.Models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OrderActivity extends AppCompatActivity {
    Button btnRent;
    Spinner time, bike;
    TextView rent_cost, rent_time, rent_bike, rent_price,parkSelected;
    FirebaseAuth auth;
    FirestoreService firestoreService;
    String selectedPark,selectedTime,selectedBike;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        //CostShow
        rent_cost = findViewById(R.id.rentCost);
        //btn rent
        btnRent = findViewById(R.id.btn_rent);
        //rent info

        auth = FirebaseAuth.getInstance();
        firestoreService = new FirestoreService(auth.getUid());


        //spinner for bike
        bike = (Spinner) findViewById(R.id.bike_number_field);
        ArrayAdapter<CharSequence> adapter_bike = ArrayAdapter.createFromResource(
                this, R.array.Bike_number, android.R.layout.simple_spinner_item);
        adapter_bike.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bike.setAdapter(adapter_bike);


        //spinner for time
        time = (Spinner) findViewById(R.id.time_number_field);
        ArrayAdapter<CharSequence> adapter_time = ArrayAdapter.createFromResource(
                this, R.array.time_number, android.R.layout.simple_spinner_item);
        adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(adapter_time);

        //handler time

        //initListeners
        initListeners();


        selectedPark = getIntent().getStringExtra("Selected");

    }



    void initListeners() {
        time.setOnItemSelectedListener(_timeSelected);

        //handler bike
        bike.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                costRentShow();
                selectedBike = parentView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        btnRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRentWindowAccept();
            }
        });
    }

    AdapterView.OnItemSelectedListener _timeSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            costRentShow();
            selectedTime = parentView.getSelectedItem().toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
        }

    };

    String costInfo;

    //handler rent price
    public void costRentShow() {

        float bikes_data = Float.parseFloat(bike.getSelectedItem().toString());
        float times_data = Float.parseFloat(time.getSelectedItem().toString());

        //fix. drop in dataBase!
        float price = 70;

        float cost;
        cost = times_data * price * bikes_data;
        costInfo = String.valueOf(cost);

        rent_cost.setText(costInfo + " UAH");


    }

    public void showRentWindowAccept() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View rent_accept = inflater.inflate(R.layout.rent_accept, null);
        dialog.setView(rent_accept);

        rent_time = rent_accept.findViewById(R.id.user_rentTime_info);
        rent_bike = rent_accept.findViewById(R.id.user_rentBike_info);
        rent_price = rent_accept.findViewById(R.id.user_orderPrice_info);
        parkSelected=rent_accept.findViewById(R.id.user_park_info);

        parkSelected.setText(selectedPark);
        rent_bike.setText(selectedBike);
        rent_time.setText(selectedTime);
        rent_price.setText(costInfo);
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String, Object> data = new HashMap<>();
                data.put("bikeNumbers", bike.getSelectedItem().toString());
                data.put("timeOrder", time.getSelectedItem().toString()+"h");
                data.put("costRent", Double.parseDouble(costInfo)+" UAH");
                data.put("park",selectedPark);

                firestoreService.updateUser(data);
                startActivity(new Intent(OrderActivity.this, MapActivity.class));
                finish();
            }

        });
        dialog.show();

    }

}