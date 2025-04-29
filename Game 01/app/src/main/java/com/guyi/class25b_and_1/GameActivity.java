package com.guyi.class25b_and_1;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.guyi.class25b_and_1.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends AppCompatActivity {

    private ActivityGameBinding binding;

    private GamaManager gamaManager;
    private AppCompatImageView[] hearts;
    ImageView[] obstacles_lane_1;
    ImageView[] obstacles_lane_1b;
    ImageView[] obstacles_lane_2;
    ImageView[] obstacles_lane_2b;
    ImageView[] obstacles_lane_3;
    ImageView[] obstacles_lane_3b;
    ImageView[] clubs;
    int currentIndex_1 = 0;
    int currentIndex_1b = 7;
    int currentIndex_2 = 7;
    int currentIndex_2b = 7;
    int currentIndex_3 = 7;
    ;
    int currentIndex_3b = 7;
    int yn = 1;
    Random random = new Random();
    ImageView[] carLanes;
    int currentCarIndex = 1; // starts in the middle lane
    int lives = 3;
    private MediaPlayer mediaPlayer;


    private void makeObstaclesInvisible(){
        for (ImageView obs : obstacles_lane_1) {
            obs.setVisibility(View.INVISIBLE);
        }
        for (ImageView obs : obstacles_lane_2) {
            obs.setVisibility(View.INVISIBLE);
        }
        for (ImageView obs : obstacles_lane_3) {
            obs.setVisibility(View.INVISIBLE);
        }
        for (ImageView obs : clubs) {
            obs.setVisibility(View.INVISIBLE);
        }

    }

    private void generateObstacle(){
        int randomLane = random.nextInt(7);
        if(randomLane==1 && currentIndex_1 == 7){
            currentIndex_1 = 0;
        }
        if(randomLane==2 && currentIndex_1b == 7){
            currentIndex_1b = 0;
        }
        else if(randomLane==3 && currentIndex_2 == 7){
            currentIndex_2 = 0;
        }
        else if(randomLane==4 && currentIndex_2b == 7){
            currentIndex_2b = 0;
        }
        else if(randomLane==5 && currentIndex_3 == 7){
            currentIndex_3 = 0;
        }
        else if(currentIndex_3b == 7){
            currentIndex_3b = 0;
        }
    };
    private void updateObstaclePosition() {
        // Hide all
        makeObstaclesInvisible();

        // Show the next one
        if(currentIndex_1 < obstacles_lane_1.length+1){
            if(currentIndex_1 < 6){
                obstacles_lane_1[currentIndex_1].setVisibility(View.VISIBLE);
            }
            currentIndex_1++;
        }
        if(currentIndex_2 < obstacles_lane_2.length+1){
            if(currentIndex_2 < 6){
                obstacles_lane_2[currentIndex_2].setVisibility(View.VISIBLE);
            }
            currentIndex_2++;
        }
        if(currentIndex_3 < obstacles_lane_3.length+1){
            if(currentIndex_3 < 6){
                obstacles_lane_3[currentIndex_3].setVisibility(View.VISIBLE);
            }
            currentIndex_3++;
        }
        if(currentIndex_1b < obstacles_lane_1b.length+1){
            if(currentIndex_1b < 6){
                obstacles_lane_1b[currentIndex_1b].setVisibility(View.VISIBLE);
            }
            currentIndex_1b++;
        }
        if(currentIndex_2b < obstacles_lane_2b.length+1){
            if(currentIndex_2b < 6){
                obstacles_lane_2b[currentIndex_2b].setVisibility(View.VISIBLE);
            }
            currentIndex_2b++;
        }
        if(currentIndex_3b < obstacles_lane_3b.length+1){
            if(currentIndex_3b < 6){
                obstacles_lane_3b[currentIndex_3b].setVisibility(View.VISIBLE);
            }
            currentIndex_3b++;
        }

    }

    private void moveCarLeft() {
        if (currentCarIndex > 0) {
            carLanes[currentCarIndex].setVisibility(View.INVISIBLE);
            currentCarIndex--;
            carLanes[currentCarIndex].setVisibility(View.VISIBLE);
        }
    }
    private void moveCarRight() {
        if (currentCarIndex < carLanes.length - 1) {
            carLanes[currentCarIndex].setVisibility(View.INVISIBLE);
            currentCarIndex++;
            carLanes[currentCarIndex].setVisibility(View.VISIBLE);
        }
    }

    private void showCollisionEffects() {
        // Toast
        Toast.makeText(this, "CRASH!", Toast.LENGTH_SHORT).show();

        // Vibration
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(300);
        }

        // Sound (short beep)
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.collision_sound); // put a collision_sound.mp3 in res/raw
        mediaPlayer.start();

        if(currentCarIndex == 0){
            clubs[2].setVisibility(View.VISIBLE);
        }
        else if(currentCarIndex == 1){
            clubs[1].setVisibility(View.VISIBLE);
        }
        else if(currentCarIndex == 2){
            clubs[0].setVisibility(View.VISIBLE);
        }

        lives--;
        if (lives <= 0) {
            lives = 3; // Reset lives when they run out
            Toast.makeText(this, "Refilled lives!", Toast.LENGTH_SHORT).show();
        }
        updateHeartsUI();
    }

    private void updateHeartsUI() {
        for (int i = 0; i < hearts.length; i++) {
            if (i < lives) {
                hearts[i].setVisibility(View.VISIBLE);
            } else {
                hearts[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void checkCollision() {
        if(((currentIndex_1 == 6 || currentIndex_1b == 6) && currentCarIndex == 0)
             || ((currentIndex_2 == 6 || currentIndex_2b == 6) && currentCarIndex == 1)
             || ((currentIndex_3 == 6 || currentIndex_3b == 6) && currentCarIndex == 2))
        {
            showCollisionEffects();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // play music
        mediaPlayer = MediaPlayer.create(this, R.raw.game_music);
        mediaPlayer.start();

        // intialize imageviews
        obstacles_lane_1 = new ImageView[] {
                findViewById(R.id.obstacle_1),
                findViewById(R.id.obstacle_4),
                findViewById(R.id.obstacle_7),
                findViewById(R.id.obstacle_10),
                findViewById(R.id.obstacle_13),
                findViewById(R.id.obstacle_16)
        };
        obstacles_lane_1b = obstacles_lane_1;

        obstacles_lane_2 = new ImageView[] {
                findViewById(R.id.obstacle_2),
                findViewById(R.id.obstacle_5),
                findViewById(R.id.obstacle_8),
                findViewById(R.id.obstacle_11),
                findViewById(R.id.obstacle_14),
                findViewById(R.id.obstacle_17)
        };
        obstacles_lane_2b = obstacles_lane_2;

        obstacles_lane_3 = new ImageView[] {
                findViewById(R.id.obstacle_3),
                findViewById(R.id.obstacle_6),
                findViewById(R.id.obstacle_9),
                findViewById(R.id.obstacle_12),
                findViewById(R.id.obstacle_15),
                findViewById(R.id.obstacle_18)
        };
        obstacles_lane_3b = obstacles_lane_3;

        clubs = new ImageView[]{
                findViewById(R.id.club_1),
                findViewById(R.id.club_2),
                findViewById(R.id.club_3)

        };

        // Make all obstacles invisible initially
        makeObstaclesInvisible();

        // intialize car imageview
        carLanes = new ImageView[] {
                findViewById(R.id.avi_1),
                findViewById(R.id.avi_2),
                findViewById(R.id.avi_3),
        };

        // set car to start in the middle
        for (int i = 0; i < carLanes.length; i++) {
            carLanes[i].setVisibility(View.INVISIBLE);
        }
        carLanes[currentCarIndex].setVisibility(View.VISIBLE);

        // Hook up buttons
        findViewById(R.id.btn_left).setOnClickListener(v -> moveCarLeft());
        findViewById(R.id.btn_right).setOnClickListener(v -> moveCarRight());



        // Start timer
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    updateObstaclePosition();
                    checkCollision();
                    if(yn%2 == 0){
                        generateObstacle();
                    }
                    yn++;
                });
            }
        }, 0, 1000); // tick every 1 second

        // intialize hearts
        hearts = new AppCompatImageView[] {
                binding.imgHeart1,
                binding.imgHeart2,
                binding.imgHeart3,
        };

        gamaManager = new GamaManager();


    }

    // stop music
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}