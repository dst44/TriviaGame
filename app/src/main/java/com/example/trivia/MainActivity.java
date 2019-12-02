package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.controller.AppController;
import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;
import com.example.trivia.util.Prefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questionTextView;
    private TextView counterTextView;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private Button trueButton;
    private Button falseButton;
    private TextView score;
    private TextView topScore;

    private final String MESSAGE_ID = "get score";
    private int counter = 0;
    private int i=0;
    private int n =1;
    private List<Question> questionList;
    private Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = new Prefs(this);
        instantiate();
        String s3 = "Your Top Score: "+prefs.getHighScore()+"/"+n;
        topScore.setText(s3);


//        SharedPreferences sharedPreferences = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.put()

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                n=questionList.size();
                //Log.d("ababa", "processFinished: "+questionArrayList.get(0).getQuest() + " n= "+n);

                //questionTextView.setText(questionArrayList.get(i).getQuest());

                String s = "start!";
                counterTextView.setText(s);
            }
        });
    }

    //
    private void instantiate(){

        questionTextView = findViewById(R.id.question_textview);
        counterTextView = findViewById(R.id.counter_text);
        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        score = findViewById(R.id.score);
        topScore = findViewById(R.id.top_score);

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);

        i = prefs.getState();

    }
//
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.prev_button:
                if(i<=0){
                    i=n-1;
                    setAll();
                    break;
                }
                i = (i-1)%n;
                setAll();
                break;

            case R.id.next_button:

                setAll();
                i = (i+1)%n;
                break;

            case R.id.false_button:
                checkAnswer(false);
                setAll();
                break;

            case R.id.true_button:
                checkAnswer(true);
                setAll();
                break;

        }
    }

    @Override
    protected void onPause() {
        prefs.setState(i);
        super.onPause();
    }

    //
    void setAll(){
        String q = questionList.get(i).getQuest();
        questionTextView.setText(q);
        String s = Integer.valueOf(i+1)+"/"+n;
        counterTextView.setText(s);
        String s2 = "Current Score: "+counter+"/"+n;
        score.setText(s2);
        String s3 = "Your Top Score: "+prefs.getHighScore()+"/"+n;
        topScore.setText(s3);
    }

    private void checkAnswer(boolean chosen){

        if(chosen == questionList.get(i).getAnswer()){
            fadeView();
            Toast.makeText(this, "Correct Answer!",Toast.LENGTH_SHORT).show();
            counter++;
            prefs.saveHighScore(counter);
        }
        else {
            shakeAnimation();
            Toast.makeText(this, "Wrong Answer!", Toast.LENGTH_SHORT).show();
            if(counter>0){
                counter--;
            }
        }
    }

    private void shakeAnimation(){
        Animation shake = AnimationUtils.loadAnimation(this,R.anim.shake_animation);
        final CardView cardView = findViewById(R.id.card);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void fadeView(){
        final CardView cardView = findViewById(R.id.card);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);

        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}
