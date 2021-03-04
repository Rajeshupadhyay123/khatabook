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
import com.example.noteapp.databinding.LoginFragmentBinding

class LoginFragment:Fragment() {
    //lateinit var toolbar: Toolbar
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var checkBox: CheckBox
    lateinit var signin: ImageButton
    lateinit var signup: Button
    lateinit var binding:LoginFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=DataBindingUtil.inflate(inflater,R.layout.login_fragment,container,false)

       // toolbar = binding.toolbar
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)

        email = binding.email
        password = binding.password

        checkBox = binding.checkbox

        signin = binding.signin

        signup = binding.signup

        signin.setOnClickListener {
            Toast.makeText(activity, "Sign In Button Clicked", Toast.LENGTH_LONG)
                .show()
        }

        signup.setOnClickListener {
            val action=LoginFragmentDirections.actionLoginFragmentToSignupFragment2()
            findNavController().navigate(action)
        }

        (activity as MainActivity).setDrawer_Locker()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).setDrawer_UnLocked()
    }
}