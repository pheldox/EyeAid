package org.tensorflow.yolo.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;

import org.tensorflow.yolo.model.Recognition;

import java.util.ArrayList;
import java.util.List;

public abstract class VibrationActivity extends CameraActivity {
    private String lastRecognizedClass = "";
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    protected void vibration(List<Recognition> results) {
        ArrayList<String> last = new ArrayList<>();

        if (!(results.isEmpty() || lastRecognizedClass.equals(results.get(0).getTitle()))) {
            lastRecognizedClass = results.get(0).getTitle();
            if (lastRecognizedClass.equals("car") || lastRecognizedClass.equals("chair")) {
                vibrator.vibrate(1000);
            }
        }
    }
}
