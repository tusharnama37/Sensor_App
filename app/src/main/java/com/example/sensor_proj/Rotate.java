package com.example.sensor_proj;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Rotate implements SensorEventListener {

    public interface OnRotationListener {
        void onRotate(String orientation, float x, float y);
    }

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private OnRotationListener rotationListener;

    public Rotate(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void setOnRotationListener(OnRotationListener listener) {
        this.rotationListener = listener;
    }

    public void register() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];

            String orientation;
            if (Math.abs(x) > Math.abs(y)) {
                orientation = x < 0 ? "Right" : "Left";
            } else {
                orientation = y < 0 ? "Down" : "Top";
            }
            if (rotationListener != null) {
                rotationListener.onRotate(orientation, x, y);
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
