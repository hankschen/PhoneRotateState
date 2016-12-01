package com.example.hanks.phonerotatestate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class compass extends AppCompatActivity {
    SurfaceView surfaceView;
    SensorManager sensorManager;
    SurfaceHolder surfaceHolder;

    int SurfaceView_w,SurfaceView_h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        findviews();
    }

    void findviews(){
        surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                SurfaceView_w = width;
                SurfaceView_h = height;

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        int delay = SensorManager.SENSOR_DELAY_UI;
        sensorManager.registerListener(sensorEvenListener, sensor, delay);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEvenListener);
    }

    SensorEventListener sensorEvenListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            showCompass(values);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    Bitmap bmpCompass;

    void showCompass(float[] values){
        Paint paint=new Paint();
        paint.setAntiAlias(true);

        bmpCompass= BitmapFactory.decodeResource(getResources(),R.drawable.compass);

        Canvas canvas=surfaceHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        canvas.save();
        canvas.rotate(-values[0]-8, SurfaceView_w / 2, SurfaceView_h / 2);
        canvas.drawBitmap(bmpCompass, SurfaceView_w / 2 - bmpCompass.getWidth() / 2, SurfaceView_h / 2 - bmpCompass.getHeight() / 2, paint);
        canvas.restore();
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    public void onPhoneRotate(View view){
        Intent intent = new Intent(compass.this, MainActivity.class);
        startActivity(intent);
    }
}
