package at.vocabdevelopment.studymanager;

import com.github.mikephil.charting.data.PieData;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class GameUnitTest
{

    private Challenge challenge = new Challenge("C-Name", new ArrayList<Question>());
    private Question question1 = new Question("Q-Name1", "Q-Question1", "Q-Answer1");
    private Question question2 = new Question("Q-Name2", "Q-Question2", "Q-Answer2");
    private Question question3 = new Question("Q-Name3", "Q-Question3", "Q-Answer3");
    private Question question4 = new Question("Q-Name4", "Q-Question4", "Q-Answer4");
    private Game game;

    public void setupEnvironment() {
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
        challenge.addQuestion(question3);
        challenge.addQuestion(question4);
        game = new Game(challenge, Game.HARD, false);
        game.incrementWrongCounter();
    }

    public void setupEnviormentActiveDeck()
    {
        question1.setActiveStatus(true);
        question2.setActiveStatus(true);
        question3.setActiveStatus(false);
        question4.setActiveStatus(false);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
        challenge.addQuestion(question3);
        challenge.addQuestion(question4);
        game = new Game(challenge, Game.HARD, true);
    }

    @Test public void testPieData()
    {
        setupEnvironment();
        PieData pieData = game.generatePieData();
        assertTrue(pieData.getDataSet().getEntryForIndex(0).getLabel().equals("wrong"));
        assertTrue(pieData.getDataSet().getEntryForIndex(0).getValue() == 1);
        assertTrue(pieData.getDataSet().getEntryForIndex(1).getLabel().equals("correct"));
        assertTrue(pieData.getDataSet().getEntryForIndex(1).getValue() == 3);
    }

    @Test public void testQuestionName() {
        setupEnvironment();
        for (int i = 0; i < challenge.getQuestionList().size(); i++) {
            assertEquals(game.getCurrentQuestionIndex().getName(), game.getDeck().get(i).getName());
            game.hasNextQuestion();
        }
    }

    @Test public void newGame() {
        setupEnvironment();
        assertEquals(game.getChallenge(), challenge);
    }

    @Test public void testQuestionQuestion() {
        setupEnvironment();
        for(int i = 0; i < challenge.getQuestionList().size(); i++) {
            assertEquals(game.getCurrentQuestionIndex().getQuestion(), game.getDeck().get(i).getQuestion());
            game.hasNextQuestion();
        }
    }

    @Test public void testQuestionAnswer()
    {
        setupEnvironment();
        for(int i = 0; i < challenge.getQuestionList().size(); i++) {
            assertEquals(game.getCurrentQuestionIndex().getAnswer(), game.getDeck().get(i).getAnswer());
            game.hasNextQuestion();
        }
    }

    @Test public void testNextQuestion() {
        setupEnvironment();
        for(int i = 0; i < challenge.getQuestionList().size(); i++) {
            assertEquals(game.getCurrentQuestionIndex(), game.getDeck().get(i));
            game.hasNextQuestion();
        }
    }

    @Test public void testNextActiveQuestion()
    {
        setupEnviormentActiveDeck();
        ArrayList<String> questionNames = new ArrayList<String>();
        while (game.hasNextQuestion())
        {
            assertTrue(game.getCurrentQuestionIndex().getName().equals(question1.getName()) ||
                    game.getCurrentQuestionIndex().getName().equals(question2.getName()));
        }

    }

    @Test public void testConstructGameFile(){
        setupEnvironment();

        File gameFile = new File(StudyManager.getCurrentGameDir() + File.separator +
                "currentGame.json");
        if(gameFile.exists()){
            gameFile.delete();
        }

        Assert.assertEquals(game.constructGameFile(), 0);
        assertTrue(gameFile.exists());
    }

}
