package at.vocabdevelopment.studymanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class GameQuestion extends Activity
{
    public TextView questionTxtView;
    public Button showAnswerBtn;
    public Button quitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionTxtView = (TextView) findViewById(R.id.questionTxtView);
        showAnswerBtn = (Button) findViewById(R.id.showAnswerBtn);
        quitBtn = (Button) findViewById(R.id.quitGameBtn);
    }
}
