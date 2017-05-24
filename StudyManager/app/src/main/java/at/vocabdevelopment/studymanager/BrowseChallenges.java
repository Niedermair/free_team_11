package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.sax.SAXTransformerFactory;

public class BrowseChallenges extends Activity implements View.OnClickListener{

    public Button buttonAddChallenge;
    public Button buttonSelectChallenge;
    public ListView challengeList;
    public ArrayAdapter<String> challengeFilesAdapter;

    public Challenge selectedChallenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_challenges);

        buttonAddChallenge = (Button) findViewById(R.id.buttonAddChallenge);
        buttonSelectChallenge = (Button) findViewById(R.id.buttonSelectChallenge);
        challengeList = (ListView) findViewById(R.id.listViewChallenges);

        File[] challengeFiles = StudyManager.getStorageDir().listFiles();
        List<String> challengeNames = new ArrayList<>();
        for (File file : challengeFiles) {
            String fileName = file.getName();
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            challengeNames.add(fileName);
        }

        challengeFilesAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                challengeNames);

        challengeList.setAdapter(challengeFilesAdapter);

        buttonAddChallenge.setOnClickListener(this);
        buttonSelectChallenge.setOnClickListener(this);
        challengeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedChallenge = StudyManager.getChallenge(challengeList.getItemAtPosition(position).toString());
                } catch (IOException e) {
                    selectedChallenge = null;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent start = new Intent(getApplicationContext(), Start.class);
        startActivity(start);
        finish();
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
                finish();
                break;
            case R.id.buttonSelectChallenge:
                if (selectedChallenge == null) {
                    Toast.makeText(this, "Please select a challenge...", Toast.LENGTH_SHORT).show();
                } else {
                    Intent setupChallenge = new Intent(getApplicationContext(), SetupChallenge.class);
                    setupChallenge.putExtra("challenge", selectedChallenge);
                    startActivity(setupChallenge);
                    finish();
                }
                break;
        }
    }
}
