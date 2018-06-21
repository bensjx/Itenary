package com.orbital.itenary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItineraryEdit extends AppCompatActivity {
    private EditText titleInput;
    private EditText typeInput;
    private EditText dateInput;
    private EditText timeInput;
    private EditText durationInput;
    private EditText notesInput;
    private EditText costInput;
    private EditText currencyInput;
    private String programId;
    private Button btn_edit;
    private Button btn_delete;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_edit);

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

        // Initialise database with offline capability
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReferenceFromUrl("https://itenary-dc075.firebaseio.com/");
        //mDatabaseRef.keepSynced(true);

        // Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get Note from bundle
        Bundle b = this.getIntent().getExtras();
        if (b != null){
            ProgramClass program = b.getParcelable("prog");
            titleInput.setText(program.getTitleOfActivity());
            typeInput.setText(program.getTypeOfActivity());
            dateInput.setText(program.getDateOfActivity());
            timeInput.setText(program.getTimeOfActivity());
            durationInput.setText(program.getDurationOfActivity());
            notesInput.setText(program.getNoteOfActivity());
            costInput.setText(program.getCostOfActivity());
            currencyInput.setText(program.getCurrencyOfActivity());
            titleInput.setText(program.getTitleOfActivity());
            programId = program.getId();
        } else {
            new NullPointerException();
        }

        // Delete button
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProgram();
                backToItineraryDisplay();
            }
        });

        // Make Changes button
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProgram();
                backToItineraryDisplay();
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
        program.setId(programId);
        mDatabaseRef.child(programId).setValue(program);
    }

    // Go back to display after button pressed
    private void backToItineraryDisplay() {
        Intent intent = new Intent(ItineraryEdit.this, ItineraryDisplay.class);
        startActivity(intent);
        finish();
    }

    // Back button
    @Override
    public boolean onSupportNavigateUp() {
        backToItineraryDisplay();
        return true;
    }

    // Delete button method
    private void deleteProgram() {
        mDatabaseRef.child(programId).removeValue();
    }
}
