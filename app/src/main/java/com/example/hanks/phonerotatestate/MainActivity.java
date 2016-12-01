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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imagePlate,imageHight,imageWidth;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        findviews();
    }

    void findviews(){
        imagePlate=(ImageView)findViewById(R.id.imageView);
        imageHight=(ImageView)findViewById(R.id.imageView2);
        imageWidth=(ImageView)findViewById(R.id.imageView3);
    }

    public void onStartRotate(View v){
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        int delay=SensorManager.SENSOR_DELAY_NORMAL;
        sensorManager.registerListener(sensorEventListener,sensor,delay);
    }

    public void onCompass(View view){
        Intent intent = new Intent(MainActivity.this, compass.class);
        startActivity(intent);
    }

    SensorEventListener sensorEventListener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] values=sensorEvent.values;
            showPhoneState(values);

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    void showPhoneState(float[] values){
        Bitmap bmp_PhonePlate= BitmapFactory.decodeResource(getResources(), R.drawable.androidplate);
        Bitmap bmp_PhoneHeight=BitmapFactory.decodeResource(getResources(), R.drawable.androidheight);
        Bitmap bmp_PhoneWidth=BitmapFactory.decodeResource(getResources(), R.drawable.androidwidth);

        int bmp_EmptyPlate_w=imagePlate.getWidth();
        int bmp_EmptyPlate_h=imagePlate.getHeight();
        Bitmap bmp_PhonePlateEmpty=Bitmap.createBitmap(bmp_EmptyPlate_w, bmp_EmptyPlate_h, Bitmap.Config.ARGB_8888);

        int bmp_EmptyHight_w=imageHight.getWidth();
        int bmp_EmptyHight_h = imageHight.getHeight();
        Bitmap bmp_PhoneHeightEmpty=Bitmap.createBitmap(bmp_EmptyHight_w, bmp_EmptyHight_h, Bitmap.Config.ARGB_8888);

        int bmp_EmptyWidth_w=imageWidth.getWidth();
        int bmp_EmptyWidth_h=imageWidth.getHeight();
        Bitmap bmp_PhoneWidthEmpty=Bitmap.createBitmap(bmp_EmptyWidth_w, bmp_EmptyWidth_h, Bitmap.Config.ARGB_8888);

        Paint paint=new Paint();
        paint.setAntiAlias(true);

        Canvas canvas1=new Canvas(bmp_PhonePlateEmpty);
        canvas1.drawColor(Color.WHITE);
        Canvas canvas2=new Canvas(bmp_PhoneHeightEmpty);
        canvas2.drawColor(Color.WHITE);
        Canvas canvas3=new Canvas(bmp_PhoneWidthEmpty);
        canvas3.drawColor(Color.WHITE);

        canvas1.save();
        canvas1.rotate(-values[0], bmp_EmptyPlate_w / 2, bmp_EmptyPlate_h / 2);
        canvas1.drawBitmap(bmp_PhonePlate, bmp_EmptyPlate_w / 2 - bmp_PhonePlate.getWidth() / 2, bmp_EmptyPlate_h / 2 - bmp_PhonePlate.getHeight() / 2, paint);
        canvas1.restore();

        canvas2.save();
        canvas2.rotate(-values[1], bmp_EmptyHight_w / 2, bmp_EmptyHight_h / 2);
        canvas2.drawBitmap(bmp_PhoneHeight, bmp_EmptyHight_w / 2 - bmp_PhoneHeight.getWidth() / 2, bmp_EmptyHight_h / 2 - bmp_PhoneHeight.getHeight() / 2, paint);
        canvas2.restore();

        canvas3.save();
        canvas3.rotate(-values[2], bmp_EmptyWidth_w / 2, bmp_EmptyWidth_h / 2);
        canvas3.drawBitmap(bmp_PhoneWidth, bmp_EmptyWidth_w / 2 - bmp_PhoneWidth.getWidth() / 2, bmp_EmptyWidth_h / 2 - bmp_PhoneWidth.getHeight() / 2, paint);
        canvas3.restore();

        imagePlate.setImageBitmap(bmp_PhonePlateEmpty);
        imageHight.setImageBitmap(bmp_PhoneHeightEmpty);
        imageWidth.setImageBitmap(bmp_PhoneWidthEmpty);

    }
}
