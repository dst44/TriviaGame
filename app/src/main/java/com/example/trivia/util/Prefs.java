package com.example.trivia.util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void saveHighScore(int score) {
        int val = preferences.getInt("hoho", 0);
        int max = Math.max(val, score);
        preferences.edit().putInt("hoho", max).apply();
    }

    public int getHighScore(){
        return preferences.getInt("hoho",0);
    }

    public void setState(int i){
        preferences.edit().putInt("state",i).apply();
    }

    public int getState(){
        return preferences.getInt("state",0);
    }

}