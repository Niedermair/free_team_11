package at.vocabdevelopment.studymanager;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class GameUnitTest
{

    private Challenge challenge = new Challenge("C-Name", new ArrayList<Question>());
    private Question question1 = new Question("Q-Name1", "Q-Question1", "Q-Answer1");
    private Question question2 = new Question("Q-Name2", "Q-Question2", "Q-Answer2");
    private Game game;

    public void setupEnviorment()
    {
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
        game = new Game(challenge);
    }

    @Test public void testQuestionName()
    {
        setupEnviorment();
        assertEquals(game.getCurrentQuestion().getName(), question1.getName());
    }

    @Test public void newGame()
    {
        setupEnviorment();
        assertEquals(game.getChallenge(), challenge);
    }

    @Test public void testQuestionQuestion()
    {
        setupEnviorment();
        assertEquals(game.getCurrentQuestion().getQuestion(), question1.getQuestion());
    }

    @Test public void testQuestionAnswer()
    {
        setupEnviorment();
        assertEquals(game.getCurrentQuestion().getAnswer(), question1.getAnswer());
    }

    @Test public void testNextQuestion()
    {
        setupEnviorment();
        assertEquals(game.hasNextQuestion(), true);
        game.nextQuestion();
        assertEquals(game.hasNextQuestion(), false);
    }
}
