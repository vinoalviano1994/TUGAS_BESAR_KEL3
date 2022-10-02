package com.alviano.ugd_kel3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.alviano.ugd_kel3.databinding.ActivityMainBinding
import com.alviano.ugd_kel3.room.CustomerDB
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    val db by lazy { CustomerDB(this) }
    lateinit var noteAdapter: RVCustomerAdapter
    private lateinit var inputUssername: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var mainLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("LOGIN")

        inputUssername = findViewById(R.id.etUsername)
        inputPassword = findViewById(R.id.etPassword)
        mainLayout = findViewById(R.id.mainLayout)
        val btnClear: Button = findViewById(R.id.btnClear)
        val btnRegister: TextView = findViewById(R.id.tvRegister)
        val btnLogin: Button = findViewById(R.id.btLogin)

        btnClear.setOnClickListener{
            inputUssername.setText("")
            inputPassword.setText("")

            Snackbar.make(mainLayout, "Text Cleared Success", Snackbar.LENGTH_LONG).show()
        }

        btnRegister.setOnClickListener {
            val moveRegister = Intent(this@MainActivity, Registrasi::class.java)
            startActivity(moveRegister)
        }

        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            var password: String = inputPassword.getText().toString()
            var username: String = inputUssername.getText().toString()

            if(username.isEmpty()){
                inputUssername.setError("Username must be filled with text")
                checkLogin = false
            }
            if(password.isEmpty()){
                inputPassword.setError("Username must be filled with text")
                checkLogin = false
            }
            if(username == "Yesi" && password == "yesi") checkLogin = true
            else if(username == "Willy" && password == "willy") checkLogin = true
            else if(username == "Vino" && password == "vino") checkLogin = true
            else if(username == "Lala" && password == "lala") checkLogin = true
            else if(username == "Yoyo" && password == "yoyo") checkLogin = true
            else if(username == "Giovani" && password == "giovani") checkLogin = true
            else if(username == "Jessica" && password == "jessica") checkLogin = true
            else if(username == "Ricky" && password == "ricky") checkLogin = true
            else if(username == "Nina" && password == "nina") checkLogin = true
            else if(username == "Tian" && password == "tian") checkLogin = true
            else{
                inputUssername.setError("Your Username and Password  are Invalid")
                inputPassword.setError("Your Username and Password  are Invalid")
            }


            if(!checkLogin) return@OnClickListener
            val moveHome = Intent(this@MainActivity, homeActivity::class.java)
            startActivity(moveHome)
        })
    }
}