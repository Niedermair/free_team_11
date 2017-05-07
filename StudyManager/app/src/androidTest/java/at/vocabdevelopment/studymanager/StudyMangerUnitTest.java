package at.vocabdevelopment.studymanager;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class StudyMangerUnitTest {

    private String challengeName = "Challenge Name";
    private String exampleQuestionName1 = "Question Name 1";
    private String exampleQuestion1 = "Question Example 1";
    private String exampleAnswer1 = "Question Answer 1";
    private String exampleQuestionName2 = "Question Name 2";
    private String exampleQuestion2 = "Question Example 2";
    private String exampleAnswer2 = "Question Answer 2";

    @Test
    public void testGetChallenge() throws Exception {

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + challengeName + ".json");
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
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

        assertEquals(question2Read.getName(), exampleQuestionName2);
        assertEquals(question2Read.getQuestion(), exampleQuestion2);
        assertEquals(question2Read.getAnswer(), exampleAnswer2);
    }

    @Test(expected = FileNotFoundException.class)
    public void testGetChallengeMissingFile() throws Exception {
        Challenge challengeRead = StudyManager.getChallenge("MISSING_CHALLENGE");
    }

}