package com.orbital.itenary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItineraryDisplay extends AppCompatActivity {

    private FloatingActionButton fabAddItinerary;
    private RecyclerView mRvProg;
    private ArrayList<ProgramClass> progList;
    private ProgramRvAdapter progRvAdapter;
    private FirebaseUser user;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_display);
        // Check if user is logged in
        // If user is not logged in, direct user to login page
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(ItineraryDisplay.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        // Get details of user
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        //Initialise fab
        fabAddItinerary = findViewById(R.id.fabAddNote);
        fabAddItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItinerary();
            }
        });

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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://itenary-dc075.firebaseio.com/");
        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
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
                    prog.setId(progId);
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

    private void addNewItinerary(){
        Intent intent = new Intent(ItineraryDisplay.this, ItineraryAdd.class);
        startActivity(intent);
    }

    //menu for logout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                userLogout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Signout User and return user to LoginActivity
    private void userLogout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ItineraryDisplay.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



}
