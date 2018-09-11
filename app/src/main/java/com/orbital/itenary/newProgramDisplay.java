package com.orbital.itenary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class newProgramDisplay extends AppCompatActivity {

    private FloatingActionButton fabAddNewProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_program_display);

        // Home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Initialise fab
        fabAddNewProgram = findViewById(R.id.fabAddNewProgram);
        fabAddNewProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewProgram();
            }
        });
    }

    private void addNewProgram(){
        Intent intent = new Intent(newProgramDisplay.this, newProgramAdd.class);
        startActivity(intent);
    }

    // Back button
    private void backToTripDisplay() {
        Intent intent = new Intent(newProgramDisplay.this, TripDisplay.class);
        startActivity(intent);
        finish();
    }

    // Back button
    @Override
    public boolean onSupportNavigateUp() {
        backToTripDisplay();
        return true;
    }



}
