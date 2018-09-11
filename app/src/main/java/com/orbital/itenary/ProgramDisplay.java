package com.orbital.itenary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
    // Data
    private String tripId; //shared within the same programDisplay
    private String tripTitle; //shared within the same programDisplay
    private ProgramClass program;
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

        // Get Note from bundle
        Bundle b = this.getIntent().getExtras();
        if (b != null){
            if (b.getParcelable("tripToProgDisplay") != null) {
                program = b.getParcelable("tripToProgDisplay");
            } else if (b.getParcelable("progDisplayToProgEdit") != null) {
                program = b.getParcelable("progDisplayToProgEdit");
            } else if (b.getParcelable("progEditToProgDisplay") != null){
                program = b.getParcelable("progEditToProgDisplay");
            } else if (b.getParcelable("progAddToProgDisplay")!=null){
                program = b.getParcelable("progAddToProgDisplay");
            } else if (b.getParcelable("progDisplayToProgAdd") != null) {
                program = b.getParcelable("progDisplayToProgAdd");
            } else if(b.getParcelable("progInviteUsersToProgDisplay") != null){
                program = b.getParcelable("progInviteUsersToProgDisplay");
            } else {
                new NullPointerException();
            }
            tripId = program.getTripId();
            tripTitle = program.getTripTitle();
        } else {
            new NullPointerException();
        }

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
        mRvProg = findViewById(R.id.recycleViewProg);
        mRvProg.setHasFixedSize(true);

        progList = new ArrayList<>();
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
        mDatabaseRef.child("trips").child(tripId).child("programs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Result will be held Here
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    ProgramClass prog = new ProgramClass();
                    String progId = String.valueOf(dataSnap.child("programId").getValue());
                    String progType = String.valueOf(dataSnap.child("typeOfActivity").getValue());
                    String progTitle = String.valueOf(dataSnap.child("titleOfActivity").getValue());
                    String progDate = String.valueOf(dataSnap.child("dateOfActivity").getValue());
                    String progTime = String.valueOf(dataSnap.child("timeOfActivity").getValue());
                    String progDuration = String.valueOf(dataSnap.child("durationOfActivity").getValue());
                    String progNote = String.valueOf(dataSnap.child("noteOfActivity").getValue());
                    String progCost = String.valueOf(dataSnap.child("costOfActivity").getValue());
                    String progCurrency = String.valueOf(dataSnap.child("currencyOfActivity").getValue());
                    prog.setProgramId(progId);
                    prog.setTripTitle(tripTitle);
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
        Bundle bundle = new Bundle();
        bundle.putParcelable("progDisplayToProgAdd", program);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // Back button
    private void backToTripDisplay() {
        Intent intent = new Intent(ProgramDisplay.this, TripDisplay.class);
        startActivity(intent);
        finish();
    }

    // Back button
    @Override
    public boolean onSupportNavigateUp() {
        backToTripDisplay();
        return true;
    }
    //menu for logout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_programdisplay, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_invite_users:
                inviteUsers();
                return true;
            case R.id.menu_delete_trip_yes:
                deleteTrip();
                return true;
            case R.id.menu_delete_trip_no:
                doNotDeleteTrip();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Delete trip and go back to Trip Display
    private void deleteTrip() {
        mDatabaseRef.child("trips").child(tripId).removeValue();
        Intent intent = new Intent(ProgramDisplay.this, TripDisplay.class);
        startActivity(intent);
        finish();
    }

    // Prevent users from accidentally deleting a trip
    private void doNotDeleteTrip() {
        Toast.makeText(this,"Crisis Avoided",Toast.LENGTH_SHORT).show();
    }

    // Invite users to this trip
    private void inviteUsers() {
        Intent intent = new Intent(ProgramDisplay.this, InviteUsers.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("progDisplayToInviteUsers", program);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}
