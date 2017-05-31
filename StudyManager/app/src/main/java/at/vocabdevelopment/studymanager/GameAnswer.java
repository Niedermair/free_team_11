package at.vocabdevelopment.studymanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameAnswer extends Activity implements View.OnClickListener
{
    public TextView questionTxtView;
    public TextView answerTxtView;
    public Button correctBtn;
    public Button wrongBtn;
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
        extras.remove("game");

        questionTxtView = (TextView) findViewById(R.id.questionAnswerTxtView);
        answerTxtView = (TextView) findViewById(R.id.answerAnswerTxtView);
        correctBtn = (Button)  findViewById(R.id.correctBtn);
        wrongBtn = (Button)  findViewById(R.id.wrongBtn);

        questionTxtView.setText(game.getCurrentQuestionIndex().getQuestion());
        answerTxtView.setText(game.getCurrentQuestionIndex().getAnswer());
        correctBtn.setOnClickListener(this);
        wrongBtn.setOnClickListener(this);

        dialogExitChallengeClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                if (choice == DialogInterface.BUTTON_POSITIVE){
                    int constructFileResult = game.constructGameFile();

                    if(constructFileResult == 0){
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.toast_success_game_saved), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.toast_error_save_data), Toast.LENGTH_SHORT).show();
                    }
                    Intent start = new Intent(getApplicationContext(), Start.class);
                    startActivity(start);
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
                if (game.hasNextQuestion()) {
                    Intent correctStartQuestion = new Intent(getApplicationContext(), GameQuestion.class);
                    correctStartQuestion.putExtra("game", game);
                    startActivity(correctStartQuestion);
                    finish();
                    break;
                }
                else {
                    Intent correctStartQuestion = new Intent(getApplicationContext(), Result.class);
                    correctStartQuestion.putExtra("game", game);
                    startActivity(correctStartQuestion);
                    finish();
                }
                break;
            case R.id.wrongBtn:
                game.storeQuestion();
                if (game.hasNextQuestion()) {
                    Intent wrongStartQuestion = new Intent(getApplicationContext(), GameQuestion.class);
                    wrongStartQuestion.putExtra("game", game);
                    startActivity(wrongStartQuestion);
                    finish();
                    break;
                }
                else {
                    Intent wrongStartQuestion = new Intent(getApplicationContext(), Result.class);
                    game.incrementWrongCounter();
                    wrongStartQuestion.putExtra("game", game);
                    startActivity(wrongStartQuestion);
                    finish();
                }
                break;
        }
    }
}
