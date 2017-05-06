package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewChallenge extends Activity implements View.OnClickListener{

    public Button buttonSaveChallenge;
    public Button buttonDeleteChallenge;
    public Button buttonEditQuestion;
    public Button buttonAddQuestion;
    public Button buttonDeleteQuestion;
    public EditText editTextChallengeName;
    public ListView questionList;

    public Challenge challenge;
    public int selectedQuestionPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_challenge);

        buttonSaveChallenge = (Button) findViewById(R.id.buttonSaveChallenge);
        buttonDeleteChallenge = (Button) findViewById(R.id.buttonDeleteChallenge);
        buttonEditQuestion = (Button) findViewById(R.id.buttonEditQuestion);
        buttonAddQuestion = (Button) findViewById(R.id.buttonAddQuestion);
        buttonDeleteQuestion = (Button) findViewById(R.id.buttonDeleteQuestion);
        editTextChallengeName = (EditText) findViewById(R.id.editTextChallengeName);
        questionList = (ListView) findViewById(R.id.listViewQuestions);

        buttonSaveChallenge.setOnClickListener(this);
        buttonDeleteChallenge.setOnClickListener(this);
        buttonEditQuestion.setOnClickListener(this);
        buttonAddQuestion.setOnClickListener(this);
        buttonDeleteQuestion.setOnClickListener(this);

        editTextChallengeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                challenge.setName(s.toString());
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null){
            if(extras.containsKey("challenge")){
                challenge = (Challenge) extras.getSerializable("challenge");
                extras.remove("challenge");

                editTextChallengeName.setText(challenge.getName());

                List<String> questionNames = new ArrayList<>();
                for (Question question : challenge.getQuestionList()) {
                    questionNames.add(question.getName());
                }

                ArrayAdapter<String> challengeQuestionsAdapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        questionNames);

                questionList.setAdapter(challengeQuestionsAdapter);

                questionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedQuestionPos = position;
                        Log.e("app", "Item position: "+ position);
                    }
                });
            }else{
                Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_missing_data), Toast.LENGTH_SHORT).show();
                Intent start = new Intent(getApplicationContext(), Start.class);
                startActivity(start);
            }
        }else{
            Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_missing_data), Toast.LENGTH_SHORT).show();
            Intent start = new Intent(getApplicationContext(), Start.class);
            startActivity(start);
        }
    }

    @Override
    public void onClick(View v) {

        Button clickedButton = (Button) v;

        switch(clickedButton.getId())
        {
            case R.id.buttonSaveChallenge:
                if(challenge.getName().trim().matches("")){
                    Toast.makeText(this, getApplicationContext().getString(R.string.toast_empty_challenge_name), Toast.LENGTH_SHORT).show();
                    return;
                }else if(challenge.getQuestionList().size() <= 0){
                    Toast.makeText(this, getApplicationContext().getString(R.string.toast_one_question), Toast.LENGTH_SHORT).show();
                    return;
                }else{

                    Integer result = constructChallengeFile(challenge);

                    if(result == 0){
                        Toast.makeText(this, "Challenge saved!", Toast.LENGTH_SHORT).show();
                        Intent browseChallenges = new Intent(getApplicationContext(), BrowseChallenges.class);
                        startActivity(browseChallenges);
                    } else if(result == -1){
                        Toast.makeText(this, "This challenge already exists...", Toast.LENGTH_SHORT).show();
                        return;
                    } else if(result == -2){
                        //TODO: think about what to do here...
                        Toast.makeText(this, "Some error occurred while constructing the challenge file...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
            case R.id.buttonDeleteChallenge:
                //TODO: still needs to be implemented...
                System.out.println("Delete Challenge Button clicked");
                break;
            case R.id.buttonEditQuestion:
                if(selectedQuestionPos >= 0){
                    Intent editQuestion = new Intent(getApplicationContext(), EditQuestion.class);
                    editQuestion.putExtra("challenge", challenge);
                    editQuestion.putExtra("questionPosition", selectedQuestionPos);
                    editQuestion.putExtra("fromActivity", "newChallenge");
                    startActivity(editQuestion);
                }else{
                    Toast.makeText(this, R.string.toast_select_a_question, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonAddQuestion:
                Intent newQuestion = new Intent(getApplicationContext(), NewQuestion.class);
                newQuestion.putExtra("challenge", challenge);
                newQuestion.putExtra("fromActivity", "newChallenge");
                startActivity(newQuestion);
                break;
            case R.id.buttonDeleteQuestion:
                //TODO: still needs to be implemented...
                System.out.println("Delete Question Button clicked");
                break;
            default:
                throw new IllegalArgumentException("Action can not be handled.");
        }

    }

    public int constructChallengeFile(Challenge challenge){

        JsonWriter challengeWriter = null;

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + challenge.getName() + ".json");
        if(!challengeFile.exists()){
            try {

                challengeWriter = new JsonWriter(new FileWriter(challengeFile));
                challengeWriter.beginObject();

                challengeWriter.name("challenge");
                challengeWriter.beginArray();
                challengeWriter.beginObject();
                challengeWriter.name("name");
                challengeWriter.value(challenge.getName());
                challengeWriter.endObject();
                challengeWriter.endArray();

                challengeWriter.name("questions");
                challengeWriter.beginArray();

                for (Question question : challenge.getQuestionList()) {
                    challengeWriter.beginObject();
                    challengeWriter.name("name");
                    challengeWriter.value(question.getName());
                    challengeWriter.name("question");
                    challengeWriter.value(question.getQuestion());
                    challengeWriter.name("answer");
                    challengeWriter.value(question.getAnswer());
                    challengeWriter.endObject();
                }

                challengeWriter.endArray();
                challengeWriter.endObject();

                challengeWriter.close();

            } catch (IOException e) {
                Log.e("dir", "Challenge-File could not be created because " +
                             "some error occured while constructing the file...");
                return -2;
            }
        }else{
            Log.e("dir", "This challenge already exists...");
            return -1;
        }
        return 0;
    }
}
