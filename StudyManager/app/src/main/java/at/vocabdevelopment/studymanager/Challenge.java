package at.vocabdevelopment.studymanager;

import android.util.JsonWriter;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Challenge implements Serializable {

    private String name;
    private ArrayList<Question> questionList;

    public Challenge(String name, ArrayList<Question> questionList) {
        this.name = name;
        this.questionList = questionList;
    }

    public void addQuestion(Question question) {
        this.questionList.add(question);
    }

    public int deleteChallengeFile() {
        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + this.getName() + ".json");
        if (challengeFile.exists()) {
            challengeFile.delete();
            return 0;
        }
        else {
            return -1;
        }
    }

    public int constructChallengeFile() {
        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + this.getName() + ".json");
        if (!challengeFile.exists()) {
            try {

                JsonWriter challengeWriter = new JsonWriter(new FileWriter(challengeFile));
                challengeWriter.beginObject();

                challengeWriter.name("challenge");
                challengeWriter.beginArray();
                challengeWriter.beginObject();
                challengeWriter.name("name");
                challengeWriter.value(this.getName());
                challengeWriter.endObject();
                challengeWriter.endArray();

                challengeWriter.name("questions");
                challengeWriter.beginArray();

                for (Question question : this.getQuestionList()) {
                    challengeWriter.beginObject();
                    challengeWriter.name("name");
                    challengeWriter.value(question.getName());
                    challengeWriter.name("question");
                    challengeWriter.value(question.getQuestion());
                    challengeWriter.name("answer");
                    challengeWriter.value(question.getAnswer());
                    challengeWriter.name("activeStatus");
                    challengeWriter.value(question.getActiveStatus().toString());
                    challengeWriter.endObject();
                }

                challengeWriter.endArray();
                challengeWriter.endObject();

                challengeWriter.close();

            }
            catch (IOException e) {
                Log.e("dir", "Challenge-File could not be created because " +
                        "some error occurred while constructing the file...");
                return -2;
            }
        }
        else {
            return -1;
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }
}
