package com.orbital.itenary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProgramDisplay extends AppCompatActivity {

    // Widget
    private FloatingActionButton fabAddProgram;
    // Firebase
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private String tripId;
    // Firebase user
    private FirebaseUser user;
    private String uid;
    // Recycler view
    private RecyclerView mRvProg;
    private ArrayList<ProgramClass> progList;
    private ProgramRvAdapter progRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_display);

        // Get details of user
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        // Home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Initialise fab
        fabAddProgram = findViewById(R.id.fabAddProgram);
        fabAddProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewProgram();
            }
        });

        // Initialise database
        mDatabaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://itenary-dc075.firebaseio.com/");

        // Part 2: CRUD display
        progList = new ArrayList<>();
        mRvProg = findViewById(R.id.recycleViewProg);
        mRvProg.setHasFixedSize(true);
        progRvAdapter = new ProgramRvAdapter(this, progList);
        mRvProg.setAdapter(progRvAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvProg.setLayoutManager(linearLayoutManager);

        getFirebaseData(new ProgCallBack() {
            @Override
            public void onCallBack(ProgramClass prog) {
                progList.add(prog);
                progRvAdapter.notifyDataSetChanged();
            }
        });

    }

    private void getFirebaseData(final ProgCallBack progCallback) {
        tripId = mDatabaseRef.child(uid).push().getKey();
        mDatabaseRef.child(uid).child(tripId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Result will be holded Here
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    ProgramClass prog = new ProgramClass();
                    String progId = String.valueOf(dataSnap.child("id").getValue());
                    String progType = String.valueOf(dataSnap.child("typeOfActivity").getValue());
                    String progTitle = String.valueOf(dataSnap.child("titleOfActivity").getValue());
                    String progDate = String.valueOf(dataSnap.child("dateOfActivity").getValue());
                    String progTime = String.valueOf(dataSnap.child("timeOfActivity").getValue());
                    String progDuration = String.valueOf(dataSnap.child("durationOfActivity").getValue());
                    String progNote = String.valueOf(dataSnap.child("noteOfActivity").getValue());
                    String progCost = String.valueOf(dataSnap.child("costOfActivity").getValue());
                    String progCurrency = String.valueOf(dataSnap.child("currencyOfActivity").getValue());
                    prog.setProgramId(progId);
                    prog.setTripId(tripId);
                    prog.setTypeOfActivity(progType);
                    prog.setTitleOfActivity(progTitle);
                    prog.setDateOfActivity(progDate);
                    prog.setTimeOfActivity(progTime);
                    prog.setDurationOfActivity(progDuration);
                    prog.setNoteOfActivity(progNote);
                    prog.setCostOfActivity(progCost);
                    prog.setCurrencyOfActivity(progCurrency);
                    progCallback.onCallBack(prog);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle db error
            }
        });
    }

    private void addNewProgram(){
        Intent intent = new Intent(ProgramDisplay.this, ProgramAdd.class);
        startActivity(intent);
    }

    // Back button
    private void backToMainPage() {
        Intent intent = new Intent(ProgramDisplay.this, TripDisplay.class);
        startActivity(intent);
        finish();
    }

    // Back button
    @Override
    public boolean onSupportNavigateUp() {
        backToMainPage();
        return true;
    }



}
