package com.orbital.itenary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TripDisplay extends AppCompatActivity {

    // Widget
    private FloatingActionButton fabAddTrip;
    private TextView titleOfTrip;
    // Firebase
    private DatabaseReference mDatabaseRef;
    private FirebaseUser user;
    private String tripId;
    // Firebase user
    private String uid;
    // Recycler view
    private RecyclerView mRvTrip;
    private ArrayList<ProgramClass> tripList;
    private TripRvAdapter tripRvAdapter;

    FirebaseRecyclerAdapter<EnterTitleTripClass, TripViewHolder> tripRecyclerAdapter;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_display);
        // Check if user is logged in
        // If user is not logged in, direct user to login page
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(TripDisplay.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        recyclerview = findViewById(R.id.recycleViewTrip);
        recyclerview.setLayoutManager(new LinearLayoutManager(TripDisplay.this));
        tripRecyclerAdapter = new FirebaseRecyclerAdapter<EnterTitleTripClass, TripViewHolder>(EnterTitleTripClass.class,
                R.layout.card_view_trip, TripViewHolder.class, mDatabaseRef) {
            @Override
            protected void populateViewHolder(TripViewHolder viewHolder, EnterTitleTripClass model, int position) {
                viewHolder.txtView.setText(model.getTitleTrip());
            }
        };
        recyclerview.setAdapter(tripRecyclerAdapter);


        // Get details of user
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        //Initialise fab
        fabAddTrip = findViewById(R.id.fabAddNewTrip);
        fabAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTrip();
            }
        });

        // Initialise database
        mDatabaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://itenary-dc075.firebaseio.com/");


        // Part 2: CRUD display
        /*tripList = new ArrayList<>();
        mRvTrip = findViewById(R.id.recycleViewTrip);
        mRvTrip.setHasFixedSize(true);
        tripRvAdapter = new TripRvAdapter(this, tripList);
        mRvTrip.setAdapter(tripRvAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvTrip.setLayoutManager(linearLayoutManager);

        getFirebaseData(new ProgCallBack() {
            @Override
            public void onCallBack(ProgramClass prog) {
                tripList.add(prog);
                tripRvAdapter.notifyDataSetChanged();
            }
        });*/

    }

    /*private void getFirebaseData(final ProgCallBack progCallback) {
        tripId = mDatabaseRef.child(uid).push().getKey();
        mDatabaseRef.child(uid).child(tripId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Result will be holded Here
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    ProgramClass prog = new ProgramClass();
                    String progId = String.valueOf(dataSnap.child("id").getValue());
                    String progTitle = String.valueOf(dataSnap.child("titleOfActivity").getValue());
                    String progDate = String.valueOf(dataSnap.child("dateOfActivity").getValue());
                    prog.setProgramId(progId);
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
    }*/

    private void addNewTrip(){
        Intent intent = new Intent(TripDisplay.this, EnterTitleOfTrip.class);
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
        Intent intent = new Intent(TripDisplay.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView txtView;

        public TripViewHolder() {
            super(itemView);
            txtView = itemView.findViewById(R.id.tripTitle);
        }
    }
}
