package at.vocabdevelopment.studymanager;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class ChallengeUnitTest {

    @Test
    public void testNewChallenge(){
        Challenge challenge = new Challenge("ChallengeName", new ArrayList<Question>());
        assertEquals(challenge.getName(), "ChallengeName");
        assertEquals(challenge.getQuestionList(), new ArrayList<Question>());
    }

    @Test
    public void testChallengeName(){
        Challenge challenge = new Challenge("ChallengeName", new ArrayList<Question>());
        challenge.setName("NewChallengeName");
        assertEquals(challenge.getName(), "NewChallengeName");
    }

    @Test
    public void testQuestionList(){
        Challenge challenge = new Challenge("ChallengeName", new ArrayList<Question>());
        ArrayList<Question> questionList = new ArrayList<Question>();
        Question question1 = new Question("QuestionName1", "QuestionExample1", "AnswerExample1");
        Question question2 = new Question("QuestionName2", "QuestionExample2", "AnswerExample2");
        questionList.add(question1);
        questionList.add(question2);
        challenge.setQuestionList(questionList);
        assertEquals(challenge.getQuestionList().get(0), question1);
        assertEquals(challenge.getQuestionList().get(1), question2);
    }

    @Test
    public void testAddChallengeQuestion(){
        Challenge challenge = new Challenge("ChallengeName", new ArrayList<Question>());
        Question question = new Question("QuestionName", "QuestionExample", "AnswerExample");
        challenge.addQuestion(question);
        assertEquals(challenge.getQuestionList().get(0), question);
    }
}