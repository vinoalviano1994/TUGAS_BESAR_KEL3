package com.alviano.ugd_kel3

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.*
import android.hardware.Camera
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlin.math.sqrt

class Camera : AppCompatActivity() {
    private var mCamera: Camera? = null
    private var mCameraView: CameraView? = null
    lateinit var sensorStatusTV: TextView
    lateinit var proximitySensor: Sensor
    lateinit var sensorManager: SensorManager
    private var currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f
    private var countShake = 0
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val notificationId1 = 101

    var proximitySensorEventListener: SensorEventListener? = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent) {
            if(event.sensor.type == Sensor.TYPE_PROXIMITY){
                if(event.values[0] == 0f){
                    rotateCamera()
                }
            }
        }
    }

    private fun rotateCamera(){
        mCamera?.release()
        if(currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK){
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        else {
            mCamera?.release()
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        try{
            mCamera = Camera.open(currentCameraId)
        } catch (e: Exception){
            Log.d("Error","Failed to get Camera" + e.message)
        }
        if (mCamera != null) {
            mCameraView = CameraView(this, mCamera!!)
            val camera_view = findViewById<View>(R.id.FLCamera) as FrameLayout
            camera_view.addView(mCameraView)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        createNotificationChannel()
        try{
            mCamera = Camera.open()
        } catch (e: Exception){
            Log.d("Error","Failed to get Camera" + e.message)
        }
        if (mCamera != null){
            mCameraView = CameraView(this, mCamera!!)
            val camera_view = findViewById<View>(R.id.FLCamera) as FrameLayout
            camera_view.addView(mCameraView)

            sensorStatusTV = findViewById(R.id.idTVSensorStatus)

            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

            if(proximitySensor == null) {
                Toast.makeText(this,"No Proximity sensor found in device..", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                sensorManager.registerListener(
                    proximitySensorEventListener,
                    proximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL
                )
            }
        }
        @SuppressLint("MissingInflatedID", "LocalSuppress") val imageClose =
            findViewById<View>(R.id.imgClose) as ImageButton
        imageClose.setOnClickListener { view: View? -> System.exit(0)}
    }

    //untuk sensor shake
    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {

            // Fetching x,y,z values
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration

            // Getting current accelerations
            // with the help of fetched x,y,z values
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta

            // Display a Toast message if
            // acceleration value is over 12
            if (acceleration > 12 && countShake == 0) {
                countShake = countShake + 1

                Toast.makeText(applicationContext, "shake sukses", Toast.LENGTH_SHORT).show()
            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onResume() {
        sensorManager?.registerListener(sensorListener, sensorManager!!.getDefaultSensor(
            Sensor .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()
    }

    override fun onPause() {
        sensorManager!!.unregisterListener(sensorListener)
        super.onPause()
    }

    // untuk send notifikasi


    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }


            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }
    }
}