package com.alviano.ugd_kel3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Registrasi : AppCompatActivity() {
    private lateinit var inputUsername: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var inputTl:TextInputEditText
    private lateinit var inputNoTelp: TextInputEditText
    private lateinit var inputEmail: TextInputEditText
    private lateinit var mainLayout: ConstraintLayout

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
            var checkRegistrasi = false
            var username: String = inputUsername.getText().toString()
            var password: String = inputPassword.getText().toString()
            var tanggalLahir: String = inputTl.getText().toString()
            var noTelp: String = inputNoTelp.getText().toString()
            var email: String = inputEmail.getText().toString()

            if(username.isEmpty()){
                inputUsername.setError("Username must be filled with text")
                checkRegistrasi = false
            }else if(password.isEmpty()){
                inputPassword.setError("Password must be filled with text")
                checkRegistrasi = false
            }else if(tanggalLahir.isEmpty()){
                inputTl.setError("Date of birth must be filled with text")
                checkRegistrasi = false
            }else if(noTelp.isEmpty()){
                inputNoTelp.setError("Phone number must be filled with text")
                checkRegistrasi = false
            }else if(email.isEmpty()) {
                inputEmail.setError("Email must be filled with text")
                checkRegistrasi = false
            }else{
                checkRegistrasi = true
            }
            if(!checkRegistrasi) return@OnClickListener
            val moveLogin = Intent(this@Registrasi, MainActivity::class.java)
            startActivity(moveLogin)

        })
        btnLogin.setOnClickListener(View.OnClickListener {
            val moveLogin = Intent(this@Registrasi, MainActivity::class.java)
            startActivity(moveLogin)
        })
    }
}