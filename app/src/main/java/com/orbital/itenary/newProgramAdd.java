package com.orbital.itenary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Spinner;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class newProgramAdd extends AppCompatActivity {
    private EditText titleInput;
    private Spinner typeInput;
    private EditText dateInput;
    private EditText timeInput;
    private EditText durationInput;
    private EditText notesInput;
    private EditText costInput;
    private EditText currencyInput;
    private Button btn_send;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private FirebaseUser user;
    private String uid;
    private String existingTripId;
    private String existingTripTitle;

    EditText eText;
    DatePickerDialog picker;
    TimePickerDialog tpicker;

    String[] typelist = {"Flight", "Hotel", "Transport", "Food", "Leisure", "Others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_add);

        // Retrieve existingTripId;
        Intent intent = getIntent();
        if (intent != null){
            existingTripId = intent.getStringExtra("tripId");
            existingTripTitle = intent.getStringExtra("titleTrip");
        }

        // Initialise Widgets
        titleInput = findViewById(R.id.input_title);
        //typeInput = findViewById(R.id.input_type);
        //dateInput = findViewById(R.id.input_date);
        //timeInput = findViewById(R.id.input_time);
        durationInput = findViewById(R.id.input_duration);
        notesInput = findViewById(R.id.input_notes);
        costInput = findViewById(R.id.input_cost);
        currencyInput = findViewById(R.id.input_currency);
        btn_send = findViewById(R.id.btn_add);

        //Datepicker
        eText= findViewById(R.id.input_date);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                //date picker dialog
                picker = new DatePickerDialog(newProgramAdd.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        eText.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
                }
            },year,month,day);
            picker.show();
        }});

        //Timepicker
        timeInput = findViewById(R.id.input_time);
        timeInput.setInputType(InputType.TYPE_NULL);
        timeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cld = Calendar.getInstance();
                int hour = cld.get(Calendar.HOUR_OF_DAY);
                int minutes = cld.get(Calendar.MINUTE);
                //time picker dialog
                tpicker = new TimePickerDialog(newProgramAdd.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                if(sMinute<10) {
                                    timeInput.setText(sHour + ":0" + sMinute);
                                }else {
                                    timeInput.setText(sHour + ":" + sMinute);
                                }
                            }
                        }, hour, minutes, true);
                tpicker.show();
            }
        });

        //type selector (spinner)
        Spinner stype = findViewById(R.id.input_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typelist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stype.setAdapter(adapter);


        // Get user id
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        // Home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReferenceFromUrl("https://itenary-dc075.firebaseio.com/");

        // Button to send data to database
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add program to Firebase
                addNewProgram();
                // Return back to main page
                backToTripDisplay();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        backToTripDisplay();
        return true;
    }

    private void addNewProgram() {
        String title = titleInput.getText().toString();
        String type = typeInput.getSelectedItem().toString();
        String date = eText.getText().toString();
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
        program.setTripId(existingTripId);
        program.setProgramId(programId);
        program.setTripTitle(existingTripTitle);
        mDatabaseRef.child("trips").child(existingTripId).child("programs").child(programId).setValue(program);
    }

    private void backToTripDisplay() {
        Intent intent = new Intent(newProgramAdd.this, TripDisplay.class);
        startActivity(intent);
        finish();
    }
}
