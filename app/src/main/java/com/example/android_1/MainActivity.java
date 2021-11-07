package com.example.android_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String QUIZ_TAG = "MainActivity";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER = "com.example.android_1.CorrectAnswer";
    private static final int REQUEST_CODE_PROMPT = 0;

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button helpButton;
    private TextView questionTextView;
    private int currentIndex = 0;
    private Boolean answerWasShown = false;

    private static Question[] questions = new Question[]{
            new Question(R.string.q_apple, true),
            new Question(R.string.q_iphone, true),
            new Question(R.string.q_android, false),
            new Question(R.string.q_property, false),
            new Question(R.string.q_google, true),

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG, "onCreate here");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
            Log.d(QUIZ_TAG, "Restore q here");

        }

        questionTextView = findViewById(R.id.question_text_view);
        setNewQuestion();
        trueButton  = findViewById(R.id.button_true);
        trueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswerCorrectness( true);
            }
        });

        falseButton = findViewById(R.id.button_false);
        falseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswerCorrectness( false);
            }
        });

        nextButton = findViewById(R.id.button_next);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % questions.length;
                answerWasShown = false;
                setNewQuestion();
            }
        });
        helpButton = findViewById(R.id.button_help);
        helpButton.setOnClickListener((v -> {
            Intent intent = new Intent(this, PromptActivity.class);
            boolean correctAnswer = questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER,correctAnswer);
            startActivityForResult(intent, REQUEST_CODE_PROMPT);
//            startActivity(intent);
        }));
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(QUIZ_TAG, "onStart here");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(QUIZ_TAG, "onResume here");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(QUIZ_TAG, "onPause here");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(QUIZ_TAG, "onStop here");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(QUIZ_TAG, "onDestroy here");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG, "onSaveInstanceState here");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){ return;}
        if(requestCode == REQUEST_CODE_PROMPT){
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }



    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(answerWasShown){
            resultMessageId = R.string.answer_was_shown;
        }
        else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNewQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

}