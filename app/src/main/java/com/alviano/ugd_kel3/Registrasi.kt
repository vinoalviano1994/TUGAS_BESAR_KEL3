package com.alviano.ugd_kel3

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.alviano.ugd_kel3.databinding.ActivityMainBinding
import com.alviano.ugd_kel3.databinding.RegistrasiBinding
import com.alviano.ugd_kel3.room.Customer
import com.alviano.ugd_kel3.room.CustomerDB
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Registrasi : AppCompatActivity() {
    private var _binding: RegistrasiBinding? = null
    private val binding get() = _binding!!
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val notificationId1 = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        _binding = RegistrasiBinding.inflate(inflater, container, false)
        var view : View = binding.root
        createNotificationChannel()
        val button : Button = view.findViewById<Button>(R.id.btnDaftar)
        binding.btnDaftar.setOnClickListener{
            val db by lazy { CustomerDB(this)}
            val username : String = binding.tilUsername?.getEditText()?.getText().toString()
            val password : String = binding.tilPassword?.getEditText()?.getText().toString()
            if(username.isEmpty()) {
                binding.tilUsername.setError("Username tidak boleh kosong")
                return@setOnClickListener
            }
            if(password.isEmpty()) {
                binding.tilPassword.setError("Password tidak boleh kosong")
                return@setOnClickListener
            }
            if(binding.tilEmail.editText?.text.toString().isEmpty()) {
                binding.tilEmail.setError("Email tidak boleh kosong")
                return@setOnClickListener
            }
            if(binding.tilTanggal.editText?.text.toString().isEmpty()) {
                binding.tilTanggal.setError("Tanggal tidak boleh kosong")
                return@setOnClickListener
            }
            if(binding.tilTelp.editText?.text.toString().isEmpty()) {
                binding.tilTelp.setError("Nomor telepon tidak boleh kosong")
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                db.customerDao().addCustomer(
                    Customer(0,
                        username,
                        password,
                        binding.tilEmail.editText?.text.toString(),
                        binding.tilTanggal.editText?.text.toString(),
                        binding.tilTelp.editText?.text.toString()
                    )
                )
                sendNotification()
            }
            val tvLogin : TextView = view.findViewById<TextView>(R.id.tvLogin)

        }
        return view
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description =descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }
    }

    private fun sendNotification(){
        val intent: Intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        val broadCastIntent: Intent = Intent(this, NotificationReceiver::class.java)
        broadCastIntent.putExtra("toastMessage", " Berhasil Registrasi!!")
        broadCastIntent.putExtra("username", binding?.tilUsername?.editText?.text.toString())
        broadCastIntent.putExtra("password", binding?.tilPassword?.editText?.text.toString())
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadCastIntent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_baseline_message_24)
            .setContentTitle("Berhasil Register !")
            .setContentText("Silahkan Login untuk Perawatan :)")
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.RED)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId1, builder.build())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}