package at.vocabdevelopment.studymanager;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class GameUnitTest
{

    private Challenge challenge = new Challenge("C-Name", new ArrayList<Question>());
    private Question question1 = new Question("Q-Name1", "Q-Question1", "Q-Answer1");
    private Question question2 = new Question("Q-Name2", "Q-Question2", "Q-Answer2");
    private Question question3 = new Question("Q-Name3", "Q-Question3", "Q-Answer3");
    private Question question4 = new Question("Q-Name4", "Q-Question4", "Q-Answer4");
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
        for(int i = 0; i < challenge.getQuestionList().size(); i++)
        {
            assertEquals(game.getCurrentQuestion().getName(), challenge.getShuffledQuestionList().get(i).getName());
            game.nextQuestion();
        }
    }

    @Test public void newGame()
    {
        setupEnviorment();
        assertEquals(game.getChallenge(), challenge);
    }

    @Test public void testQuestionQuestion()
    {
        setupEnviorment();
        for(int i = 0; i < challenge.getQuestionList().size(); i++)
        {
            assertEquals(game.getCurrentQuestion().getQuestion(), challenge.getShuffledQuestionList().get(i).getQuestion());
            game.nextQuestion();
        }
    }

    @Test public void testQuestionAnswer()
    {
        setupEnviorment();
        for(int i = 0; i < challenge.getQuestionList().size(); i++)
        {
            assertEquals(game.getCurrentQuestion().getAnswer(), challenge.getShuffledQuestionList().get(i).getAnswer());
            game.nextQuestion();
        }
    }

    @Test public void testNextQuestion()
    {
        setupEnviorment();
        for(int i = 0; i < challenge.getQuestionList().size(); i++)
        {
            assertEquals(game.getCurrentQuestion(), challenge.getShuffledQuestionList().get(i));
            game.nextQuestion();
        }
    }
}
