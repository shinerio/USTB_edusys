package com.ustb.superui.ustbedu.Utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;

/**
 * Created by jstxzhangrui on 2016/10/4.
 */
public class ShakeListener implements SensorEventListener {
    // 速度阈值，当摇晃速度达到这值后产生作用
    private static final int SPEED_SHRESHOLD = 3000;
    //两次检测时间间隔
    private static final int UPDATE_INTERVAL_TIME = 70;
    //传感器管理器
    private SensorManager sensorManager;
    //传感器
    private  Sensor sensor;
    //加速计感应监听器
    private OnShakeListener onShakeListener;
    //activity上下文
    private Context context;
    //手机上一个位置时感应坐标
    private float lastX;
    private float lastY;
    private float lastZ;
    //上次检测时间
    private long lastUpdateTime = 0;

    public ShakeListener(Context context) {
        this.context = context;
        start();
    }

//开始
    public void start() {
        //获得传感器管理器
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager!=null){
            //获得加速计传感器
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        if(sensor!=null){
            //注册
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME);
        }
    }

    //停止检测
    public void stop(){
        sensorManager.unregisterListener(this);
    }

    // 设置加速计监听器
    public void setOnShakeListener(OnShakeListener listener) {
        onShakeListener = listener;
    }

    //传感器数据变化
    @Override
    public void onSensorChanged(SensorEvent event) {
        //现在检测时间
        Long currentTime =System.currentTimeMillis();
        //两次检测时间差
        Long timeInterval = currentTime-lastUpdateTime;
        //未达到检测间隔
        if(Math.abs(timeInterval)<UPDATE_INTERVAL_TIME)
            return;
        //现在时间变过去时间
        lastUpdateTime = currentTime;
        // 获得x,y,z坐标
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        // 获得x,y,z的变化值
        float deltaX = x - lastX;
        float deltaY = y - lastY;
        float deltaZ = z - lastZ;
        // 将现在的坐标变成last坐标
        lastX = x;
        lastY = y;
        lastZ = z;
        double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
                * deltaZ)
                / timeInterval * 10000;
        if(speed>SPEED_SHRESHOLD)
            onShakeListener.onShake();  //达到速度阈值，响应震动
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
//设置接口，回调使用
    public interface OnShakeListener{
        public void onShake();
    }
}
