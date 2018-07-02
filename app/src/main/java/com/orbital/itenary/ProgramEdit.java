package com.orbital.itenary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProgramEdit extends AppCompatActivity {

    // Widgets
    private EditText titleInput;
    private EditText typeInput;
    private EditText dateInput;
    private EditText timeInput;
    private EditText durationInput;
    private EditText notesInput;
    private EditText costInput;
    private EditText currencyInput;

    private Button btn_edit;
    private Button btn_delete;

    // Data
    private String programId;
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
        setContentView(R.layout.activity_program_edit);

        // Initialise Widgets
        titleInput = findViewById(R.id.input_title);
        typeInput = findViewById(R.id.input_type);
        dateInput = findViewById(R.id.input_date);
        timeInput = findViewById(R.id.input_time);
        durationInput = findViewById(R.id.input_duration);
        notesInput = findViewById(R.id.input_notes);
        costInput = findViewById(R.id.input_cost);
        currencyInput = findViewById(R.id.input_currency);
        btn_edit = findViewById(R.id.btn_edit);
        btn_delete = findViewById(R.id.btn_delete);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReferenceFromUrl("https://itenary-dc075.firebaseio.com/");

        // Get user id
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        // Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get Note from bundle
        b = this.getIntent().getExtras();
        if (b != null){
            ProgramClass program = b.getParcelable("progDisplayToProgEdit");
            titleInput.setText(program.getTitleOfActivity());
            typeInput.setText(program.getTypeOfActivity());
            dateInput.setText(program.getDateOfActivity());
            timeInput.setText(program.getTimeOfActivity());
            durationInput.setText(program.getDurationOfActivity());
            notesInput.setText(program.getNoteOfActivity());
            costInput.setText(program.getCostOfActivity());
            currencyInput.setText(program.getCurrencyOfActivity());
            titleInput.setText(program.getTitleOfActivity());
            programId = program.getProgramId();
            tripId = program.getTripId();
            tripTitle = program.getTripTitle();
        } else {
            new NullPointerException();
        }

        // Delete button
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProgram();
                backToProgramDisplay();
            }
        });

        // Make Changes button
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProgram();
            }
        });

    }

    // Make Changes button method
    private void editProgram() {
        String title = titleInput.getText().toString();
        String type = typeInput.getText().toString();
        String date = dateInput.getText().toString();
        String time = timeInput.getText().toString();
        String duration = durationInput.getText().toString();
        String note = notesInput.getText().toString();
        String cost = costInput.getText().toString();
        String currency = currencyInput.getText().toString();
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
        program.setProgramId(programId);
        program.setTripId(tripId);
        program.setTripTitle(tripTitle);
        // Pass it back to Program Display
        Intent intent = new Intent(ProgramEdit.this, ProgramDisplay.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("progEditToProgDisplay", program);
        intent.putExtras(bundle);
        mDatabaseRef.child(tripId).child("programs").child(programId).setValue(program);
        startActivity(intent);
    }

    // Go back to display after button pressed
    private void backToProgramDisplay() {
        // Pass it back to Program Display
        Intent intent = new Intent(ProgramEdit.this, ProgramDisplay.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    // Back button
    @Override
    public boolean onSupportNavigateUp() {
        backToProgramDisplay();
        return true;
    }

    // Delete button method
    private void deleteProgram() {
        mDatabaseRef.child(tripId).child("programs").child(programId).removeValue();
    }
}
