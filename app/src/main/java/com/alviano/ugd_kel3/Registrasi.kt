package com.alviano.ugd_kel3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.alviano.ugd_kel3.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Registrasi : AppCompatActivity() {
    private lateinit var inputUsername: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var inputTl:TextInputEditText
    private lateinit var inputNoTelp: TextInputEditText
    private lateinit var inputEmail: TextInputEditText
    private lateinit var mainLayout: ConstraintLayout
    private val binding: ActivityMainBinding? = null
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val CHANNEL_ID_2 = "channel_notification_02"
    private val notificationId1 = 101
    private val notificationId2 = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrasi)

        setTitle("User Login")
        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        inputTl = findViewById(R.id.inputLayoutTL)
        inputNoTelp = findViewById(R.id.inputLayoutNoTelp)
        inputEmail = findViewById(R.id.inputLayoutEmail)
        mainLayout = findViewById(R.id.mainLayout)
        val btnDaftar: Button = findViewById(R.id.btnDaftar)
        val btnLogin: TextView = findViewById(R.id.tvLogin)

        btnDaftar.setOnClickListener(View.OnClickListener {
            var checkRegistrasi = true
            var username: String = inputUsername.getText().toString()
            var password: String = inputPassword.getText().toString()
            var tanggalLahir: String = inputTl.getText().toString()
            var noTelp: String = inputNoTelp.getText().toString()
            var email: String = inputEmail.getText().toString()

            if (username.isEmpty()) {
                inputUsername.setError("Username must be filled with text")
                checkRegistrasi = false
            }
            if (password.isEmpty()) {
                inputPassword.setError("Password must be filled with text")
                checkRegistrasi = false
            }
            if (tanggalLahir.isEmpty()) {
                inputTl.setError("Date of birth must be filled with text")
                checkRegistrasi = false
            }
            if (noTelp.isEmpty()) {
                inputNoTelp.setError("Phone number must be filled with text")
                checkRegistrasi = false
            }
            if (email.isEmpty()) {
                inputEmail.setError("Email must be filled with text")
                checkRegistrasi = false
            }
            if (!checkRegistrasi) return@OnClickListener

            val intent: Intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            val broadcastIntent: Intent = Intent(this, NotificationReceiver::class.java)
            broadcastIntent.putExtra("toastMessage", "Regsitrasi")

            val actionIntent = PendingIntent.getBroadcast(
                this,
                0,
                broadcastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_baseline_looks_one_24)
                .setContentTitle("Regiter Atma Salon")
                .setContentText("Anda berhasil Register, silahkan check email untuk verivikasi")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(this)) {
                notify(notificationId1, builder.build())
            }

            val moveLogin = Intent(this@Registrasi, MainActivity::class.java)
            startActivity(moveLogin)

        })
        btnLogin.setOnClickListener(View.OnClickListener {
            val moveLogin = Intent(this@Registrasi, MainActivity::class.java)
            startActivity(moveLogin)
        })

    }
}