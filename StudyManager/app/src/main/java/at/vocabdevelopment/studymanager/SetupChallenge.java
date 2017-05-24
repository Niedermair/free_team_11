package at.vocabdevelopment.studymanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SetupChallenge extends Activity implements View.OnClickListener{

    public TextView textViewChallengeName;
    public Button buttonEditChallenge;
    public Button buttonStartGame;
    public Challenge challenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_challenge);

        textViewChallengeName = (TextView) findViewById(R.id.textViewSetupChallengeChallengeName);
        buttonEditChallenge = (Button) findViewById(R.id.buttonSetupChallengeEditChallenge);
        buttonStartGame = (Button) findViewById(R.id.buttonStart);

        buttonEditChallenge.setOnClickListener(this);
        buttonStartGame.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null) {
            if (extras.containsKey("challenge")) {
                challenge = (Challenge) extras.getSerializable("challenge");
                extras.remove("challenge");

                textViewChallengeName.setText(challenge.getName());
            } else {
                Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_missing_data), Toast.LENGTH_SHORT).show();
                Intent start = new Intent(getApplicationContext(), Start.class);
                startActivity(start);
                finish();
            }
        } else {
            Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_missing_data), Toast.LENGTH_SHORT).show();
            Intent start = new Intent(getApplicationContext(), Start.class);
            startActivity(start);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent browseChallenges = new Intent(getApplicationContext(), BrowseChallenges.class);
        startActivity(browseChallenges);
        finish();
    }

    @Override
    public void onClick(View v)
    {
        Button clickedButton = (Button) v;

        switch(clickedButton.getId())
        {
            case R.id.buttonSetupChallengeEditChallenge:
                Intent editChallenge = new Intent(getApplicationContext(), EditChallenge.class);
                editChallenge.putExtra("challenge", challenge);
                editChallenge.putExtra("originalChallenge", challenge);
                startActivity(editChallenge);
                finish();
                break;
            case R.id.buttonStart:
                Game game = new Game(challenge);
                Intent startGame = new Intent(getApplicationContext(), GameQuestion.class);
                startGame.putExtra("game", game);
                startActivity(startGame);
                finish();
                break;
        }
    }
}
