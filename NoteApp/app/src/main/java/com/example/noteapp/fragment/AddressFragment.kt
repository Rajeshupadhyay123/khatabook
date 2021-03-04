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
import com.example.noteapp.databinding.AddressFragmentBinding
import com.example.noteapp.model.Person
import com.example.noteapp.viewmodel.ProfileViewModel

class AddressFragment:Fragment() {
    lateinit var binding:AddressFragmentBinding
    lateinit var viewModel:ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.address_fragment,container,false)

        viewModel=ViewModelProvider(this).get(ProfileViewModel::class.java)

        val argument=AddressFragmentArgs.fromBundle(requireArguments())

        val address_text=argument.address
        binding.updateAddressEdittext.setText(address_text)


        binding.addressSubmit.setOnClickListener {
            val address=binding.updateAddressEdittext.text.toString().trim()
            if(address!=""){
                viewModel.addressUpdate(address,1)
                val action=AddressFragmentDirections.actionAddressFragmentToProfileFragment()
                findNavController().navigate(action)
            }else{
                Toast.makeText(activity,"please enter in the field",Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}