package at.vocabdevelopment.studymanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class EditChallenge extends Activity implements View.OnClickListener{

    public Button buttonSaveChallenge;
    public Button buttonDeleteChallenge;
    public Button buttonEditQuestion;
    public Button buttonAddQuestion;
    public Button buttonDeleteQuestion;
    public EditText editTextChallengeName;
    public ListView questionList;

    public Challenge challenge;
    public int selectedQuestionPos = -1;
    public DialogInterface.OnClickListener dialogDeleteQuestionClickListener;
    public DialogInterface.OnClickListener dialogDeleteChallengeClickListener;
    public List<String> questionNames;
    public ArrayAdapter<String> challengeQuestionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_challenge);

        buttonSaveChallenge = (Button) findViewById(R.id.buttonEditChallengeSaveChallenge);
        buttonDeleteChallenge = (Button) findViewById(R.id.buttonEditChallengeDeleteChallenge);
        buttonEditQuestion = (Button) findViewById(R.id.buttonEditChallengeEditQuestion);
        buttonAddQuestion = (Button) findViewById(R.id.buttonEditChallengeAddQuestion);
        buttonDeleteQuestion = (Button) findViewById(R.id.buttonEditChallengeDeleteQuestion);
        editTextChallengeName = (EditText) findViewById(R.id.editTextEditChallengeChallengeName);
        questionList = (ListView) findViewById(R.id.listViewEditChallengeQuestions);

        buttonSaveChallenge.setOnClickListener(this);
        buttonDeleteChallenge.setOnClickListener(this);
        buttonEditQuestion.setOnClickListener(this);
        buttonAddQuestion.setOnClickListener(this);
        buttonDeleteQuestion.setOnClickListener(this);

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
                    selectedQuestionPos = -1;
                }else{
                    questionList.setAdapter(challengeQuestionsAdapter);
                    challengeQuestionsAdapter.notifyDataSetChanged();
                    selectedQuestionPos = -1;
                }
            }
        };

        dialogDeleteChallengeClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                if (choice == DialogInterface.BUTTON_POSITIVE){
                    int result = challenge.deleteChallengeFile();
                    if(result == 0){
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.toast_success_challenge_deleted), Toast.LENGTH_SHORT).show();
                        Intent browseChallenges = new Intent(getApplicationContext(), BrowseChallenges.class);
                        startActivity(browseChallenges);
                    }else{
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.toast_error_challenge_delete), Toast.LENGTH_SHORT).show();
                        Intent browseChallenges = new Intent(getApplicationContext(), BrowseChallenges.class);
                        startActivity(browseChallenges);
                    }
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
                    }
                });

            }else{
                Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_missing_data), Toast.LENGTH_SHORT).show();
                Intent start = new Intent(getApplicationContext(), Start.class);
                startActivity(start);
            }
        }else{
            Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_missing_data), Toast.LENGTH_SHORT).show();
            Intent start = new Intent(getApplicationContext(), Start.class);
            startActivity(start);
        }
    }

    @Override
    public void onClick(View v) {

        Button clickedButton = (Button) v;

        switch(clickedButton.getId())
        {
            case R.id.buttonEditChallengeSaveChallenge:
                if(challenge.getName().trim().matches("")){
                    Toast.makeText(this, getApplicationContext().getString(R.string.toast_empty_challenge_name), Toast.LENGTH_SHORT).show();
                    return;
                }else if(challenge.getQuestionList().size() <= 0){
                    Toast.makeText(this, getApplicationContext().getString(R.string.toast_one_question), Toast.LENGTH_SHORT).show();
                    return;
                }else{

                    int deleteFileResult = challenge.deleteChallengeFile();
                    int constructFileResult = challenge.constructChallengeFile();

                    if(deleteFileResult == 0 && constructFileResult == 0){
                        Toast.makeText(this, R.string.toast_success_challenge_saved, Toast.LENGTH_SHORT).show();
                        Intent setupChallenge = new Intent(getApplicationContext(), SetupChallenge.class);
                        setupChallenge.putExtra("challenge", challenge);
                        startActivity(setupChallenge);
                    } else {
                        Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_save_data), Toast.LENGTH_SHORT).show();
                        Intent start = new Intent(getApplicationContext(), Start.class);
                        startActivity(start);
                    }
                }
                break;
            case R.id.buttonEditChallengeDeleteChallenge:
                AlertDialog.Builder deleteChallengeBuilder = new AlertDialog.Builder(this);
                deleteChallengeBuilder.setMessage(R.string.dialog_delete_challenge)
                        .setPositiveButton(R.string.dialog_yes, dialogDeleteChallengeClickListener)
                        .setNegativeButton(R.string.dialog_no, dialogDeleteChallengeClickListener)
                        .setCancelable(false).show();
                break;
            case R.id.buttonEditChallengeEditQuestion:
                if(selectedQuestionPos >= 0){
                    Intent editQuestion = new Intent(getApplicationContext(), EditQuestion.class);
                    editQuestion.putExtra("challenge", challenge);
                    editQuestion.putExtra("questionPosition", selectedQuestionPos);
                    editQuestion.putExtra("fromActivity", "editChallenge");
                    startActivity(editQuestion);
                }else{
                    Toast.makeText(this, R.string.toast_select_a_question, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonEditChallengeAddQuestion:
                Intent newQuestion = new Intent(getApplicationContext(), NewQuestion.class);
                newQuestion.putExtra("challenge", challenge);
                newQuestion.putExtra("fromActivity", "editChallenge");
                startActivity(newQuestion);
                break;
            case R.id.buttonEditChallengeDeleteQuestion:
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
            default:
                throw new IllegalArgumentException("Action can not be handled.");
        }
    }
}
