package at.vocabdevelopment.studymanager;

import java.io.Serializable;
import java.util.ArrayList;

public class Challenge implements Serializable {

    private String name;
    private ArrayList<Question> questionList;

    public Challenge(String name, ArrayList<Question> questionList){
        this.name = name;
        this.questionList = questionList;
    }

    public String getName(){
        return name;
    }

    public ArrayList<Question> getQuestionList(){
        return questionList;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setQuestionList(ArrayList<Question> questionList){
        this.questionList = questionList;
    }

    public void addQuestion(Question question){
        this.questionList.add(question);
    }
}
