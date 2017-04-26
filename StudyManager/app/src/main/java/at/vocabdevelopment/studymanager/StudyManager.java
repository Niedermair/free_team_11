package at.vocabdevelopment.studymanager;

import android.os.Environment;

import java.io.File;

class StudyManager {
    static File storageDir =
            new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "StudyManager");
}
