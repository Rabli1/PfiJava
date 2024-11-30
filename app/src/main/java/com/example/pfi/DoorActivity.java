package com.example.pfi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DoorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);

        String selectedClass = getIntent().getStringExtra("selectedClass");

        Button buttonDoor1 = findViewById(R.id.buttonDoor1);
        Button buttonDoor2 = findViewById(R.id.buttonDoor2);

        buttonDoor1.setOnClickListener(v -> {
            Intent intent = new Intent(DoorActivity.this, CasinoActivity.class);
            intent.putExtra("selectedClass", selectedClass); // Transférer la classe choisie
            startActivity(intent);
        });

        buttonDoor2.setOnClickListener(v -> {
            Intent intent = new Intent(DoorActivity.this, Combat1Activity.class);
            intent.putExtra("selectedClass", selectedClass); // Transférer la classe choisie
            startActivity(intent);
        });

    }
}

