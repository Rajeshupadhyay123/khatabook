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
import com.example.noteapp.databinding.NameFragmentBinding
import com.example.noteapp.model.Person
import com.example.noteapp.viewmodel.ProfileViewModel

class NameFragment:Fragment() {
    lateinit var binding:NameFragmentBinding
    lateinit var viewModel:ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.name_fragment,container,false)

        viewModel=ViewModelProvider(this).get(ProfileViewModel::class.java)

        val argument=NameFragmentArgs.fromBundle(requireArguments())
        binding.nameEdittext.setText(argument.name)

        binding.nameSubmit.setOnClickListener {
            val name=binding.nameEdittext.text.toString().trim()
            if(name.isNotEmpty() || name!=""){
                viewModel.nameUpdate(name = name,person_id = 1)
                val action=NameFragmentDirections.actionNameFragmentToProfileFragment()
                findNavController().navigate(action)
            }
        }

        return binding.root
    }
}