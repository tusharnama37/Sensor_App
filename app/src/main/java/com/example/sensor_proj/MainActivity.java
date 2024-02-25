package com.example.sensor_proj;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

public class MainActivity extends Activity implements Accelerometer.ActivityListener, Rotate.OnRotationListener {

    private Accelerometer accelerometer;
    private Rotate rotate;
    private TextView activityTextView;
    private TextView orientationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextViews
        activityTextView = findViewById(R.id.activity_text);
        orientationTextView = findViewById(R.id.orientation_text);

        // Initialize Accelerometer for activity detection
        accelerometer = new Accelerometer(this);
        accelerometer.setActivityListener(this);

        // Initialize Rotate for orientation detection
        rotate = new Rotate(this);
        rotate.setOnRotationListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.register();
        rotate.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        accelerometer.unregister();
        rotate.unregister();
    }

    @Override
    public void onActivityDetected(String activity,float x, float y, float z) {

        String activityText = "Person is  " + activity + "\nSensor Value is " + "\nX: " + x + "\nY: " + y + "\nZ: " + z;
        Log.d(activityText," Activity with sensor value");
        activityTextView.setText(activityText);
    }

    @Override
    public void onRotate(String orientation, float x, float y) {
        String output = "Orientation: " + orientation + "\nX: " + x + "\nY: " + y;
        TextView orientationTextView = findViewById(R.id.orientation_text);
        orientationTextView.setText(output);
            Log.d(orientation,"Orientation is ");
            Log.i(orientation,"Orientation is ");
            switch (orientation) {
                case "Top":
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    break;
                case "Down":
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    break;
                case "Left":
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
                case "Right":
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    break;
            }
    }
}
