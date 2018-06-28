package com.orbital.itenary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnterTitleOfTrip extends AppCompatActivity {

    private EditText edtTitleTrip;
    private Button btnTitleTrip;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private String newTripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_title_of_trip);

        edtTitleTrip = findViewById(R.id.input_trip_title);
        btnTitleTrip = findViewById(R.id.btn_title_trip);
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReferenceFromUrl("https://itenary-dc075.firebaseio.com/");
        newTripId = mDatabaseRef.push().getKey();

        btnTitleTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRef.child(newTripId).child("Title").setValue(edtTitleTrip.getText().toString());
                Intent intent = new Intent(EnterTitleOfTrip.this, newProgramAdd.class);
                intent.putExtra("tripId",newTripId);
                startActivity(intent);
            }
        });

    }
}
