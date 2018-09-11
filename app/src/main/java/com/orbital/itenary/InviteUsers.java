package com.orbital.itenary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InviteUsers extends AppCompatActivity {

    // Firebase Database
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;

    // Firebase user
    private FirebaseUser auth;

    // Data
    private ProgramClass program;
    private String tripTitle;
    private String tripId;

    // Widgets
    private CheckBox cbView;
    private CheckBox cbEdit;
    private Button btnSubmit;
    private EditText edtInviteEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_users);

        // Initialise widgets
        cbView = findViewById(R.id.checkBox_read);
        cbEdit = findViewById(R.id.checkBox_write);
        btnSubmit = findViewById(R.id.btn_invite_users);
        edtInviteEmail = findViewById(R.id.input_invite_email);

        // Home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Initialise firebase
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReferenceFromUrl("https://itenary-dc075.firebaseio.com/");

        Bundle b = this.getIntent().getExtras();
        if (b != null){
            program = b.getParcelable("progDisplayToInviteUsers");
            tripTitle = program.getTripTitle();
            tripId = program.getTripId();
        } else {
            new NullPointerException();
        }

        // Submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    // Retrieve the uid
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String uid;
                        for (final DataSnapshot dataSnap : dataSnapshot.getChildren()){
                            // Retrieve the uid
                            if (edtInviteEmail.getText().toString().equals(dataSnap.child("email").getValue().toString())){
                                uid = dataSnap.child("uid").getValue().toString();
                                    if (cbView.isChecked() && cbEdit.isChecked()) {
                                        mDatabaseRef.child("trips").child(tripId).child("users").child(uid).child("edit").setValue("true");
                                        mDatabaseRef.child("trips").child(tripId).child("users").child(uid).child("view").setValue("true");
                                    } else if (cbEdit.isChecked()) {
                                        mDatabaseRef.child("trips").child(tripId).child("users").child(uid).child("edit").setValue("true");
                                        mDatabaseRef.child("trips").child(tripId).child("users").child(uid).child("view").setValue("false");
                                    } else if (cbView.isChecked()) {
                                        mDatabaseRef.child("trips").child(tripId).child("users").child(uid).child("edit").setValue("false");
                                        mDatabaseRef.child("trips").child(tripId).child("users").child(uid).child("view").setValue("true");
                                    } else {
                                        backToProgramDisplay();
                                    }
                                    backToProgramDisplay();
                                    return;

                            }
                        }
                        // If no match for uid (user is not registered)
                        uid = "invalid user";
                        Toast.makeText(InviteUsers.this,"User not registered",Toast.LENGTH_SHORT).show();
                        if (cbView.isChecked() && cbEdit.isChecked()){
                            mDatabaseRef.child("trips").child(tripId).child("users").child(uid).child("edit").setValue("true");
                            mDatabaseRef.child("trips").child(tripId).child("users").child(uid).child("view").setValue("true");
                        } else if (cbEdit.isChecked()){
                            mDatabaseRef.child("trips").child(tripId).child("users").child(uid).child("edit").setValue("true");
                            mDatabaseRef.child("trips").child(tripId).child("users").child(uid).child("view").setValue("false");
                        } else if (cbView.isChecked()){
                            mDatabaseRef.child("trips").child(tripId).child("users").child(uid).child("edit").setValue("false");
                            mDatabaseRef.child("trips").child(tripId).child("users").child(uid).child("view").setValue("true");
                        } else{
                            backToProgramDisplay();
                        }
                        backToProgramDisplay();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        backToProgramDisplay();
        return true;
    }

    private void backToProgramDisplay() {
        Intent intent = new Intent(InviteUsers.this, ProgramDisplay.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("progInviteUsersToProgDisplay", program);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
