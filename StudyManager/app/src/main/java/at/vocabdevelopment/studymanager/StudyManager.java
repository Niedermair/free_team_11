package at.vocabdevelopment.studymanager;

import android.os.Environment;

import java.io.File;

class StudyManager {
    final static File storageDir =
            new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "StudyManager");

    static File getStorageDir(){
        return storageDir;
    }
}
