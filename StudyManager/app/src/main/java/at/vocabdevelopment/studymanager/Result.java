package at.vocabdevelopment.studymanager;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends Activity implements View.OnClickListener
{
    public TextView resultTxtView;
    public Button returnToBrowse;
    private Game game;

    public DialogInterface.OnClickListener dialogExitChallengeClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        game = (Game) extras.getSerializable("game");

        resultTxtView = (TextView) findViewById(R.id.ResultTxtView);
        returnToBrowse = (Button) findViewById(R.id.returnToBrowse);

        returnToBrowse.setOnClickListener(this);

        resultTxtView.setText("Number of wrong answered questions: " + game.getWrongCounter());

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

        switch (clickedButton.getId())
        {
            case R.id.returnToBrowse:
                Intent browseChallenges = new Intent(getApplicationContext(), BrowseChallenges.class);
                startActivity(browseChallenges);
                finish();
                break;
        }
    }
}
