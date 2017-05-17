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
        challenge.shuffle();
        currentQuestion = 0;
        wrongCounter = 0;
    }

    public boolean hasNextQuestion()
    {
        int i = currentQuestion+1;
        if(i < challenge.getShuffledQuestionList().size()) {return true;}
        return false;
    }

    public void nextQuestion()
    {
        if(hasNextQuestion()) {currentQuestion++;}
    }

    public Question getCurrentQuestion()
    {
        if(currentQuestion < challenge.getShuffledQuestionList().size())
        {
            return challenge.getShuffledQuestionList().get(currentQuestion);
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
