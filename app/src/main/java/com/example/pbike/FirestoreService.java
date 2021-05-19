package com.example.pbike;

import android.util.Log;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import com.example.pbike.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Map;

class FirestorePath {
    static String users = "users";

    static String bikeNumbers = "bikeNumbers";
}

public class FirestoreService {

    FirebaseFirestore db;
    CollectionReference users;
    final String uid;



    FirestoreService(String uid) {
        this.uid = uid;
        db = FirebaseFirestore.getInstance();
        users = db.collection(FirestorePath.users);

    }

    void updateUser(Map<String, Object> data){
        users.document(uid).set(data, SetOptions.merge());
    }
    void addUser(User data, RelativeLayout root){
        users.document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Snackbar.make(root,"User add!",Snackbar.LENGTH_LONG).show();

            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(root,"User not add",Snackbar.LENGTH_LONG).show();
                Log.e("Error!", e.toString());
            }
        });

    }

     Task<DocumentSnapshot> getUser() {
        return users.document(uid).get();
    }
}
