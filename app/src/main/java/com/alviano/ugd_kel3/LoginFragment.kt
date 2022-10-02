package com.alviano.ugd_kel3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.alviano.ugd_kel3.entity.Customer
import com.alviano.ugd_kel3.room.CustomerDB
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_login, container, false)
        inputUsername = view.findViewById(R.id.inputLayoutUsername)
        inputPassword = view.findViewById(R.id.inputLayoutPassword)

        val extras = activity?.intent?.extras
        Log.d("extras", extras.toString())

        val user: String? = extras?.getString("username", "Tidak tersedia")
        inputUsername.getEditText()?.setText(user)
        val pass: String? = extras?.getString("password", "Tidak tersedia")
        inputPassword.getEditText()?.setText(user)
        val btnLogin: Button = view.findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val db by lazy {
                CustomerDB(requireContext())
            }
            var username: String = inputUsername.getEditText()?.getText().toString();
            var password: String = inputPassword.getEditText()?.getText().toString();

            if (username.isEmpty()) {
                inputUsername.setError("Username must be filled with text")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                inputPassword.setError("Password must be filled with text")
                return@setOnClickListener
            }

            var valid: Boolean = false
            CoroutineScope(Dispatchers.IO).launch {
                val customers: List<Customer> = db.customerDao().getCustomers()
                for (customer in customers) {
                    Log.d("customer", customer.toString())
                    if ((username == customer.nama && password == customer.password)) {
                        val sp = requireActivity().getSharedPreferences("customer", 0)
                        val editor = sp.edit()
                        editor.putInt("id", customer.id)
                        editor.commit()

                        valid = true
                        val mainIntent = Intent(getActivity(), MainActivity::class.java)
                        getActivity()?.startActivity(mainIntent)
                        getActivity()?.finish()


                    }
                }
                return@launch
            }
            val btnClear : Button = view.findViewById<Button>(R.id.btnClear)
            btnClear.setOnClickListener{
                inputUsername = requireView().findViewById(R.id.inputLayoutUsername)
                inputPassword = requireView().findViewById(R.id.inputLayoutPassword)
                inputUsername?.getEditText()?.setText("")
                inputPassword?.getEditText()?.setText("")
            }

            val tvRegister : TextView = view.findViewById<TextView>(R.id.tvRegister)
            tvRegister.setOnClickListener{
                val ft: FragmentTransaction = getParentFragmentManager().beginTransaction()

                ft.replace(R.id.fragmentContainerView, RegisterFragment())
                ft.commit()
            }

        }
        return view
    }
}