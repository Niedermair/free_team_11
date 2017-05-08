package at.vocabdevelopment.studymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameQuestion extends Activity implements View.OnClickListener
{
    public TextView questionTxtView;
    public Button showAnswerBtn;
    public Button quitBtn;
    public Game game;
    public Challenge challenge;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        game = (Game) extras.getSerializable("game");
        extras.remove("game");

        questionTxtView = (TextView) findViewById(R.id.questionTxtView);
        showAnswerBtn = (Button) findViewById(R.id.showAnswerBtn);
        quitBtn = (Button) findViewById(R.id.quitGameBtn);
        Log.d("StudyManager", "test");
        questionTxtView.setText(game.getCurrentQuestion().getQuestion());
        showAnswerBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        Button clickedButton = (Button) v;
        switch(clickedButton.getId())
        {
            case R.id.showAnswerBtn:
                Intent startAnswer = new Intent(getApplicationContext(), GameAnswer.class);
                startAnswer.putExtra("game", game);
                startActivity(startAnswer);
                break;
            default:
                throw new IllegalArgumentException("Action can not be handled.");
        }
    }
}
