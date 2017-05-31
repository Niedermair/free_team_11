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

    final static File currentGameDir =
            new File(Environment.getExternalStorageDirectory().getAbsolutePath()  + File.separator +
                    "StudyManager", "currentGame");

    static File getStorageDir(){
        return storageDir;
    }

    static File getCurrentGameDir(){
        return currentGameDir;
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
                        } else {
                            reader.skipValue();
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
                            } else if (name.equals("activeStatus")) {
                                String status = reader.nextString();
                                if (status.equals("false")){
                                    question.setActiveStatus(false);
                                } else {
                                    question.setActiveStatus(true);
                                }
                            } else {
                               reader.skipValue();
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

    static Game getGame() throws IOException {

        Challenge challenge = new Challenge("", new ArrayList<Question>());
        Game game = new Game(challenge, -1);
        ArrayList<ArrayList<Question>> attemptsList = new ArrayList<>();

        File gameFile = new File(StudyManager.getCurrentGameDir() + File.separator +
                "currentGame.json");

        if(gameFile.exists()){
            JsonReader reader = new JsonReader(new FileReader(gameFile));

            reader.beginObject();
            while(reader.hasNext()){
                String objectName = reader.nextName();
                if(objectName.equals("challenge")){
                    reader.beginArray();
                    reader.beginObject();
                    while(reader.hasNext()){
                        String name = reader.nextName();
                        if(name.equals("name")){
                            // game.setChallenge(getChallenge(reader.nextString()));
                            challenge.setName(reader.nextString());
                        } else {
                            reader.skipValue();
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
                            } else if (name.equals("activeStatus")) {
                                String status = reader.nextString();
                                if (status.equals("false")){
                                    question.setActiveStatus(false);
                                } else {
                                    question.setActiveStatus(true);
                                }
                            } else {
                                reader.skipValue();
                            }
                        }
                        challenge.addQuestion(question);
                        reader.endObject();
                    }
                    reader.endArray();
                    game.setChallenge(challenge);
                    game.setDeck(challenge.getQuestionList());
                } else if(objectName.equals("difficulty")) {
                    game.setDifficulty(reader.nextInt());
                } else if(objectName.equals("currentQuestionIndex")) {
                    game.setCurrentQuestionIndex(reader.nextInt());
                } else if(objectName.equals("wrongCounter")) {
                    game.setWrongCounter(reader.nextInt());
                } else if(objectName.equals("attempts")) {
                    game.setAttempts(reader.nextInt());
                } else if(objectName.equals("attemptsList")){
                    reader.beginArray();
                    while(reader.hasNext()) {
                        reader.beginObject();

                        ArrayList<Question> sideDeck = new ArrayList<>();

                        if(reader.nextName().equals("questions")) {
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
                                    } else if (name.equals("activeStatus")) {
                                        String status = reader.nextString();
                                        if (status.equals("false")){
                                            question.setActiveStatus(false);
                                        } else {
                                            question.setActiveStatus(true);
                                        }
                                    } else {
                                        reader.skipValue();
                                    }
                                }
                                reader.endObject();
                                sideDeck.add(question);
                            }

                            reader.endArray();
                        }
                        reader.endObject();
                        attemptsList.add(sideDeck);
                    }
                    reader.endArray();

                    game.setAttemptsList(attemptsList);
                }else {
                    reader.skipValue();
                }
            }

            reader.endObject();
            reader.close();

            return game;
        }else{

            throw new FileNotFoundException();
        }
    }
}
