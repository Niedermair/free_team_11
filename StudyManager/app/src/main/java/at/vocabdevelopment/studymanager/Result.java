package at.vocabdevelopment.studymanager;


import android.app.Activity;
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
                break;
            default:
                throw new IllegalArgumentException("Action can not be handled.");
        }
    }
}
