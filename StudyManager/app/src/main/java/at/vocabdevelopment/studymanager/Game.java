package at.vocabdevelopment.studymanager;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import android.util.JsonWriter;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game implements Serializable {

    public static final int EASY = 2;
    public static final int MEDIUM = 1;
    public static final int HARD = 0;

    private Challenge challenge;
    private ArrayList<Question> deck;
    private int difficulty;
    private int currentQuestionIndex;
    private int wrongCounter;
    private ArrayList<ArrayList<Question>> attemptsList;
    private int attempts;
    private int numberOfQuestions;

    public Game(Challenge challenge, int difficulty)
    {
        this.challenge = challenge;
        this.difficulty = difficulty;
        this.deck = challenge.getQuestionList();
        this.numberOfQuestions = deck.size();
        this.attemptsList = new ArrayList<>();
        Collections.shuffle(this.deck);
        this.currentQuestionIndex = 0;
        this.wrongCounter = 0;
        this.attempts = 0;
    }

    public boolean hasNextQuestion()
    {
        if (currentQuestionIndex < deck.size()-1) {
            currentQuestionIndex++;
            return true;
        }
        else if (attempts < difficulty && !attemptsList.isEmpty() && !attemptsList.get(attempts).isEmpty()) {
            deck.clear();
            deck = attemptsList.get(attempts);
            Collections.shuffle(deck);
            attempts++;
            currentQuestionIndex = 0;
            return true;
        }
        return false;
    }

    public void storeQuestion()
    {
        if (attempts == difficulty)
            wrongCounter++;

        if (attemptsList.isEmpty()) {
            ArrayList<Question> sideDeck = new ArrayList<Question>();
            sideDeck.add(deck.get(currentQuestionIndex));
            attemptsList.add(sideDeck);
        }
        else if (attemptsList.size() <= attempts) {
            ArrayList<Question> sideDeck = new ArrayList<Question>();
            sideDeck.add(deck.get(currentQuestionIndex));
            attemptsList.add(sideDeck);
        }
        else {
            attemptsList.get(attempts).add(deck.get(currentQuestionIndex));
        }
    }

    public Question getCurrentQuestionIndex()
    {
        if (currentQuestionIndex < deck.size()) {
            return deck.get(currentQuestionIndex);
        }
        return null;
    }


    public PieData generatePieData() {
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(wrongCounter, "wrong"));
        pieEntries.add(new PieEntry(numberOfQuestions - wrongCounter, "correct"));
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Your Result");
        PieData pieData = new PieData(pieDataSet);

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        return pieData;
    }

    public int deleteGameFile() {
        File gameFile = new File(StudyManager.getCurrentGameDir() + File.separator +
                "currentGame.json");
        if (gameFile.exists()) {
            gameFile.delete();
            return 0;
        }
        else {
            return -1;
        }
    }

    public int constructGameFile() {
        File gameFile = new File(StudyManager.getCurrentGameDir() + File.separator +
                "currentGame.json");

        try {
            JsonWriter gameWriter = new JsonWriter(new FileWriter(gameFile));
            gameWriter.beginObject();

            gameWriter.name("challenge");
            gameWriter.beginArray();
            gameWriter.beginObject();
            gameWriter.name("name");
            gameWriter.value(this.getChallenge().getName());
            gameWriter.endObject();
            gameWriter.endArray();

            gameWriter.name("questions");
            gameWriter.beginArray();

            for (Question question : this.getDeck()) {
                gameWriter.beginObject();
                gameWriter.name("name");
                gameWriter.value(question.getName());
                gameWriter.name("question");
                gameWriter.value(question.getQuestion());
                gameWriter.name("answer");
                gameWriter.value(question.getAnswer());
                gameWriter.name("activeStatus");
                gameWriter.value(question.getActiveStatus().toString());
                gameWriter.endObject();
            }
            gameWriter.endArray();

            gameWriter.name("difficulty");
            gameWriter.value(this.getDifficulty());
            gameWriter.name("currentQuestionIndex");
            gameWriter.value(this.getDeck().indexOf(this.getCurrentQuestionIndex()));
            gameWriter.name("wrongCounter");
            gameWriter.value(this.getWrongCounter());
            gameWriter.name("attempts");
            gameWriter.value(this.getAttempts());

            gameWriter.name("attemptsList");
            gameWriter.beginArray();

            for(ArrayList<Question> sideDeck : this.getAttemptsList()) {
                gameWriter.beginObject();
                gameWriter.name("questions");
                gameWriter.beginArray();
                for(Question question: sideDeck) {
                    gameWriter.beginObject();
                    gameWriter.name("name");
                    gameWriter.value(question.getName());
                    gameWriter.name("question");
                    gameWriter.value(question.getQuestion());
                    gameWriter.name("answer");
                    gameWriter.value(question.getAnswer());
                    gameWriter.name("activeStatus");
                    gameWriter.value(question.getActiveStatus().toString());
                    gameWriter.endObject();

                }
                gameWriter.endArray();
                gameWriter.endObject();
            }
            gameWriter.endArray();

            gameWriter.endObject();
            gameWriter.close();

        }
        catch (IOException e) {
            Log.e("dir", "Game-File of current game could not be created because " +
                    "some error occurred while constructing the file...");
            return -2;
        }

        return 0;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public int getWrongCounter() {
        return wrongCounter;
    }

    public void incrementWrongCounter()
    {
        wrongCounter++;
    }

    public ArrayList<Question> getDeck() {
        return deck;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public ArrayList<ArrayList<Question>> getAttemptsList() {
        return attemptsList;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }


    public void setDeck(ArrayList<Question> deck) {
        this.deck = deck;
    }


    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public void setWrongCounter(int wrongCounter) {
        this.wrongCounter = wrongCounter;
    }

    public void setAttemptsList(ArrayList<ArrayList<Question>> attemptsList) {
        this.attemptsList = attemptsList;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}
