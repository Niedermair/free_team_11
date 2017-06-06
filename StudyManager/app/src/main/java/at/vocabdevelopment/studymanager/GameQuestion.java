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

public class GameQuestion extends Activity implements View.OnClickListener
{
    public TextView questionTxtView;
    public Button showAnswerBtn;
    public Button quitBtn;
    public Game game;

    public DialogInterface.OnClickListener dialogExitChallengeClickListener;

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
        questionTxtView.setText(game.getCurrentQuestionIndex().getQuestion());
        showAnswerBtn.setOnClickListener(this);
        quitBtn.setOnClickListener(this);

        dialogExitChallengeClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                if (choice == DialogInterface.BUTTON_POSITIVE){
                    int constructFileResult = game.constructGameFile();

                    Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.toast_success_game_saved), Toast.LENGTH_SHORT).show();
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
            case R.id.showAnswerBtn:
                Intent startAnswer = new Intent(getApplicationContext(), GameAnswer.class);
                startAnswer.putExtra("game", game);
                startActivity(startAnswer);
                finish();
                break;
            case R.id.quitGameBtn:
                AlertDialog.Builder deleteChallengeBuilder = new AlertDialog.Builder(this);
                deleteChallengeBuilder.setMessage(R.string.dialog_exit_challenge)
                        .setPositiveButton(R.string.dialog_yes, dialogExitChallengeClickListener)
                        .setNegativeButton(R.string.dialog_no, dialogExitChallengeClickListener)
                        .setCancelable(false).show();
                break;
        }
    }
}
