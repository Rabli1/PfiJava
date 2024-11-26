package com.example.pfi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int currentExpression = 0;
    private final int[] avatarExpressions = {
            R.drawable.avatar_happy,
            R.drawable.avatar_sad,
            R.drawable.avatar_angry
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView avatarImageView = findViewById(R.id.avatarImageView);
        TextView playerNameTextView = findViewById(R.id.playerNameTextView);
        TextView playerHpTextView = findViewById(R.id.playerHpTextView);
        Button startButton = findViewById(R.id.startButton);

        String playerName = "Héros";
        int playerHP = 100;
        playerNameTextView.setText(playerName);
        playerHpTextView.setText("PV : " + playerHP);

        avatarImageView.setOnClickListener(v -> {
            currentExpression = (currentExpression + 1) % avatarExpressions.length;
            avatarImageView.setImageResource(avatarExpressions[currentExpression]);
        });

        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Combat1Activity.class);
            startActivity(intent);
        });
    }
}
