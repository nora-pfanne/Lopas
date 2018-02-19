package com.example.norablakaj.lateinapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.norablakaj.lateinapp.Activities.Home;
import com.example.norablakaj.lateinapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class SettingsActivity extends AppCompatActivity{

    //Copy/Pasted from: https://stackoverflow.com/questions/2317428/android-i-want-to-shake-it
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    //Copy/Pasted from: https://stackoverflow.com/questions/38743402/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
    Thread thread=new Thread(){
        @Override
        public void run() {
            float i;
            try {
                for ( i = 0; i <= 100; i++) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (Math.abs(mAccel) > 5){
                                deviceShaken();
                            }
                        }

                    });
                    sleep(500);
                }
            }
            catch (InterruptedException e)
            {e.printStackTrace();}
        }
    };




    Switch toggleDevModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toggleDevModeSwitch = findViewById(R.id.toggleDevModeSwitch);

        toggleDevModeSwitch.setChecked(Home.DEVELOPER);
        toggleDevModeSwitch.setVisibility(View.GONE);

        //Copy/Pasted from: https://stackoverflow.com/questions/2317428/android-i-want-to-shake-it
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        thread.start();
    }

    public void switchPressed(View v){

        if (v.getId() == R.id.toggleDevModeSwitch){
            Home.DEVELOPER = toggleDevModeSwitch.isChecked();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    private void deviceShaken(){
        toggleDevModeSwitch.setVisibility(View.VISIBLE);
    }


}
