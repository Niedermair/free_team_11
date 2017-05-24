package at.vocabdevelopment.studymanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

    public DialogInterface.OnClickListener dialogExitChallengeClickListener;

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

        dialogExitChallengeClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                if (choice == DialogInterface.BUTTON_POSITIVE){
                    Intent browseChallenges = new Intent(getApplicationContext(), BrowseChallenges.class);
                    startActivity(browseChallenges);
                    finish();
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder deleteChallengeBuilder = new AlertDialog.Builder(this);
        deleteChallengeBuilder.setMessage(R.string.dialog_exit_challenge)
                .setPositiveButton(R.string.dialog_yes, dialogExitChallengeClickListener)
                .setNegativeButton(R.string.dialog_no, dialogExitChallengeClickListener)
                .setCancelable(false).show();
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
                    finish();
                    break;
                }
                Intent correctStartQuestion = new Intent(getApplicationContext(), Result.class);
                correctStartQuestion.putExtra("game", game);
                startActivity(correctStartQuestion);
                finish();
                break;
            case R.id.wrongBtn:
                if(game.hasNextQuestion())
                {
                    game.nextQuestion();
                    game.incrementWrongCounter();
                    Intent wrongStartQuestion = new Intent(getApplicationContext(), GameQuestion.class);
                    wrongStartQuestion.putExtra("game", game);
                    startActivity(wrongStartQuestion);
                    finish();
                    break;
                }
                Intent wrongStartQuestion = new Intent(getApplicationContext(), Result.class);
                game.incrementWrongCounter();
                wrongStartQuestion.putExtra("game", game);
                startActivity(wrongStartQuestion);
                finish();
                break;
        }
    }
}
