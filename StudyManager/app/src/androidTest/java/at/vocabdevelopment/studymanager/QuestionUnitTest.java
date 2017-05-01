package at.vocabdevelopment.studymanager;

import org.junit.Test;

import static org.junit.Assert.*;


public class QuestionUnitTest {
    @Test
    public void testGetQuestionName(){
        Question question = new Question("QuestionName", "QuestionExample", "QuestionAnswer");
        assertEquals(question.getName(), "QuestionName");
    }

    @Test
    public void testGetQuestion(){
        Question question = new Question("QuestionName", "QuestionExample", "QuestionAnswer");
        assertEquals(question.getQuestion(), "QuestionExample");
    }

    @Test
    public void testGetQuestionAnswer(){
        Question question = new Question("QuestionName", "QuestionExample", "QuestionAnswer");
        assertEquals(question.getAnswer(), "QuestionAnswer");
    }

    @Test
    public void testSetQuestionName(){
        Question question = new Question("QuestionName", "QuestionExample", "QuestionAnswer");
        question.setName("NewQuestionName");
        assertEquals(question.getName(), "NewQuestionName");
    }

    @Test
    public void testSetQuestion(){
        Question question = new Question("QuestionName", "QuestionExample", "QuestionAnswer");
        question.setQuestion("NewQuestionExample");
        assertEquals(question.getQuestion(), "NewQuestionExample");
    }

    @Test
    public void testSetQuestionAnswer(){
        Question question = new Question("QuestionName", "QuestionExample", "QuestionAnswer");
        question.setAnswer("NewQuestionAnswer");
        assertEquals(question.getAnswer(), "NewQuestionAnswer");
    }
}