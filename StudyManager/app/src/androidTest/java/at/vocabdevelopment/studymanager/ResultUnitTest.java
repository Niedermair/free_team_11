package at.vocabdevelopment.studymanager;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResultUnitTest
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
        game = new Game(challenge, Game.HARD, false);
        game.constructGameFile();
    }

    @Test public void testGameFileDeletedSuccess(){
        setupEnvironment();

        File gameFile = new File(StudyManager.getCurrentGameDir() + File.separator +
                "currentGame.json");

        Assert.assertEquals(game.deleteGameFile(), 0);
        assertTrue(!gameFile.exists());
    }

    @Test public void testGameFileDeletedFailed(){
        setupEnvironment();
        game.deleteGameFile();

        File gameFile = new File(StudyManager.getCurrentGameDir() + File.separator +
                "currentGame.json");

        Assert.assertEquals(game.deleteGameFile(), -1);
        assertTrue(!gameFile.exists());
    }
}
