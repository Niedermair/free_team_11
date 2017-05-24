package at.vocabdevelopment.studymanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Start extends Activity implements View.OnClickListener{

    public Button buttonContinueChallenge;
    public Button buttonSearchChallenge;
    public TextView textViewPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        buttonContinueChallenge = (Button) findViewById(R.id.buttonContinueChallenge);
        buttonSearchChallenge = (Button) findViewById(R.id.buttonBrowseChallenges);
        textViewPermissions = (TextView) findViewById(R.id.textViewPermissions);

        buttonContinueChallenge.setOnClickListener(this);
        buttonSearchChallenge.setOnClickListener(this);

        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        PackageManager pm = getApplicationContext().getPackageManager();
        int hasPermissionRead = pm.checkPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                getApplicationContext().getPackageName());
        int hasPermissionWrite = pm.checkPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                getApplicationContext().getPackageName());

        if(hasPermissionRead != PackageManager.PERMISSION_GRANTED ||
                hasPermissionWrite != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Start.this, permissions, 401);
        }else{
            buttonContinueChallenge.setEnabled(true);
            buttonSearchChallenge.setEnabled(true);
            textViewPermissions.setVisibility(View.GONE);
            createExternalStorageFolder();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {

        Button clickedButton = (Button) v;

        switch(clickedButton.getId()) {
            case R.id.buttonContinueChallenge:
                //TODO: still needs to be implemented...
                System.out.println("Continue Button clicked");
                break;

            case R.id.buttonBrowseChallenges:
                Intent browseChallenges = new Intent(getApplicationContext(), BrowseChallenges.class);
                startActivity(browseChallenges);
                finish();
                break;
            default:
                throw new IllegalArgumentException("Action can not be handled.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 401: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    buttonContinueChallenge.setEnabled(true);
                    buttonSearchChallenge.setEnabled(true);
                    textViewPermissions.setVisibility(View.GONE);
                    createExternalStorageFolder();

                } else {
                    buttonContinueChallenge.setEnabled(false);
                    buttonSearchChallenge.setEnabled(false);
                    textViewPermissions.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void createExternalStorageFolder(){
        if (!StudyManager.getStorageDir().exists()) {
            StudyManager.getStorageDir().mkdirs();
        }
    }
}
