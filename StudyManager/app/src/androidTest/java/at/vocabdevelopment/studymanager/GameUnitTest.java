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

    public void setupEnvironment() {
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
        game = new Game(challenge, Game.HARD);
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
}
