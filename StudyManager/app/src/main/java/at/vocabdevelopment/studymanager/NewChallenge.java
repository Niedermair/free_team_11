package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class NewChallenge extends Activity implements View.OnClickListener{

    public Button buttonSaveChallenge;
    public Button buttonDeleteChallenge;
    public Button buttonEditQuestion;
    public Button buttonAddQuestion;
    public Button buttonDeleteQuestion;
    public EditText editTextChallengeName;

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

        buttonSaveChallenge.setOnClickListener(this);
        buttonDeleteChallenge.setOnClickListener(this);
        buttonEditQuestion.setOnClickListener(this);
        buttonAddQuestion.setOnClickListener(this);
        buttonDeleteQuestion.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Button clickedButton = (Button) v;

        switch(clickedButton.getId())
        {
            case R.id.buttonSaveChallenge:
                //TODO: check tat challengeName is not empty and at least one question is inserted

                String challengeName = editTextChallengeName.getText().toString();
                if(challengeName.matches("")){
                    Toast.makeText(this, "You did not enter a Challenge Name", Toast.LENGTH_SHORT).show();
                    return;
                }else{

                    //TODO: construct challenge file, the following is just for the prototype
                    File challengeFile = new File(StudyManager.storageDir + File.separator + challengeName + ".txt");
                    if(!challengeFile.exists()){
                        try {
                            challengeFile.createNewFile();
                            Log.d("dir", "Challenge file created.");

                            Intent browseChallenges = new Intent(getApplicationContext(), BrowseChallenges.class);
                            startActivity(browseChallenges);
                            break;
                        } catch (IOException e) {
                            Log.e("dir", "Challenge File could not be created...");
                        }
                    }else{
                        Toast.makeText(this, "Challenge already exists...", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                break;
            case R.id.buttonDeleteChallenge:
                //TODO: still needs to be implemented...
                System.out.println("Delete Challenge Button clicked");
                break;
            case R.id.buttonEditQuestion:
                //TODO: still needs to be implemented...
                System.out.println("Edit Question Button clicked");
                break;
            case R.id.buttonAddQuestion:
                //TODO: still needs to be implemented...
                System.out.println("Add Question Button clicked");
                break;
            case R.id.buttonDeleteQuestion:
                //TODO: still needs to be implemented...
                System.out.println("Delete Question Button clicked");
                break;
            default:
                //TODO: still needs to be implemented...
                System.out.println("Default behaviour not handled yet...");
                break;
        }

    }


}
