package com.guyi.class25b_and_1;

import android.widget.Toast;

import java.util.ArrayList;

public class GamaManager {


    private int currentCountryIndex = 0;
    private int score = 0;
    private int lives = 3;



    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    private void next() {
        currentCountryIndex++;
    }







}
