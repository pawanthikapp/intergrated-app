package com.s23010255.intergratedapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

public class SensorsActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private MediaPlayer mp;
    private boolean isRunning = false;
    private final float THRESHOLD = 55.0f; // Threshold based on SID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        textView = findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE); // Change to TYPE_LIGHT if needed

        if (sensor == null) {
            textView.setText("Ambient Temperature Sensor not available!");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float temperature = event.values[0];
        textView.setText("Current Temp: " + temperature + "°C");

        if (temperature > THRESHOLD && !isRunning) {
            // Start music
            mp = MediaPlayer.create(this, R.raw.song1);
            mp.start();
            isRunning = true;

            // Reset isRunning when music ends
            mp.setOnCompletionListener(mediaPlayer -> {
                isRunning = false;
            });
        } else if (temperature <= THRESHOLD && isRunning) {
            // Stop music
            if (mp != null && mp.isPlaying()) {
                mp.stop();
                mp.release();
                mp = null;
            }
            isRunning = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.release();
        }
    }
}