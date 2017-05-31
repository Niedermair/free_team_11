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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

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

        returnToBrowse = (Button) findViewById(R.id.returnToBrowse);
        PieChart pieChart = (PieChart) findViewById(R.id.ChartView);


        pieChart.setData(game.generatePieData());
        pieChart.invalidate();

        returnToBrowse.setOnClickListener(this);


        dialogExitChallengeClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                if (choice == DialogInterface.BUTTON_POSITIVE){
                    int constructFileResult = game.deleteGameFile();
                    if(constructFileResult != 0){
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.toast_error_game_delete), Toast.LENGTH_SHORT).show();
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

        switch (clickedButton.getId())
        {
            case R.id.returnToBrowse:
                int constructFileResult = game.deleteGameFile();
                if(constructFileResult != 0){
                    Toast.makeText(this, R.string.toast_error_game_delete, Toast.LENGTH_SHORT).show();
                }

                Intent start = new Intent(getApplicationContext(), Start.class);
                startActivity(start);
                finish();
                break;
        }
    }
}
