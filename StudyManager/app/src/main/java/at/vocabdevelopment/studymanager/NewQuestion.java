package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
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
    public Challenge originalChallenge;
    public String fromActivity;

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
            if (extras.containsKey("challenge") && extras.containsKey("fromActivity")) {
                challenge = (Challenge) extras.getSerializable("challenge");
                fromActivity = intent.getStringExtra("fromActivity");
                extras.remove("fromActivity");
                extras.remove("challenge");
            } else {
                Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_missing_data), Toast.LENGTH_SHORT).show();
                Intent start = new Intent(getApplicationContext(), Start.class);
                startActivity(start);
                finish();
            }
            if(extras.containsKey("originalChallenge")){
                originalChallenge = (Challenge) extras.getSerializable("originalChallenge");
                extras.remove("originalChallenge");
            }else{
                originalChallenge = null;
            }
        } else {
            Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_missing_data), Toast.LENGTH_SHORT).show();
            Intent start = new Intent(getApplicationContext(), Start.class);
            startActivity(start);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (fromActivity.equals("newChallenge")) {
            Intent newChallenge = new Intent(getApplicationContext(), NewChallenge.class);
            newChallenge.putExtra("challenge", challenge);
            startActivity(newChallenge);
            finish();
        } else if(fromActivity.equals("editChallenge")) {
            Intent editChallenge = new Intent(getApplicationContext(), EditChallenge.class);
            editChallenge.putExtra("challenge", challenge);
            editChallenge.putExtra("originalChallenge", originalChallenge);
            startActivity(editChallenge);
            finish();
        } else {
            Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_missing_data), Toast.LENGTH_SHORT).show();
            Intent start = new Intent(getApplicationContext(), Start.class);
            startActivity(start);
            finish();
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

                if(challengeQuestionName.trim().matches("")) {
                    Toast.makeText(this, R.string.toast_empty_question_name, Toast.LENGTH_SHORT).show();
                    return;
                } else if(challengeQuestion.trim().matches("")){
                    Toast.makeText(this, R.string.toast_empty_challenge_question, Toast.LENGTH_SHORT).show();
                } else if(challengeAnswer.trim().matches("")){
                    Toast.makeText(this, R.string.toast_empty_challenge_answer, Toast.LENGTH_SHORT).show();
                } else{
                    Question newQuestion = new Question(challengeQuestionName, challengeQuestion, challengeAnswer);
                    challenge.addQuestion(newQuestion);

                    if (fromActivity.equals("newChallenge")){
                        Intent newChallenge = new Intent(getApplicationContext(), NewChallenge.class);
                        newChallenge.putExtra("challenge", challenge);
                        startActivity(newChallenge);
                        finish();
                    } else if(fromActivity.equals("editChallenge")){
                        Intent editChallenge = new Intent(getApplicationContext(), EditChallenge.class);
                        editChallenge.putExtra("challenge", challenge);
                        editChallenge.putExtra("originalChallenge", originalChallenge);
                        startActivity(editChallenge);
                        finish();
                    } else {
                        Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_missing_data), Toast.LENGTH_SHORT).show();
                        Intent start = new Intent(getApplicationContext(), Start.class);
                        startActivity(start);
                        finish();
                    }
                }

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
