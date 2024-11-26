package com.example.pfi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class Combat1Activity extends AppCompatActivity {
    private int currentExpression = 0;
    private final int[] avatarExpressions = {
            R.drawable.avatar_happy,
            R.drawable.avatar_sad,
            R.drawable.avatar_angry
    };

    private int playerHP = 100;
    private int enemyHP = 100;
    private boolean isPlayerTurn = true;
    private final Random random = new Random();
    private MediaPlayer attackSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combat1_activity);

        // Initialize sound effects
        attackSound = MediaPlayer.create(this, R.raw.attack_sound);

        // Initialize views
        ImageView playerAvatarImageView = findViewById(R.id.playerAvatarImageView);
        ImageView enemyAvatarImageView = findViewById(R.id.enemyAvatarImageView);
        ProgressBar playerHealthBar = findViewById(R.id.playerHealthBar);
        ProgressBar enemyHealthBar = findViewById(R.id.enemyHealthBar);
        ImageView playerCursor = findViewById(R.id.playerCursor);
        ImageView enemyCursor = findViewById(R.id.enemyCursor);
        TextView playerActionText = findViewById(R.id.playerActionText);
        TextView enemyActionText = findViewById(R.id.enemyActionText);
        Button attackButton = findViewById(R.id.attackButton);
        Button defendButton = findViewById(R.id.defendButton);
        Button healButton = findViewById(R.id.healButton);

        highlightTurn(playerCursor, enemyCursor, isPlayerTurn);

        playerAvatarImageView.setOnClickListener(v -> {
            currentExpression = (currentExpression + 1) % avatarExpressions.length;
            playerAvatarImageView.setImageResource(avatarExpressions[currentExpression]);
        });

        attackButton.setOnClickListener(v -> {
            if (isPlayerTurn) {
                playAttackSound();
                enemyHP = Math.max(enemyHP - 15, 0);
                showAction(playerActionText, "Vous attaquez !");
                updateHealthBars(playerHealthBar, enemyHealthBar);
                if (checkGameOver(playerHealthBar, enemyHealthBar)) return;
                isPlayerTurn = false;
                delayEnemyTurn(playerCursor, enemyCursor, playerActionText, enemyActionText, playerHealthBar, enemyHealthBar);
            }
        });

        defendButton.setOnClickListener(v -> {
            if (isPlayerTurn) {
                showAction(playerActionText, "Vous défendez !");
                isPlayerTurn = false;
                delayEnemyTurn(playerCursor, enemyCursor, playerActionText, enemyActionText, playerHealthBar, enemyHealthBar);
            }
        });

        healButton.setOnClickListener(v -> {
            if (isPlayerTurn) {
                playerHP = Math.min(playerHP + 20, 100);
                showAction(playerActionText, "Vous soignez !");
                updateHealthBars(playerHealthBar, enemyHealthBar);
                if (checkGameOver(playerHealthBar, enemyHealthBar)) return;
                isPlayerTurn = false;
                delayEnemyTurn(playerCursor, enemyCursor, playerActionText, enemyActionText, playerHealthBar, enemyHealthBar);
            }
        });
    }

    private void playAttackSound() {
        if (attackSound != null) {
            attackSound.start();
        }
    }

    private void delayEnemyTurn(ImageView playerCursor, ImageView enemyCursor, TextView playerActionText, TextView enemyActionText, ProgressBar playerHealthBar, ProgressBar enemyHealthBar) {
        new Handler().postDelayed(() -> {
            highlightTurn(playerCursor, enemyCursor, isPlayerTurn);
            enemyTurn(enemyActionText, playerHealthBar, enemyHealthBar);
            if (checkGameOver(playerHealthBar, enemyHealthBar)) return;
            highlightTurn(playerCursor, enemyCursor, isPlayerTurn);
        }, 1000);
    }

    private void enemyTurn(TextView enemyActionText, ProgressBar playerHealthBar, ProgressBar enemyHealthBar) {
        if (enemyHP > 0) {
            int action = random.nextInt(2);
            if (action == 0) {
                playAttackSound();
                playerHP = Math.max(playerHP - 15, 0);
                showAction(enemyActionText, "L'ennemi attaque !");
            } else {
                enemyHP = Math.min(enemyHP + 10, 100);
                showAction(enemyActionText, "L'ennemi se soigne !");
            }
            updateHealthBars(playerHealthBar, enemyHealthBar);
        }
        isPlayerTurn = true;
    }

    private void updateHealthBars(ProgressBar playerHealthBar, ProgressBar enemyHealthBar) {
        playerHealthBar.setProgress(playerHP);
        enemyHealthBar.setProgress(enemyHP);
    }

    private void highlightTurn(ImageView playerCursor, ImageView enemyCursor, boolean isPlayerTurn) {
        if (isPlayerTurn) {
            playerCursor.setVisibility(View.VISIBLE);
            enemyCursor.setVisibility(View.INVISIBLE);
        } if(!isPlayerTurn) {
            playerCursor.setVisibility(View.INVISIBLE);
            enemyCursor.setVisibility(View.VISIBLE);
        }
    }

    private void showAction(TextView actionText, String message) {
        actionText.setText(message);
        actionText.setVisibility(TextView.VISIBLE);
        new Handler().postDelayed(() -> actionText.setVisibility(TextView.GONE), 1500);
    }

    private boolean checkGameOver(ProgressBar playerHealthBar, ProgressBar enemyHealthBar) {
        if (playerHP <= 0) {
            navigateToDefeat();
            return true;
        }
        if (enemyHP <= 0) {
            navigateToVictory();
            return true;
        }
        return false;
    }

    private void navigateToVictory() {
        Intent intent = new Intent(Combat1Activity.this, VictoireActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToDefeat() {
        Intent intent = new Intent(Combat1Activity.this, DefaiteActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (attackSound != null) {
            attackSound.release();
        }
    }
}
