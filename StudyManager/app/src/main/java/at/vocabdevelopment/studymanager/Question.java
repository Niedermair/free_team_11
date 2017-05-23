package at.vocabdevelopment.studymanager;

import java.io.Serializable;

public class Question implements Serializable {

    private String answer;
    private String question;
    private String name;
    private Boolean activeStatus;

    public Question(String name, String question, String answer){
        this.name = name;
        this.question = question;
        this.answer = answer;
        this.activeStatus = true;
    }

    public String getName(){
        return name;
    }

    public String getQuestion(){
        return question;
    }

    public String getAnswer(){
        return answer;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    public void setActiveStatus(Boolean activeStatus){
        this.activeStatus = activeStatus;
    }
}
