package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewQuestion extends Activity implements View.OnClickListener{

    public Button buttonSaveQuestion;
    public EditText editTextQuestion;
    public EditText editTextAnswer;
    public EditText editTextQuestionName;

    public Challenge challenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);

        buttonSaveQuestion = (Button) findViewById(R.id.buttonSaveQuestion);
        editTextQuestion = (EditText) findViewById(R.id.editTextQuestion);
        editTextAnswer = (EditText) findViewById(R.id.editTextAnswer);
        editTextQuestionName = (EditText) findViewById(R.id.editTextQuestionName);

        buttonSaveQuestion.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null) {
            if (extras.containsKey("challenge")) {
                challenge = (Challenge) extras.getSerializable("challenge");
                extras.remove("challenge");
            } else {
                //TODO: PROBLEM - terminate
                Log.e("app", "NO extras...");
            }
        } else{
            //TODO: PROBLEM - terminate
            Log.e("app", "NO Extras...");
        }
    }

    @Override
    public void onClick(View v) {

        Button clickedButton = (Button) v;

        switch(clickedButton.getId())
        {
            case R.id.buttonSaveQuestion:

                String challengeQuestion = getChallengeQuestion();
                String challengeAnswer = getChallengeAnswer();
                String challengeQuestionName = getChallengeQuestionName();

                if(challengeQuestionName.matches("")) {
                    Toast.makeText(this, R.string.toast_empty_question_name, Toast.LENGTH_SHORT).show();
                    return;
                } else if(challengeQuestion.matches("")){
                    Toast.makeText(this, R.string.toast_empty_challenge_question, Toast.LENGTH_SHORT).show();
                    return;
                } else if(challengeAnswer.matches("")){
                    Toast.makeText(this, R.string.toast_empty_challenge_answer, Toast.LENGTH_SHORT).show();
                    return;
                } else{
                    Question newQuestion = new Question(challengeQuestionName, challengeQuestion, challengeAnswer);
                    challenge.addQuestion(newQuestion);

                    Intent newChallenge = new Intent(getApplicationContext(), NewChallenge.class);
                    newChallenge.putExtra("challenge", challenge);
                    startActivity(newChallenge);
                }

                break;
            default:
                //TODO: still needs to be implemented...
                System.out.println("Default behaviour not handled yet...");
                break;
        }
    }

    public String getChallengeQuestion(){
        return editTextQuestion.getText().toString();
    }

    public String getChallengeAnswer(){
        return editTextAnswer.getText().toString();
    }

    public String getChallengeQuestionName(){
        return editTextQuestionName.getText().toString();
    }
}
