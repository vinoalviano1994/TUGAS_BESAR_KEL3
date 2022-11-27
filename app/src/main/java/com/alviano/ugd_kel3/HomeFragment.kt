package com.alviano.ugd_kel3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alviano.ugd_kel3.entity.customer
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)

        view.btn_isi_data.setOnClickListener { view ->
            Log.d("btn_isi_data", "Selected")
            val intent = Intent(requireActivity(), IsiDataActivity::class.java)
            startActivity(intent)
        }
    }
}