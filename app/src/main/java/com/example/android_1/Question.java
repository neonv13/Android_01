package com.example.android_1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Question{
    private int questionId;
    private boolean trueAnswer;

    public Question(int questionID, boolean trueAnswer){
        this.questionId = questionID;
        this.trueAnswer = trueAnswer;
    }
}