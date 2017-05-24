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

        questionTxtView.setText(game.getCurrentQuestionIndex().getQuestion());
        answerTxtView.setText(game.getCurrentQuestionIndex().getAnswer());
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
                if (game.hasNextQuestion()) {
                    Intent correctStartQuestion = new Intent(getApplicationContext(), GameQuestion.class);
                    correctStartQuestion.putExtra("game", game);
                    startActivity(correctStartQuestion);
                }
                else {
                    Intent correctStartQuestion = new Intent(getApplicationContext(), Result.class);
                    correctStartQuestion.putExtra("game", game);
                    startActivity(correctStartQuestion);
                }
                break;
            case R.id.wrongBtn:
                game.storeQuestion();
                if (game.hasNextQuestion()) {
                    Intent wrongStartQuestion = new Intent(getApplicationContext(), GameQuestion.class);
                    wrongStartQuestion.putExtra("game", game);
                    startActivity(wrongStartQuestion);
                }
                else {
                    Intent wrongStartQuestion = new Intent(getApplicationContext(), Result.class);
                    wrongStartQuestion.putExtra("game", game);
                    startActivity(wrongStartQuestion);
                }
                break;
            default:
                throw new IllegalArgumentException("Action can not be handled.");
        }
    }
}
