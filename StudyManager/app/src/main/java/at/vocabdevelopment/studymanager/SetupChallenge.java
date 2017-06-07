package at.vocabdevelopment.studymanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SetupChallenge extends Activity implements View.OnClickListener {

    public static final int NOT_SET = -1;
    public static final int BLUE = Color.parseColor("#ff0099cc");

    public TextView textViewChallengeName;
    public Button buttonEditChallenge;
    public Button buttonStartGame;
    public Challenge challenge;
    public Button buttonEasy;
    public Button buttonMedium;
    public Button buttonHard;
    public Button buttonActiveDeck;
    public Button buttonTotalDeck;
    public Drawable defaultBackground;
    private int difficulty;
    private boolean active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_challenge);

        textViewChallengeName = (TextView) findViewById(R.id.textViewSetupChallengeChallengeName);
        buttonEditChallenge = (Button) findViewById(R.id.buttonSetupChallengeEditChallenge);
        buttonStartGame = (Button) findViewById(R.id.buttonStart);
        buttonEasy = (Button) findViewById(R.id.buttonEasy);
        buttonMedium = (Button) findViewById(R.id.buttonMedium);
        buttonHard = (Button) findViewById(R.id.buttonHard);
        buttonActiveDeck = (Button) findViewById(R.id.buttonActiveDeck);
        buttonTotalDeck = (Button) findViewById(R.id.buttonTotalDeck);
        defaultBackground = buttonEasy.getBackground();
        difficulty = NOT_SET;

        buttonEditChallenge.setOnClickListener(this);
        buttonStartGame.setOnClickListener(this);
        buttonEasy.setOnClickListener(this);
        buttonMedium.setOnClickListener(this);
        buttonHard.setOnClickListener(this);
        buttonActiveDeck.setOnClickListener(this);
        buttonTotalDeck.setOnClickListener(this);

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
        active = false;
        buttonTotalDeck.setBackgroundColor(BLUE);
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
                if (difficulty != NOT_SET) {
                    Game game = new Game(challenge, difficulty, active);
                    if(game.getDeck().size() > 0)
                    {
                        Intent startGame = new Intent(getApplicationContext(), GameQuestion.class);
                        startGame.putExtra("game", game);
                        startActivity(startGame);
                        finish();
                    }else{
                        Toast.makeText(this, "No active questions in deck.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_difficulty), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonEasy:
                buttonEasy.setBackgroundColor(BLUE);
                buttonMedium.setBackground(defaultBackground);
                buttonHard.setBackground(defaultBackground);
                difficulty = Game.EASY;
                break;
            case R.id.buttonMedium:
                buttonMedium.setBackgroundColor(BLUE);
                buttonEasy.setBackground(defaultBackground);
                buttonHard.setBackground(defaultBackground);
                difficulty = Game.MEDIUM;
                break;
            case R.id.buttonHard:
                buttonHard.setBackgroundColor(BLUE);
                buttonEasy.setBackground(defaultBackground);
                buttonMedium.setBackground(defaultBackground);
                difficulty = Game.HARD;
                break;
            case R.id.buttonActiveDeck:
                active = true;
                buttonActiveDeck.setBackgroundColor(BLUE);
                buttonTotalDeck.setBackground(defaultBackground);
                break;
            case R.id.buttonTotalDeck:
                active = false;
                buttonTotalDeck.setBackgroundColor(BLUE);
                buttonActiveDeck.setBackground(defaultBackground);
                break;
        }
    }
}
