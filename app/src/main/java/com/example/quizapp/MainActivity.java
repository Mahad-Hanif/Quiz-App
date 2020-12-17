package com.example.quizapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // TODO: Declare member variables here:
    TextView question;
    Button trueButton;
    Button falseButton;
    TextView score;
    ProgressBar progress;
    boolean [] correctAnswers = {true, true, true, true, true, false, true, false, true, true, false, false, true};
    int [] questions = {R.string.question_1, R.string.question_2, R.string.question_3, R.string.question_4, R.string.question_5, R.string.question_6, R.string.question_7, R.string.question_8, R.string.question_9, R.string.question_10, R.string.question_11, R.string.question_12, R.string.question_13};
    int index;
    int marks;
    Toast toastMessage;
    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / questions.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = findViewById(R.id.question_text_view);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        score = findViewById(R.id.score);
        progress = findViewById(R.id.progress_bar);

        if (savedInstanceState != null) {
            marks = savedInstanceState.getInt("ScoreKey");
            index = savedInstanceState.getInt("IndexKey");
        } else {
            marks = 0;
            index = 0;
        }

        question.setText(questions[index]);
        score.setText("Score " + marks + "/" + questions.length);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
    }

    private void checkAnswer(boolean bool) {
        Log.d("Check Index", String.valueOf(index));
        if (correctAnswers[index] == bool) {
            marks += 1;
            toastMessage = Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT);
            score.setText("Score " + marks + "/" + questions.length);
        }
        else
        {
            toastMessage = Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_LONG);
        }
        toastMessage.show();
        progress.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        index += 1;
        if (index == questions.length) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("You scored " + marks + " points!");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        } else {
            question.setText(questions[index]);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("ScoreKey", marks);
        outState.putInt("IndexKey", index);
    }
}
