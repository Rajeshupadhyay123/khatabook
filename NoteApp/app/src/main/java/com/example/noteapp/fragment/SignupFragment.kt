package com.example.noteapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.noteapp.MainActivity
import com.example.noteapp.R
import com.example.noteapp.databinding.SignupFragmentBinding

class SignupFragment:Fragment() {
    //lateinit var toolbar: Toolbar
    lateinit var email:EditText
    lateinit var name: EditText
    lateinit var password: EditText
    lateinit var checkBox: CheckBox
    lateinit var signup: ImageButton
    lateinit var signin: Button
    lateinit var binding:SignupFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.signup_fragment,container,false)

        //toolbar = binding.toolbar
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)

        email = binding.email
        password = binding.password
        name = binding.name

        checkBox = binding.checkbox

        signup = binding.signup

        signin = binding.signin

        signup.setOnClickListener {
            Toast.makeText(activity, "Sign Up Button Clicked", Toast.LENGTH_LONG)
                .show()
        }


        signin.setOnClickListener {
            val action=SignupFragmentDirections.actionGlobalLoginFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}