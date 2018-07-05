package com.orbital.itenary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnterTitleOfTrip extends AppCompatActivity {

    private EditText edtTitleTrip;
    private Button btnTitleTrip;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private String newTripId;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_title_of_trip);

        // Home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edtTitleTrip = findViewById(R.id.input_trip_title);
        btnTitleTrip = findViewById(R.id.btn_title_trip);
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReferenceFromUrl("https://itenary-dc075.firebaseio.com/");
        newTripId = mDatabaseRef.push().getKey();
        auth = FirebaseAuth.getInstance();

        btnTitleTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("trips").child(newTripId).child("Title").setValue(edtTitleTrip.getText().toString());
                mDatabaseRef.child("trips").child(newTripId).child("users").child(auth.getUid()).child("view").setValue("true");
                mDatabaseRef.child("trips").child(newTripId).child("users").child(auth.getUid()).child("edit").setValue("true");
                Intent intent = new Intent(EnterTitleOfTrip.this, newProgramAdd.class);
                intent.putExtra("titleTrip", edtTitleTrip.getText().toString());
                intent.putExtra("tripId",newTripId);
                startActivity(intent);
            }
        });
    }
    // Back button
    private void backToTripDisplay() {
        Intent intent = new Intent(EnterTitleOfTrip.this, TripDisplay.class);
        startActivity(intent);
        finish();
    }

    // Back button
    @Override
    public boolean onSupportNavigateUp() {
        backToTripDisplay();
        return true;
    }
}
