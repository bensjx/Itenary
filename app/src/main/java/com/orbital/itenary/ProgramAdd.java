package com.orbital.itenary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProgramAdd extends AppCompatActivity {

    // Widgets
    private EditText titleInput;
    private EditText typeInput;
    private EditText dateInput;
    private EditText timeInput;
    private EditText durationInput;
    private EditText notesInput;
    private EditText costInput;
    private EditText currencyInput;
    private Button btn_send;

    // Data
    private String tripId;
    private String tripTitle;

    // Firebase
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;

    // Firebase User
    private FirebaseUser user;
    private String uid;

    // Bundles
    private Bundle b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_add);

        // Initialise Widgets
        titleInput = findViewById(R.id.input_title);
        typeInput = findViewById(R.id.input_type);
        dateInput = findViewById(R.id.input_date);
        timeInput = findViewById(R.id.input_time);
        durationInput = findViewById(R.id.input_duration);
        notesInput = findViewById(R.id.input_notes);
        costInput = findViewById(R.id.input_cost);
        currencyInput = findViewById(R.id.input_currency);
        btn_send = findViewById(R.id.btn_add);

        // Get user id
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        // Home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReferenceFromUrl("https://itenary-dc075.firebaseio.com/");

        // Get Note from bundle
        b = this.getIntent().getExtras();
        if (b != null){
            ProgramClass program = b.getParcelable("progDisplayToProgAdd");
            tripTitle = program.getTripTitle();
            tripId = program.getTripId();
        } else {
            new NullPointerException();
        }

        // Button to send data to database
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add program to Firebase
                addNewProgram();
            }
        });

    }

    private void addNewProgram() {
        String title = titleInput.getText().toString();
        String type = typeInput.getText().toString();
        String date = dateInput.getText().toString();
        String time = timeInput.getText().toString();
        String duration = durationInput.getText().toString();
        String note = notesInput.getText().toString();
        String cost = costInput.getText().toString();
        String currency = currencyInput.getText().toString();
        String programId = mDatabaseRef.push().getKey();
        // program object to store title and message
        ProgramClass program = new ProgramClass();
        program.setTitleOfActivity(title);
        program.setTypeOfActivity(type);
        program.setDateOfActivity(date);
        program.setTimeOfActivity(time);
        program.setDurationOfActivity(duration);
        program.setNoteOfActivity(note);
        program.setCostOfActivity(cost);
        program.setCurrencyOfActivity(currency);
        program.setTripTitle(tripTitle);
        program.setTripId(tripId);
        program.setProgramId(programId);
        // Pass it back to Program Display
        Intent intent = new Intent(ProgramAdd.this, ProgramDisplay.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("progAddToProgDisplay", program);
        intent.putExtras(bundle);
        mDatabaseRef.child(tripId).child("programs").child(programId).setValue(program);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        backToProgramDisplay();
        return true;
    }
    private void backToProgramDisplay() {
        Intent intent = new Intent(ProgramAdd.this, ProgramDisplay.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}
