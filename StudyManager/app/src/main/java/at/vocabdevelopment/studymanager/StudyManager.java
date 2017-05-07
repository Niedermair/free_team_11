package at.vocabdevelopment.studymanager;

import android.os.Environment;
import android.util.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class StudyManager {
    final static File storageDir =
            new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "StudyManager");

    static File getStorageDir(){
        return storageDir;
    }

    static Challenge getChallenge(String challengeName) throws IOException {

        Challenge challenge = new Challenge("", new ArrayList<Question>());

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + challengeName + ".json");
        if(challengeFile.exists()){
            JsonReader reader = new JsonReader(new FileReader(challengeFile));

            reader.beginObject();
            while(reader.hasNext()){
                String objectName = reader.nextName();
                if(objectName.equals("challenge")){
                    reader.beginArray();
                    reader.beginObject();
                    while(reader.hasNext()){
                        String name = reader.nextName();
                        if(name.equals("name")){
                            challenge.setName(reader.nextString());
                        }
                    }
                    reader.endObject();
                    reader.endArray();
                } else if(objectName.equals("questions")){
                    reader.beginArray();
                    while(reader.hasNext()) {
                        reader.beginObject();
                        Question question = new Question("", "", "");
                        while (reader.hasNext()) {
                            String name = reader.nextName();
                            if (name.equals("name")) {
                                question.setName(reader.nextString());
                            } else if (name.equals("question")) {
                                question.setQuestion(reader.nextString());
                            } else if (name.equals("answer")) {
                                question.setAnswer(reader.nextString());
                            }
                        }
                        challenge.addQuestion(question);
                        reader.endObject();
                    }
                    reader.endArray();
                }else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            reader.close();

            return challenge;
        }else{
            throw new FileNotFoundException();
        }
    }
}
