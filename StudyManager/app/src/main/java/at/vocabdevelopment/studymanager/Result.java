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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;

public class Result extends Activity implements View.OnClickListener
{
    public TextView resultTxtView;
    public Button returnToStart;
    public Game game;

    public DialogInterface.OnClickListener dialogExitChallengeClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        game = (Game) extras.getSerializable("game");

        returnToStart = (Button) findViewById(R.id.returnToStart);
        PieChart pieChart = (PieChart) findViewById(R.id.ChartView);
        pieChart.setData(game.generatePieData());
        pieChart.getData().setValueTextSize(40);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.invalidate();
        returnToStart.setOnClickListener(this);

        dialogExitChallengeClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                if (choice == DialogInterface.BUTTON_POSITIVE){
                    int constructFileResult = game.deleteGameFile();
                    if(constructFileResult != 0){
                        Intent start = new Intent(getApplicationContext(), Start.class);
                        startActivity(start);
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.toast_error_game_delete), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Intent start = new Intent(getApplicationContext(), Start.class);
                        startActivity(start);
                        finish();
                    }
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
            case R.id.returnToStart:
                int constructFileResult = game.deleteGameFile();
                Intent start = new Intent(getApplicationContext(), Start.class);
                startActivity(start);
                finish();
                break;
        }
    }
}
