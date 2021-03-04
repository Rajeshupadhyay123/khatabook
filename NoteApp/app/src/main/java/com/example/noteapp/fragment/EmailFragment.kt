package com.example.noteapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.EmailFragmentBinding
import com.example.noteapp.viewmodel.ProfileViewModel

class EmailFragment:Fragment() {
    lateinit var binding:EmailFragmentBinding
    lateinit var viewModel:ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.email_fragment,container,false)
        viewModel=ViewModelProvider(this).get(ProfileViewModel::class.java)

        val argument=EmailFragmentArgs.fromBundle(requireArguments())
        val email=argument.email
        binding.updateNameEdittextx.setText(email)

        binding.emailSubmit.setOnClickListener {
            val email_text=binding.updateNameEdittextx.text.toString().trim()
            if(email_text.isNotEmpty() || email_text!=""){
                viewModel.emailUpdate(email_text,1)
                val action=EmailFragmentDirections.actionEmailFragmentToProfileFragment()
                findNavController().navigate(action)
            }
        }

        return binding.root
    }
}