package at.vocabdevelopment.studymanager;

import org.junit.Test;

import static org.junit.Assert.*;


public class QuestionUnitTest {

    public String exampleQuestionName = "Question Name";
    public String exampleQuestion = "Question Example";
    public String exampleAnswer = "Answer Example";
    public String exampleQuestionNameNew = "Question Name New";
    public String exampleQuestionNew = "Question Example New";
    public String exampleAnswerNew = "Answer Example New";

    @Test
    public void testGetQuestionName(){
        Question question = new Question(exampleQuestionName, exampleQuestion, exampleAnswer);
        assertEquals(question.getName(), exampleQuestionName);
    }

    @Test
    public void testGetQuestion(){
        Question question = new Question(exampleQuestionName, exampleQuestion, exampleAnswer);
        assertEquals(question.getQuestion(), exampleQuestion);
    }

    @Test
    public void testGetQuestionAnswer(){
        Question question = new Question(exampleQuestionName, exampleQuestion, exampleAnswer);
        assertEquals(question.getAnswer(), exampleAnswer);
    }

    @Test
    public void testGetActiveStatus(){
        Question question = new Question(exampleQuestionName, exampleQuestion, exampleAnswer);
        assertEquals(question.getActiveStatus(), true);
    }

    @Test
    public void testSetQuestionName(){
        Question question = new Question(exampleQuestionName, exampleQuestion, exampleAnswer);
        question.setName(exampleQuestionNameNew);
        assertEquals(question.getName(), exampleQuestionNameNew);
    }

    @Test
    public void testSetQuestion(){
        Question question = new Question(exampleQuestionName, exampleQuestion, exampleAnswer);
        question.setQuestion(exampleQuestionNew);
        assertEquals(question.getQuestion(), exampleQuestionNew);
    }

    @Test
    public void testSetQuestionAnswer(){
        Question question = new Question(exampleQuestionName, exampleQuestion, exampleAnswer);
        question.setAnswer(exampleAnswerNew);
        assertEquals(question.getAnswer(), exampleAnswerNew);
    }

    @Test
    public void testSetActiveStatus(){
        Question question = new Question(exampleQuestionName, exampleQuestion, exampleAnswer);
        question.setActiveStatus(false);
        assertEquals(question.getActiveStatus(), false);
    }
}