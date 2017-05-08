package at.vocabdevelopment.studymanager;

import android.util.Log;

import java.io.Serializable;

public class Game implements Serializable {

    private Challenge challenge;
    private int currentQuestion;
    private int wrongCounter;

    public Game(Challenge challenge)
    {
        this.challenge = challenge;
        currentQuestion = 0;
        wrongCounter = 0;
    }

    public boolean hasNextQuestion()
    {
        int i = currentQuestion+1;
        if(i < challenge.getQuestionList().size()) {return true;}
        return false;
    }

    public void nextQuestion()
    {
        if(hasNextQuestion()) {currentQuestion++;}
    }

    public Question getCurrentQuestion()
    {
        if(currentQuestion < challenge.getQuestionList().size())
        {
            return challenge.getQuestionList().get(currentQuestion);
        }
        return null;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public int getWrongCounter() {
        return wrongCounter;
    }

    public void incrementWrongCounter() {
        this.wrongCounter++;
    }

}
