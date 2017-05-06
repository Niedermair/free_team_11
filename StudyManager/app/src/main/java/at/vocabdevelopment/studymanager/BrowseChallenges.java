package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BrowseChallenges extends Activity implements View.OnClickListener{

    public Button buttonAddChallenge;
    public Button buttonSelectChallenge;
    public ListView challengeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_challenges);

        buttonAddChallenge = (Button) findViewById(R.id.buttonAddChallenge);
        buttonSelectChallenge = (Button) findViewById(R.id.buttonSelectChallenge);
        challengeList = (ListView) findViewById(R.id.listViewChallenges);

        //TODO: Just for prototype, we should read the json file instead of this...
        File[] challengeFiles = StudyManager.getStorageDir().listFiles();
        List<String> challengeNames = new ArrayList<String>();
        for (File file : challengeFiles) {
            if (file.isFile()) {
                challengeNames.add(file.getName());
            }
        }

        ArrayAdapter<String> challengeFilesAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                challengeNames);

        challengeList.setAdapter(challengeFilesAdapter);

        buttonAddChallenge.setOnClickListener(this);
        buttonSelectChallenge.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Button clickedButton = (Button) v;

        switch(clickedButton.getId()) {
            case R.id.buttonAddChallenge:
                Intent newChallenge = new Intent(getApplicationContext(), NewChallenge.class);
                Challenge challenge = new Challenge("", new ArrayList<Question>());
                newChallenge.putExtra("challenge", challenge);
                startActivity(newChallenge);
                break;
            case R.id.buttonSelectChallenge:
                //TODO: still needs to be implemented...
                System.out.println("Select Button clicked");
                break;
            default:
                throw new IllegalArgumentException("Action can not be handled.");
        }
    }
}
