package at.vocabdevelopment.studymanager;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class ChallengeUnitTest {

    public String exampleChallengeName = "Challenge Name";
    public String exampleNewChallengeName = "New Challenge Name";
    public String exampleQuestionName1 = "Question Name 1";
    public String exampleQuestion1 = "Question Example 1";
    public String exampleAnswer1 = "Answer Example 1";
    public String exampleQuestionName2 = "Question Name 2";
    public String exampleQuestion2 = "Question Example 2";
    public String exampleAnswer2 = "Answer Example 2";

    @Test
    public void testNewChallenge(){
        Challenge challenge = new Challenge(exampleChallengeName, new ArrayList<Question>());
        assertEquals(challenge.getName(), exampleChallengeName);
        assertEquals(challenge.getQuestionList(), new ArrayList<Question>());
    }

    @Test
    public void testChallengeName(){
        Challenge challenge = new Challenge(exampleChallengeName, new ArrayList<Question>());
        challenge.setName(exampleNewChallengeName);
        assertEquals(challenge.getName(), exampleNewChallengeName);
    }

    @Test
    public void testQuestionList(){
        Challenge challenge = new Challenge(exampleChallengeName, new ArrayList<Question>());
        ArrayList<Question> questionList = new ArrayList<Question>();
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        questionList.add(question1);
        questionList.add(question2);
        challenge.setQuestionList(questionList);
        assertEquals(challenge.getQuestionList().get(0), question1);
        assertEquals(challenge.getQuestionList().get(1), question2);
    }

    @Test
    public void testAddChallengeQuestion(){
        Challenge challenge = new Challenge(exampleChallengeName, new ArrayList<Question>());
        Question question = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        challenge.addQuestion(question);
        assertEquals(challenge.getQuestionList().get(0), question);
    }

    @Test
    public void testConstructChallengeFile(){
        Challenge challenge = new Challenge(exampleChallengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + exampleChallengeName + ".json");
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        assertEquals(challenge.constructChallengeFile(), 0);
        assertTrue(challengeFile.exists());
    }

    @Test
    public void testConstructChallengeFileError(){
        Challenge challenge = new Challenge(exampleChallengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + exampleChallengeName + ".json");
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        assertEquals(challenge.constructChallengeFile(), 0);
        assertTrue(challengeFile.exists());
        assertEquals(challenge.constructChallengeFile(), -1);
    }

    @Test
    public void testDeleteChallengeFileSuccess(){
        Challenge challenge = new Challenge(exampleChallengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + exampleChallengeName + ".json");
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        assertEquals(challenge.constructChallengeFile(), 0);
        assertTrue(challengeFile.exists());
        assertEquals(challenge.deleteChallengeFile(), 0);
    }

    @Test
    public void testDeleteChallengeFileError(){
        Challenge challenge = new Challenge(exampleChallengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + exampleChallengeName + ".json");
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        assertEquals(challenge.constructChallengeFile(), 0);
        assertTrue(challengeFile.exists());
        assertEquals(challenge.deleteChallengeFile(), 0);
        assertEquals(challenge.deleteChallengeFile(), -1);
    }
}