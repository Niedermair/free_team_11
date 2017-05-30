package at.vocabdevelopment.studymanager;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

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

    public Game(Challenge challenge, int difficulty)
    {
        this.challenge = challenge;
        this.difficulty = difficulty;
        this.deck = challenge.getQuestionList();
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

    public PieData generatePieData()
    {
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(wrongCounter, "wrong"));
        pieEntries.add(new PieEntry(challenge.getQuestionList().size() - wrongCounter, "correct"));
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Your Result");
        PieData pieData = new PieData(pieDataSet);

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        return pieData;
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

    public ArrayList<Question> getDeck() {
        return deck;
    }
}
