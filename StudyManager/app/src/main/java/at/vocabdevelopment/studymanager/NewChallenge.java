package at.vocabdevelopment.studymanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class NewChallenge extends Activity implements View.OnClickListener{

    public Button buttonSaveChallenge;
    public Button buttonDeleteChallenge;
    public Button buttonEditQuestion;
    public Button buttonAddQuestion;
    public Button buttonDeleteQuestion;
    public EditText editTextChallengeName;
    public ListView questionList;
    public ToggleButton toggleButtonQuestionStatus;

    public Challenge challenge;
    public int selectedQuestionPos = -1;
    public ArrayAdapter<String> challengeQuestionsAdapter;
    public DialogInterface.OnClickListener dialogDeleteQuestionClickListener;
    public DialogInterface.OnClickListener dialogDeleteChallengeClickListener;
    public List<String> questionNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_challenge);

        buttonSaveChallenge = (Button) findViewById(R.id.buttonSaveChallenge);
        buttonDeleteChallenge = (Button) findViewById(R.id.buttonDeleteChallenge);
        buttonEditQuestion = (Button) findViewById(R.id.buttonEditQuestion);
        buttonAddQuestion = (Button) findViewById(R.id.buttonAddQuestion);
        buttonDeleteQuestion = (Button) findViewById(R.id.buttonDeleteQuestion);
        editTextChallengeName = (EditText) findViewById(R.id.editTextChallengeName);
        questionList = (ListView) findViewById(R.id.listViewQuestions);

        toggleButtonQuestionStatus = (ToggleButton) findViewById(R.id.toggleButtonQuestionStatus);
        toggleButtonQuestionStatus.setEnabled(false);

        buttonSaveChallenge.setOnClickListener(this);
        buttonDeleteChallenge.setOnClickListener(this);
        buttonEditQuestion.setOnClickListener(this);
        buttonAddQuestion.setOnClickListener(this);
        buttonDeleteQuestion.setOnClickListener(this);

        toggleButtonQuestionStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question currentQuestion = challenge.getQuestionList().get(selectedQuestionPos);
                currentQuestion.setActiveStatus(!currentQuestion.getActiveStatus());
            }
        });

        editTextChallengeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                challenge.setName(s.toString());
            }
        });

        dialogDeleteQuestionClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                if (choice == DialogInterface.BUTTON_POSITIVE){
                    challenge.getQuestionList().remove(selectedQuestionPos);
                    questionNames.remove(selectedQuestionPos);

                    questionList.setAdapter(challengeQuestionsAdapter);
                    challengeQuestionsAdapter.notifyDataSetChanged();
                    toggleButtonQuestionStatus.setEnabled(false);
                    selectedQuestionPos = -1;
                }else{
                    questionList.setAdapter(challengeQuestionsAdapter);
                    challengeQuestionsAdapter.notifyDataSetChanged();
                    toggleButtonQuestionStatus.setEnabled(false);
                    selectedQuestionPos = -1;
                }
            }
        };

        dialogDeleteChallengeClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                if (choice == DialogInterface.BUTTON_POSITIVE){
                    Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.toast_success_challenge_deleted), Toast.LENGTH_SHORT).show();
                    Intent browseChallenges = new Intent(getApplicationContext(), BrowseChallenges.class);
                    startActivity(browseChallenges);
                    finish();
                }
            }
        };

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null){
            if(extras.containsKey("challenge")){
                challenge = (Challenge) extras.getSerializable("challenge");
                extras.remove("challenge");

                editTextChallengeName.setText(challenge.getName());

                questionNames = new ArrayList<>();
                for (Question question : challenge.getQuestionList()) {
                    questionNames.add(question.getName());
                }

                challengeQuestionsAdapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        questionNames);

                questionList.setAdapter(challengeQuestionsAdapter);

                questionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedQuestionPos = position;
                        toggleButtonQuestionStatus.setEnabled(true);

                        Question currentChallenge = challenge.getQuestionList().get(selectedQuestionPos);
                        if (currentChallenge.getActiveStatus()){
                            toggleButtonQuestionStatus.setChecked(true);
                        }else{
                            toggleButtonQuestionStatus.setChecked(false);
                        }
                    }
                });
            }else{
                Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_missing_data), Toast.LENGTH_SHORT).show();
                Intent start = new Intent(getApplicationContext(), Start.class);
                startActivity(start);
                finish();
            }
        }else{
            Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_missing_data), Toast.LENGTH_SHORT).show();
            Intent start = new Intent(getApplicationContext(), Start.class);
            startActivity(start);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent browseChallenges = new Intent(getApplicationContext(), BrowseChallenges.class);
        startActivity(browseChallenges);
        finish();
    }

    @Override
    public void onClick(View v) {

        Button clickedButton = (Button) v;

        switch(clickedButton.getId())
        {
            case R.id.buttonSaveChallenge:
                if(challenge.getName().trim().matches("")){
                    Toast.makeText(this, getApplicationContext().getString(R.string.toast_empty_challenge_name), Toast.LENGTH_SHORT).show();
                    return;
                }else if(challenge.getQuestionList().size() <= 0){
                    Toast.makeText(this, getApplicationContext().getString(R.string.toast_one_question), Toast.LENGTH_SHORT).show();
                    return;
                }else{

                    int constructFileResult = challenge.constructChallengeFile();

                    if(constructFileResult == 0){
                        Toast.makeText(this, R.string.toast_success_challenge_saved, Toast.LENGTH_SHORT).show();
                        Intent browseChallenges = new Intent(getApplicationContext(), BrowseChallenges.class);
                        startActivity(browseChallenges);
                        finish();
                    } else if(constructFileResult == -1){
                        Toast.makeText(this, R.string.toast_error_challenge_exists_already, Toast.LENGTH_SHORT).show();
                    } else if(constructFileResult == -2){
                        Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_save_data), Toast.LENGTH_SHORT).show();
                        Intent start = new Intent(getApplicationContext(), Start.class);
                        startActivity(start);
                        finish();
                    }
                }
                break;
            case R.id.buttonDeleteChallenge:
                AlertDialog.Builder deleteChallengeBuilder = new AlertDialog.Builder(this);
                deleteChallengeBuilder.setMessage(R.string.dialog_delete_challenge)
                        .setPositiveButton(R.string.dialog_yes, dialogDeleteChallengeClickListener)
                        .setNegativeButton(R.string.dialog_no, dialogDeleteChallengeClickListener)
                        .setCancelable(false).show();
                break;
            case R.id.buttonEditQuestion:
                if(selectedQuestionPos >= 0){
                    Intent editQuestion = new Intent(getApplicationContext(), EditQuestion.class);
                    editQuestion.putExtra("challenge", challenge);
                    editQuestion.putExtra("questionPosition", selectedQuestionPos);
                    editQuestion.putExtra("fromActivity", "newChallenge");
                    startActivity(editQuestion);
                    finish();
                }else{
                    Toast.makeText(this, R.string.toast_select_a_question, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonAddQuestion:
                Intent newQuestion = new Intent(getApplicationContext(), NewQuestion.class);
                newQuestion.putExtra("challenge", challenge);
                newQuestion.putExtra("fromActivity", "newChallenge");
                startActivity(newQuestion);
                finish();
                break;
            case R.id.buttonDeleteQuestion:
                if(selectedQuestionPos >= 0){
                    AlertDialog.Builder deleteQuestionBuilder = new AlertDialog.Builder(this);
                    deleteQuestionBuilder.setMessage(R.string.dialog_delete_question)
                            .setPositiveButton(R.string.dialog_yes, dialogDeleteQuestionClickListener)
                            .setNegativeButton(R.string.dialog_no, dialogDeleteQuestionClickListener)
                            .setCancelable(false).show();
                }else{
                    Toast.makeText(this, R.string.toast_select_a_question, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
