package at.vocabdevelopment.studymanager;

import android.util.JsonWriter;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class StudyMangerUnitTest extends StudyManager {

    private String challengeName = "Challenge Name";
    private String exampleQuestionName1 = "Question Name 1";
    private String exampleQuestion1 = "Question Example 1";
    private String exampleAnswer1 = "Question Answer 1";
    private String exampleQuestionName2 = "Question Name 2";
    private String exampleQuestion2 = "Question Example 2";
    private String exampleAnswer2 = "Question Answer 2";

    @Test
    public void testGetChallenge() throws Exception{

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + challengeName + ".json");
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        question1.setActiveStatus(true);
        question2.setActiveStatus(false);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
        challenge.constructChallengeFile();

        Challenge challengeRead = StudyManager.getChallenge(challengeName);
        assertEquals(challengeRead.getName(), challengeName);

        assertEquals(challengeRead.getQuestionList().size(), 2);

        Question question1Read = challengeRead.getQuestionList().get(0);
        Question question2Read = challengeRead.getQuestionList().get(1);

        assertEquals(question1Read.getName(), exampleQuestionName1);
        assertEquals(question1Read.getQuestion(), exampleQuestion1);
        assertEquals(question1Read.getAnswer(), exampleAnswer1);
        assertTrue(question1Read.getActiveStatus());

        assertEquals(question2Read.getName(), exampleQuestionName2);
        assertEquals(question2Read.getQuestion(), exampleQuestion2);
        assertEquals(question2Read.getAnswer(), exampleAnswer2);
        assertFalse(question2Read.getActiveStatus());

        if(challengeFile.exists()){
            challengeFile.delete();
        }
    }

    @Test
    public void testGetChallenge2() throws Exception {

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + challengeName + ".json");
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        challenge.constructChallengeFile();

        Challenge challengeRead = StudyManager.getChallenge(challengeName);
        assertEquals(challengeRead.getName(), challengeName);

        assertEquals(challengeRead.getQuestionList().size(), 0);

        if(challengeFile.exists()){
            challengeFile.delete();
        }
    }

    @Test
    public void testGetChallengeInvalid() throws Exception {

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + "INVALID_FILE" + ".json");
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        JsonWriter challengeWriter = new JsonWriter(new FileWriter(challengeFile));
        challengeWriter.beginObject();

        challengeWriter.name("invalid");
        challengeWriter.beginArray();
        challengeWriter.beginObject();
        challengeWriter.name("invalid");
        challengeWriter.value("something");
        challengeWriter.endObject();
        challengeWriter.endArray();

        challengeWriter.name("invalid");
        challengeWriter.beginArray();
        challengeWriter.endArray();
        challengeWriter.endObject();

        challengeWriter.close();

        Challenge challengeRead = StudyManager.getChallenge("INVALID_FILE");

        assertEquals(challengeRead.getName(), "");
        assertEquals(challengeRead.getQuestionList().size(), 0);

        if(challengeFile.exists()){
            challengeFile.delete();
        }
    }

    @Test
    public void testGetChallengeInvalid2() throws Exception {

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + "INVALID_FILE" + ".json");
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        JsonWriter challengeWriter = new JsonWriter(new FileWriter(challengeFile));
        challengeWriter.beginObject();

        challengeWriter.name("challenge");
        challengeWriter.beginArray();
        challengeWriter.beginObject();
        challengeWriter.name("invalid");
        challengeWriter.value("something");
        challengeWriter.endObject();
        challengeWriter.endArray();

        challengeWriter.name("invalid");
        challengeWriter.beginArray();
        challengeWriter.endArray();
        challengeWriter.endObject();

        challengeWriter.close();

        Challenge challengeRead = StudyManager.getChallenge("INVALID_FILE");

        assertEquals(challengeRead.getName(), "");
        assertEquals(challengeRead.getQuestionList().size(), 0);

        if(challengeFile.exists()){
            challengeFile.delete();
        }
    }

    @Test
    public void testGetChallengeInvalid3() throws Exception {

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + "INVALID_FILE" + ".json");
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        JsonWriter challengeWriter = new JsonWriter(new FileWriter(challengeFile));
        challengeWriter.beginObject();

        challengeWriter.name("invalid");
        challengeWriter.beginArray();
        challengeWriter.beginObject();
        challengeWriter.name("invalid");
        challengeWriter.value("something");
        challengeWriter.endObject();
        challengeWriter.endArray();

        challengeWriter.name("questions");
        challengeWriter.beginArray();
        challengeWriter.beginObject();
        challengeWriter.name("invalid");
        challengeWriter.value("something");
        challengeWriter.name("activeStatus");
        challengeWriter.value("something");
        challengeWriter.endObject();
        challengeWriter.endArray();
        challengeWriter.endObject();

        challengeWriter.close();

        Challenge challengeRead = StudyManager.getChallenge("INVALID_FILE");

        assertEquals(challengeRead.getName(), "");
        assertEquals(challengeRead.getQuestionList().size(), 1);

        Question question1Read = challengeRead.getQuestionList().get(0);

        assertEquals(question1Read.getName(), "");
        assertEquals(question1Read.getQuestion(), "");
        assertEquals(question1Read.getAnswer(), "");

        if(challengeFile.exists()){
            challengeFile.delete();
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void testGetChallengeMissingFile() throws Exception {
        Challenge challengeRead = StudyManager.getChallenge("MISSING_CHALLENGE_FILE");
    }

    @Test
    public void testGetGameValid() throws Exception{

        File gameFile = new File(StudyManager.getCurrentGameDir() + File.separator +
                "currentGame.json");

        if(gameFile.exists()){
            gameFile.delete();
        }

        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        question1.setActiveStatus(true);
        question2.setActiveStatus(false);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);

        Game game = new Game(challenge, Game.EASY, false);
        game.constructGameFile();

        Game gameRead = StudyManager.getGame();
        assertEquals(gameRead.getChallenge().getName(), challengeName);

        assertEquals(gameRead.getChallenge().getQuestionList().size(), 2);

        ArrayList<String> questionNames = new ArrayList<String>();
        ArrayList<String> questionQuestions = new ArrayList<String>();
        ArrayList<String> questionAnswer = new ArrayList<String>();

        for (Question q: gameRead.getChallenge().getQuestionList())
        {
            questionNames.add(q.getName());
            questionQuestions.add(q.getQuestion());
            questionAnswer.add(q.getAnswer());
        }


        assertTrue(questionNames.contains(question1.getName()));
        assertTrue(questionNames.contains(question2.getName()));
        assertTrue(questionQuestions.contains(question1.getQuestion()));
        assertTrue(questionQuestions.contains(question2.getQuestion()));
        assertTrue(questionAnswer.contains(question1.getAnswer()));
        assertTrue(questionAnswer.contains(question2.getAnswer()));
        assertEquals(game.getDifficulty(), Game.EASY);

        if(gameFile.exists()){
            gameFile.delete();
        }
    }

    @Test
    public void testGetGameInvalid() throws Exception {

        File gameFile = new File(StudyManager.getCurrentGameDir() + File.separator +
                "currentGame.json");
        if(gameFile.exists()){
            gameFile.delete();
        }

        JsonWriter gameWriter = new JsonWriter(new FileWriter(gameFile));
        gameWriter.beginObject();

        gameWriter.name("invalid");
        gameWriter.beginArray();
        gameWriter.beginObject();
        gameWriter.name("invalid");
        gameWriter.value("something");
        gameWriter.endObject();
        gameWriter.endArray();

        gameWriter.name("invalid");
        gameWriter.beginArray();
        gameWriter.endArray();

        gameWriter.name("invalid");
        gameWriter.value(-1);
        gameWriter.name("invalid");
        gameWriter.value(-1);
        gameWriter.name("invalid");
        gameWriter.value(-1);
        gameWriter.name("invalid");
        gameWriter.value(-1);

        gameWriter.name("invalid");
        gameWriter.beginArray();
        gameWriter.endArray();
        gameWriter.endObject();

        gameWriter.close();

        Game gameRead = StudyManager.getGame();

        assertEquals(gameRead.getChallenge().getName(), "");
        assertEquals(gameRead.getChallenge().getQuestionList().size(), 0);
        assertEquals(gameRead.getDeck().size(), 0);
        assertEquals(gameRead.getCurrentQuestionIndex(), null);
        assertEquals(gameRead.getWrongCounter(), 0);
        assertEquals(gameRead.getAttemptsList().size(), 0);
        assertEquals(gameRead.getAttempts(), 0);

        if(gameFile.exists()){
            gameFile.delete();
        }
    }

    @Test public void testGetValidGameWithSideDeck() throws Exception
    {
        File gameFile = new File(StudyManager.getCurrentGameDir() + File.separator +
                "currentGame.json");

        if(gameFile.exists()){
            gameFile.delete();
        }

        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        question1.setActiveStatus(true);
        question2.setActiveStatus(false);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);

        Game game = new Game(challenge, Game.EASY, false);
        game.storeQuestion();
        game.constructGameFile();
        Game gameRead = StudyManager.getGame();

        assertEquals(gameRead.getAttemptsList().size(), 1);
    }

    @Test(expected = FileNotFoundException.class)
    public void testGetGameMissingFile() throws Exception {
        File gameFile = new File(StudyManager.getCurrentGameDir() + File.separator +
                "currentGame.json");
        if(gameFile.exists()){
            gameFile.delete();
        }

        Game gameRead = StudyManager.getGame();
    }

}