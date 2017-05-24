package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditQuestion extends Activity implements View.OnClickListener{

    public Button buttonSaveQuestionEdit;
    public EditText editTextQuestionEdit;
    public EditText editTextAnswerEdit;
    public EditText editTextQuestionNameEdit;

    public Challenge challenge;
    public Challenge originalChallenge;
    public String fromActivity;
    public int questionPosition;
    public Boolean activeStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        buttonSaveQuestionEdit = (Button) findViewById(R.id.buttonSaveQuestionEdit);
        editTextQuestionEdit = (EditText) findViewById(R.id.editTextQuestionEdit);
        editTextAnswerEdit = (EditText) findViewById(R.id.editTextAnswerEdit);
        editTextQuestionNameEdit = (EditText) findViewById(R.id.editTextQuestionNameEdit);

        buttonSaveQuestionEdit.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null) {
            if (extras.containsKey("challenge") &&
                    extras.containsKey("fromActivity") &&
                    extras.containsKey("questionPosition")) {

                challenge = (Challenge) extras.getSerializable("challenge");
                fromActivity = intent.getStringExtra("fromActivity");

                int position = intent.getIntExtra("questionPosition", -1);
                if(position >= 0 && position < challenge.getQuestionList().size()){
                    questionPosition = position;

                    Question currentQuestion = challenge.getQuestionList().get(questionPosition);
                    activeStatus = currentQuestion.getActiveStatus();
                    editTextQuestionNameEdit.setText(currentQuestion.getName());
                    editTextAnswerEdit.setText(currentQuestion.getAnswer());
                    editTextQuestionEdit.setText(currentQuestion.getQuestion());

                }else{
                    Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_corrupt_data), Toast.LENGTH_SHORT).show();
                    Intent start = new Intent(getApplicationContext(), Start.class);
                    startActivity(start);
                    finish();
                }

                extras.remove("fromActivity");
                extras.remove("challenge");
                extras.remove("questionPosition");

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
            case R.id.buttonSaveQuestionEdit:

                String challengeQuestionName = getChallengeQuestionName();
                String challengeQuestion = getChallengeQuestion();
                String challengeAnswer = getChallengeAnswer();

                if(challengeQuestionName.trim().matches("")) {
                    Toast.makeText(this, R.string.toast_empty_question_name, Toast.LENGTH_SHORT).show();
                    return;
                } else if(challengeQuestion.trim().matches("")){
                    Toast.makeText(this, R.string.toast_empty_challenge_question, Toast.LENGTH_SHORT).show();
                    return;
                } else if(challengeAnswer.trim().matches("")){
                    Toast.makeText(this, R.string.toast_empty_challenge_answer, Toast.LENGTH_SHORT).show();
                    return;
                } else{
                    Question editedQuestion = new Question(challengeQuestionName, challengeQuestion, challengeAnswer);
                    editedQuestion.setActiveStatus(activeStatus);
                    challenge.getQuestionList().set(questionPosition, editedQuestion);

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

                break;
        }
    }

    public String getChallengeQuestion(){
        return editTextQuestionEdit.getText().toString();
    }

    public String getChallengeAnswer(){
        return editTextAnswerEdit.getText().toString();
    }

    public String getChallengeQuestionName(){
        return editTextQuestionNameEdit.getText().toString();
    }
}
