package com.alviano.ugd_kel3.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alviano.ugd_kel3.R
import com.alviano.ugd_kel3.RVCustomerAdapter
import com.alviano.ugd_kel3.entity.customer



class Fragment_customer : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_customer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter : RVCustomerAdapter = RVCustomerAdapter(customer.listOfcustomer)
        val rvCustomer : RecyclerView = view.findViewById(R.id.rv_customer)
        rvCustomer.layoutManager = layoutManager
        rvCustomer.setHasFixedSize(true)
        rvCustomer.adapter = adapter
    }
}