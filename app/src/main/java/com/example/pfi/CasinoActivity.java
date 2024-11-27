package com.example.pfi;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class CasinoActivity extends AppCompatActivity {
    private final int[] slotImages = {
            R.drawable.archer_neutre,
            R.drawable.mage_neutre,
            R.drawable.guerrier_neutre,
            R.drawable.voleur_neutre
    };

    private ImageView slot1, slot2, slot3;
    private Random random;
    private Handler handler;
    private boolean isSpinning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casino);

        TextView selectedClassTextView = findViewById(R.id.selectedClassTextView);
        ImageView selectedClassAvatar = findViewById(R.id.selectedClassAvatar);
        Button spinButton = findViewById(R.id.spinButton);

        slot1 = findViewById(R.id.rouletteSlot1);
        slot2 = findViewById(R.id.rouletteSlot2);
        slot3 = findViewById(R.id.rouletteSlot3);

        random = new Random();
        handler = new Handler();

        String selectedClass = getIntent().getStringExtra("selectedClass");
        selectedClassTextView.setText("Classe sélectionnée : " + selectedClass);

        int avatarResource = getAvatarResource(selectedClass);
        selectedClassAvatar.setImageResource(avatarResource);

        spinButton.setOnClickListener(v -> {
            if (!isSpinning) {
                isSpinning = true;
                spinRoulette();
            }
        });
    }

    private int getAvatarResource(String selectedClass) {
        switch (selectedClass) {
            case "Archer":
                return R.drawable.archer_neutre;
            case "Mage":
                return R.drawable.mage_neutre;
            case "Guerrier":
                return R.drawable.guerrier_neutre;
            case "Voleur":
                return R.drawable.voleur_neutre;
            default:
                return R.drawable.archer_neutre; // Valeur par défaut
        }
    }

    private void spinRoulette() {
        final int[] spins = new int[3];
        final int duration = 2000; // Durée totale de la rotation (en ms)
        final int interval = 100; // Intervalle entre les changements (en ms)

        handler.postDelayed(new Runnable() {
            int elapsed = 0;

            @Override
            public void run() {
                if (elapsed < duration) {
                    spins[0] = random.nextInt(slotImages.length);
                    spins[1] = random.nextInt(slotImages.length);
                    spins[2] = random.nextInt(slotImages.length);

                    slot1.setImageResource(slotImages[spins[0]]);
                    slot2.setImageResource(slotImages[spins[1]]);
                    slot3.setImageResource(slotImages[spins[2]]);

                    elapsed += interval;
                    handler.postDelayed(this, interval);
                } else {
                    isSpinning = false;
                    checkWinningCondition(spins);
                }
            }
        }, interval);
    }

    private void checkWinningCondition(int[] spins) {
        if (spins[0] == spins[1] && spins[1] == spins[2]) {
            showResult("Bravo ! Vous avez gagné !");
        } else {
            showResult("Dommage ! Essayez encore !");
        }
    }

    private void showResult(String message) {
        TextView resultTextView = new TextView(this);
        resultTextView.setText(message);
        resultTextView.setTextSize(18);
        resultTextView.setPadding(16, 16, 16, 16);

        setContentView(resultTextView);
    }
}
