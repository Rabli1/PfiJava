package com.example.pfi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int currentExpression = 0;
    private String selectedClass = "";
    private Button selectedButton;

    private final int[][] classExpressions = {
            {R.drawable.archer_neutre, R.drawable.archer_happy, R.drawable.archer_mad},
            {R.drawable.mage_neutre, R.drawable.mage_sad, R.drawable.mage_angry},
            {R.drawable.guerrier_neutre, R.drawable.guerrier_happy, R.drawable.guerrier_mad},
            {R.drawable.voleur_neutre, R.drawable.voleur_happy, R.drawable.voleur_mad}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView archerImageView = findViewById(R.id.archerImageView);
        ImageView mageImageView = findViewById(R.id.mageImageView);
        ImageView warriorImageView = findViewById(R.id.warriorImageView);
        ImageView thiefImageView = findViewById(R.id.thiefImageView);

        Button archerButton = findViewById(R.id.archerButton);
        Button mageButton = findViewById(R.id.mageButton);
        Button warriorButton = findViewById(R.id.warriorButton);
        Button thiefButton = findViewById(R.id.thiefButton);
        Button startButton = findViewById(R.id.startButton);

        archerImageView.setOnClickListener(v -> changeExpression(archerImageView, 0));
        mageImageView.setOnClickListener(v -> changeExpression(mageImageView, 1));
        warriorImageView.setOnClickListener(v -> changeExpression(warriorImageView, 2));
        thiefImageView.setOnClickListener(v -> changeExpression(thiefImageView, 3));

        archerButton.setOnClickListener(v -> selectClass("Archer", archerButton));
        mageButton.setOnClickListener(v -> selectClass("Mage", mageButton));
        warriorButton.setOnClickListener(v -> selectClass("Guerrier", warriorButton));
        thiefButton.setOnClickListener(v -> selectClass("Voleur", thiefButton));

        startButton.setOnClickListener(v -> {
            if (!selectedClass.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, DoorActivity.class);
                Bundle extras = new Bundle();
                extras.putString("selectedClass", selectedClass);
                extras.putInt("expression", currentExpression);
                intent.putExtras(extras); // Transférer la classe choisie
                startActivity(intent);
            } else {
                startButton.setError("Veuillez sélectionner une classe !");
            }
        });

    }

    private void changeExpression(ImageView imageView, int classIndex) {
        currentExpression = (currentExpression + 1) % classExpressions[classIndex].length;
        imageView.setImageResource(classExpressions[classIndex][currentExpression]);
    }

    private void selectClass(String className, Button button) {
        selectedClass = className;

        if (selectedButton != null) {
            selectedButton.setBackgroundResource(android.R.drawable.btn_default);
        }

        button.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        selectedButton = button;
    }
}
