package com.example.sensor_proj;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Accelerometer implements SensorEventListener {

    public interface ActivityListener {
        void onActivityDetected(String activity, float x, float y, float z);
    }

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private ActivityListener activityListener;

    public Accelerometer(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void setActivityListener(ActivityListener listener) {
        this.activityListener = listener;
    }

    public void register() {
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            double magnitude = Math.sqrt(x*x + y*y + z*z) - 9.81; // Subtracting Earth's gravity value to get the magnitude of dynamic acceleration
            String activity = "Still" ;
            if (magnitude < 1.0) { // Threshold for "Still"
                activity = "Still";
            } else if (magnitude >= 2.0 && magnitude <= 5.0) { // Thresholds for "Walking"
                activity = "Walking";
            } else if (magnitude > 5.0) { // Threshold for "Running"
                activity = "Running";
            }
            notifyActivityListener(activity, x, y, z);
        }

    }

    private void notifyActivityListener(String activity, float x, float y, float z) {
        if (activityListener != null) {
            activityListener.onActivityDetected(activity, x, y, z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
