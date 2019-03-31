package org.tensorflow.yolo.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import org.tensorflow.yolo.model.Recognition;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.tensorflow.yolo.Config.LOGGING_TAG;

/**
 * Created by Zoltan Szabo on 4/25/18.
 */

public abstract class TextToSpeechActivity extends CameraActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech textToSpeech;
    private String lastRecognizedClass = "";
    private  Vibrator vibrator;
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(LOGGING_TAG, "Text to speech error: This Language is not supported");
            }
        } else {
            Log.e(LOGGING_TAG, "Text to speech: Initilization Failed!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textToSpeech = new TextToSpeech(this, this);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    protected void speak(List<Recognition> results) {
        if (!(results.isEmpty() || lastRecognizedClass.equals(results.get(0).getTitle()))) {
            lastRecognizedClass = results.get(0).getTitle();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.speak(lastRecognizedClass, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                textToSpeech.speak(lastRecognizedClass, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
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
