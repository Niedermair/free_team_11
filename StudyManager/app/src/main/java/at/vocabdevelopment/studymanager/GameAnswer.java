package at.vocabdevelopment.studymanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class GameAnswer extends Activity
{
    public TextView questionTxtView;
    public TextView answerAnswerTxtView;
    public Button correctBtn;
    public Button wrongBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        questionTxtView = (TextView) findViewById(R.id.questionAnswerTxtView);
        answerAnswerTxtView = (TextView) findViewById(R.id.answerAnswerTxtView);
        correctBtn = (Button)  findViewById(R.id.correctBtn);
        wrongBtn = (Button)  findViewById(R.id.wrongBtn);
    }


}
