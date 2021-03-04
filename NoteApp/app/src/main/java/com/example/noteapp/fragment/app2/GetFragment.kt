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
import com.example.noteapp.databinding.GetFragmentBinding
import com.example.noteapp.model.Customer
import com.example.noteapp.model.CustomerDetail
import com.example.noteapp.viewmodel.TransactionViewModel
import java.text.SimpleDateFormat

class GetFragment:Fragment() {
    lateinit var binding:GetFragmentBinding
    lateinit var viewModel:TransactionViewModel
    lateinit var time:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.get_fragment,container,false)

        val argument=GetFragmentArgs.fromBundle(requireArguments())
        val customer:Customer=argument.customer
        /**
         * text
         */
        val systemTime=System.currentTimeMillis()
        time=convertLongToDateString(systemTime)

        viewModel=ViewModelProvider(this).get(TransactionViewModel::class.java)

        binding.getButton.setOnClickListener {
            /*val d=binding.getEditText.text.toString().trim()
            val getAmount:Double=d.toDouble()
            if(getAmount>0.0){
                val detailTable=CustomerDetail(gaveAmount = 0.0,getAmount = getAmount,phoneNumber = customer.phoneNumber,time = time)
                viewModel.insertCustomerDetail(detail = detailTable)
                val action=GetFragmentDirections.actionGetFragmentToCustomerDetailFragment(customer)
                findNavController().navigate(action)
            }*/

            val d=binding.getEditText.text.toString().trim()
            val getAmount:Double=d.toDouble()
            val systemTime=System.currentTimeMillis()
            time=convertLongToDateString(systemTime)
            val detailTable=CustomerDetail(gaveAmount = 0.0,getAmount = getAmount,time = time,phoneNumber = customer.phoneNumber)
            if(getAmount>0.0){
                viewModel.insertCustomerDetail(detailTable)
                val action=GetFragmentDirections.actionGetFragmentToCustomerDetailFragment(customer)
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