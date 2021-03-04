package com.example.noteapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.PhoneFragmentBinding
import com.example.noteapp.viewmodel.ProfileViewModel

class PhoneFragment:Fragment() {
    lateinit var binding:PhoneFragmentBinding
    lateinit var viewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.phone_fragment,container,false)

        viewModel=ViewModelProvider(this).get(ProfileViewModel::class.java)

        val argument=PhoneFragmentArgs.fromBundle(requireArguments())
        binding.updatePhoneEdittext.setText(argument.phone)

        binding.phoneUpdate.setOnClickListener {
            val phone=binding.updatePhoneEdittext.text.toString().trim()
            if(phone.isNotEmpty() || phone!=""){
                val count:Int=editTextStringCount(phone)
                if(count==13){
                    viewModel.phoneUpdate(phone,1)
                    val action=PhoneFragmentDirections.actionPhoneFragmentToProfileFragment()
                    findNavController().navigate(action)
                }else{
                    Toast.makeText(activity,"phone length is ${count-3} shorter then 10",Toast.LENGTH_SHORT).show()
                }
            }

        }

        return binding.root
    }

    fun editTextStringCount(customerPhone: String):Int{
        var count:Int=0

        //Counts each character except space
        //Counts each character except space
        for (i in 0 until customerPhone.length) {
            if (customerPhone.get(i) !== ' ') count++
        }
        return count
    }

}