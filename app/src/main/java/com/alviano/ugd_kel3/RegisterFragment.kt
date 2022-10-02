package com.alviano.ugd_kel3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.alviano.ugd_kel3.databinding.FragmentRegisterBinding
import com.alviano.ugd_kel3.room.CustomerDB




class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        var view:View = binding.root
        val  btnDaftar : Button = view.findViewById<Button>(R.id.btnDaftar)
        binding.btnDaftar.setOnClickListener{
            val db by lazy { CustomerDB(requireContext()) }
            val username : String = binding.inputLayoutUsername?.getEditText()?.getText().toString()
            val password : String = binding.inputLayoutPassword?.getEditText()?.getText().toString()

            if (username.isEmpty()) {
                binding.inputLayoutUsername.setError("Username must be filled with text")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.inputLayoutPassword.setError("Password must be filled with text")
                return@setOnClickListener
            }
            if (binding.inputLayoutTL.editText?.text.toString().isEmpty()) {
                binding.inputLayoutTL.setError("Date of birth must be filled with text")
                return@setOnClickListener
            }
            if (binding.inputLayoutNoTelp.editText?.text.toString().isEmpty()) {
                binding.inputLayoutNoTelp.setError("Phone number must be filled with text")
                return@setOnClickListener
            }
            if (binding.inputLayoutEmail.editText?.text.toString().isEmpty()) {
                binding.inputLayoutEmail.setError("Email must be filled with text")
                return@setOnClickListener
            }


        }
        val tvLogin : TextView = view.findViewById<TextView>(R.id.tvLogin)
        tvLogin.setOnClickListener{
            val ft: FragmentTransaction = getParentFragmentManager().beginTransaction()
            ft.replace(R.id.fragmentContainerView, LoginFragment())
            ft.commit()
        }
        return view


    }



}