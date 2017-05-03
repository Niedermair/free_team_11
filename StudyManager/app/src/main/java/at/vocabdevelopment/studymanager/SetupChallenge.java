package at.vocabdevelopment.studymanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SetupChallenge extends Activity {

    public TextView challengeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_challenge);

        challengeName = (TextView) findViewById(R.id.textViewChallengeName);
        challengeName.setText(getIntent().getExtras().getString("SELECTED_CHALLENGE"));
    }
}
