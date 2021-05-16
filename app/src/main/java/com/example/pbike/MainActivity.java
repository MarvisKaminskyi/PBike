package com.example.pbike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.pbike.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {

    Button btnLogin,btnSignIn;
    FirebaseAuth auth;
    FirestoreService firestoreService;

    RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        firestoreService = new FirestoreService(auth.getUid());

        auth.addAuthStateListener(firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user!=null){
                startActivity(new Intent(MainActivity.this, MapActivity.class));
                finish();

            }
        });

        btnLogin=findViewById(R.id.btn_login);
        btnSignIn=findViewById(R.id.btn_sign_in);

        root=findViewById(R.id.root_element);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterWindow();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginWindow();
            }
        });
    }
    private void showLoginWindow() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Authorization");
        dialog.setMessage("Input all authorization data");

        LayoutInflater inflater=LayoutInflater.from(this);
        View login_window=inflater.inflate(R.layout.login_window,null);
        dialog.setView(login_window);

        MaterialEditText email=login_window.findViewById(R.id.emailField);
        MaterialEditText password=login_window.findViewById(R.id.passField);

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root,"Input your email",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(password.getText().toString().length()<5){
                    Snackbar.make(root,"Input your password ",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(MainActivity.this, MapActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root,"Error Authorization. " +e.getMessage(),Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.show();
    }
    private void showRegisterWindow() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Registration");
        dialog.setMessage("Input all registration data");

        LayoutInflater inflater=LayoutInflater.from(this);
        View register_window=inflater.inflate(R.layout.register_window,null);
        dialog.setView(register_window);

        MaterialEditText email=register_window.findViewById(R.id.emailField);
        MaterialEditText password=register_window.findViewById(R.id.passField);
        MaterialEditText name=register_window.findViewById(R.id.nameField);
        MaterialEditText phone=register_window.findViewById(R.id.phoneField);


        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root,"Input your email",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name.getText().toString())){
                    Snackbar.make(root,"Input your name",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phone.getText().toString())){
                    Snackbar.make(root,"Input your phone",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(password.getText().toString().length()<5){
                    Snackbar.make(root,"Input your password > 5 ",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                //Registration user
                auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user=new User();
                                user.setEmail(email.getText().toString());
                                user.setPass(password.getText().toString());
                                user.setName(name.getText().toString());
                                user.setPhone(phone.getText().toString());
                                user.setBikeNumbers(0);
                                user.setTimeOrder(0);
                                user.setPark("");
                                user.setCostRent(0);

                                firestoreService.addUser(user,root);
                                startActivity(new Intent(MainActivity.this, MapActivity.class));
                                finish();

                            }
                        });
            }
        });
        dialog.show();
    }
}