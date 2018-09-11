package com.orbital.itenary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProgramAdd extends AppCompatActivity {

    // Widgets
    private EditText titleInput;
    private Spinner typeInput;
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

    // Widgets
    DatePickerDialog picker;
    TimePickerDialog tpicker;

    String[] typelist = {"Flight", "Hotel", "Transport", "Food", "Leisure", "Others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_add);

        // Initialise Widgets
        titleInput = findViewById(R.id.input_title);
        typeInput = findViewById(R.id.input_type);
        durationInput = findViewById(R.id.input_duration);
        notesInput = findViewById(R.id.input_notes);
        costInput = findViewById(R.id.input_cost);
        currencyInput = findViewById(R.id.input_currency);
        btn_send = findViewById(R.id.btn_add);

        // Allow multiple lines for notes
        notesInput.setSingleLine(false);

        // Get user id
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        // Home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReferenceFromUrl("https://itenary-dc075.firebaseio.com/");

        //Datepicker
        dateInput = findViewById(R.id.input_date);
        dateInput.setInputType(InputType.TYPE_NULL);
        dateInput.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                //date picker dialog
                picker = new DatePickerDialog(ProgramAdd.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateInput.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
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
                tpicker = new TimePickerDialog(ProgramAdd.this,
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
        stype.setPrompt("Select the type of activity");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typelist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stype.setAdapter(adapter);

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
        String type = typeInput.getSelectedItem().toString();
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
        mDatabaseRef.child("trips").child(tripId).child("programs").child(programId).setValue(program);
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
