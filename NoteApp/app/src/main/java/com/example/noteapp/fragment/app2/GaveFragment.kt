package com.example.noteapp.fragment.app2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.GaveFragmentBinding
import com.example.noteapp.model.CustomerDetail
import com.example.noteapp.viewmodel.TransactionViewModel
import java.text.SimpleDateFormat

class GaveFragment:Fragment() {

    lateinit var binding:GaveFragmentBinding
    lateinit var viewModel:TransactionViewModel
    lateinit var time:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.gave_fragment,container,false)

        val argument=GaveFragmentArgs.fromBundle(requireArguments())
        val customer=argument.customer

        viewModel=ViewModelProvider(this).get(TransactionViewModel::class.java)

        binding.gaveSubmit.setOnClickListener {
            val d=binding.gaveEditText.text.toString().trim()
            val gaveMount:Double=d.toDouble()
            val systemTime=System.currentTimeMillis()
            time=convertLongToDateString(systemTime)
            val detailTable=CustomerDetail(gaveAmount = gaveMount,getAmount = 0.0,time = time,phoneNumber = customer.phoneNumber)
            if(gaveMount>0.0){
                viewModel.insertCustomerDetail(detailTable)
                val action=GaveFragmentDirections.actionGaveFragmentToCustomerDetailFragment(customer)
                findNavController().navigate(action)
            }
        }

        return binding.root
    }
    @SuppressLint("SimpleDateFormat")
    fun convertLongToDateString(systemTime: Long): String {
        return SimpleDateFormat(" MMMM dd, h:mm a")
            .format(systemTime).toString()
    }
}