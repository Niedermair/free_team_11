package at.vocabdevelopment.studymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameAnswer extends Activity implements View.OnClickListener
{
    public TextView questionTxtView;
    public TextView answerTxtView;
    public Button correctBtn;
    public Button wrongBtn;
    //public Challenge challenge;
    public Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        game = (Game) extras.getSerializable("game");

        questionTxtView = (TextView) findViewById(R.id.questionAnswerTxtView);
        answerTxtView = (TextView) findViewById(R.id.answerAnswerTxtView);
        correctBtn = (Button)  findViewById(R.id.correctBtn);
        wrongBtn = (Button)  findViewById(R.id.wrongBtn);

        questionTxtView.setText(game.getCurrentQuestion().getQuestion());
        answerTxtView.setText(game.getCurrentQuestion().getAnswer());
        correctBtn.setOnClickListener(this);
        wrongBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Button clickedButton = (Button) v;

        switch(clickedButton.getId())
        {
            case R.id.correctBtn:
                if(game.hasNextQuestion())
                {
                    game.nextQuestion();
                    Intent correctStartQuestion = new Intent(getApplicationContext(), GameQuestion.class);
                    correctStartQuestion.putExtra("game", game);
                    startActivity(correctStartQuestion);
                    break;
                }
                Intent correctStartQuestion = new Intent(getApplicationContext(), Result.class);
                correctStartQuestion.putExtra("game", game);
                startActivity(correctStartQuestion);
                break;
            case R.id.wrongBtn:
                if(game.hasNextQuestion())
                {
                    game.nextQuestion();
                    Intent wrongStartQuestion = new Intent(getApplicationContext(), GameQuestion.class);
                    wrongStartQuestion.putExtra("game", game);
                    startActivity(wrongStartQuestion);
                    break;
                }
                Intent wrongStartQuestion = new Intent(getApplicationContext(), Result.class);
                wrongStartQuestion.putExtra("game", game);
                startActivity(wrongStartQuestion);
                break;
            default:
                throw new IllegalArgumentException("Action can not be handled.");
        }
    }
}
