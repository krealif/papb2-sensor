package com.example.sensorreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private Sensor sensorLight;
    private Sensor sensorProximity;
    private Sensor sensorAccelerometer;
    private Sensor sensorGyroscope;
    private Sensor sensorPressure;

    private TextView textSensorLight;
    private TextView textSensorProximity;
    private TextView textSensorAccelerometer;
    private TextView textSensorGyroscope;
    private TextView textSensorPressure;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Untuk menampung sensor apa saja
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorText = new StringBuilder();

        for (Sensor currentSensor : sensorList) {
            sensorText.append(currentSensor.getName())
                    .append(System.getProperty("line.separator"));
        }

        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

        textSensorLight = findViewById(R.id.label_light);
        textSensorProximity = findViewById(R.id.label_proximity);
        textSensorAccelerometer = findViewById(R.id.label_accelerometer);
        textSensorGyroscope = findViewById(R.id.label_gyroscope);
        textSensorPressure = findViewById(R.id.label_pressure);

        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);


        String sensorError = "No Sensor";
        if (sensorLight == null) {
            textSensorLight.setText(sensorError);
        }
        if (sensorProximity == null) {
            textSensorProximity.setText(sensorError);
        }
        if (sensorAccelerometer == null) {
            textSensorProximity.setText(sensorError);
        }
        if (sensorGyroscope == null) {
            textSensorProximity.setText(sensorError);
        }
        if (sensorPressure == null) {
            textSensorProximity.setText(sensorError);
        }
    }

    // register sensor ke sensor manager
    @Override
    protected void onStart() {
        super.onStart();
        if(sensorLight != null) {
            sensorManager.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(sensorProximity != null) {
            sensorManager.registerListener(this, sensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(sensorAccelerometer != null) {
            sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(sensorGyroscope != null) {
            sensorManager.registerListener(this, sensorGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(sensorPressure != null) {
            sensorManager.registerListener(this, sensorPressure, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    // listener ketika sensor berubah
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // mendapatkan sensor type
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];
        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                textSensorLight.setText(String.format("Light: %1$.2f", currentValue));
                changeBackgroundColor(currentValue);
                break;
            case Sensor.TYPE_PROXIMITY:
                textSensorProximity.setText(String.format("Proximity: %1$.2f", currentValue));
                break;
            case Sensor.TYPE_ACCELEROMETER:
                textSensorAccelerometer.setText(String.format("Accelerometer: %1$.2f", currentValue));
                break;
            case Sensor.TYPE_GYROSCOPE:
                textSensorGyroscope.setText(String.format("Gyroscope: %1$.2f", currentValue));
                break;
            case Sensor.TYPE_PRESSURE:
                textSensorPressure.setText(String.format("Pressure: %1$.2f", currentValue));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void changeBackgroundColor(float currentValue) {
        ConstraintLayout layout = findViewById(R.id.constraint);
        if (currentValue >= 20000 && currentValue <= 40000) {
            layout.setBackgroundColor(Color.RED);
        } else {
            layout.setBackgroundColor(Color.BLUE);
        }
    }
}