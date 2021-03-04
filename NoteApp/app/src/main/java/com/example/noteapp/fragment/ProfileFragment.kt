package com.example.noteapp.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.noteapp.R
import com.example.noteapp.databinding.ProfileFragmentBinding
import com.example.noteapp.model.Person
import com.example.noteapp.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import java.util.*

class ProfileFragment : Fragment() {
    lateinit var binding: ProfileFragmentBinding
    lateinit var viewModel: ProfileViewModel
    lateinit var person: Person
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)

        val name = binding.nametext
        val email = binding.emailtext
        val phone = binding.phonetext
        val address = binding.addresstext
        val profile = binding.profileImage

        lifecycleScope.launch {
            person = Person(profilePhoto = getBitmap(), person_id = 1)
            viewModel.insertCheck(person)
        }


        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.profileTable.observe(viewLifecycleOwner, Observer {
            it?.let {
                name.text = it.name
                email.text = it.email
                phone.text = it.phone
                profile.load(it.profilePhoto)
                address.setText(it.address)
                binding.profileHeaderName.setText(it.name.toUpperCase(Locale.ROOT))
                binding.profileHeaderAddress.setText(it.address.toUpperCase(Locale.ROOT)+",INDIA")
                person=it
            }
        })

        binding.profileImage.setOnClickListener {
            //val photoIntent = Intent(activity, PhotoActivity::class.java)
        }
        binding.nameField.setOnClickListener {
            val name_text=binding.nametext.text.toString()
            val action = ProfileFragmentDirections.actionProfileFragmentToNameFragment(name_text)
            findNavController().navigate(action)
        }

        binding.addressField.setOnClickListener {
            val address_text=binding.addresstext.text.toString()
            val action = ProfileFragmentDirections.actionProfileFragmentToAddressFragment(address_text)
            findNavController().navigate(action)
        }

        binding.emailField.setOnClickListener {
            val email_text=binding.emailtext.text.toString()
            val action = ProfileFragmentDirections.actionProfileFragmentToEmailFragment(email_text)
            findNavController().navigate(action)
        }

        binding.phoneField.setOnClickListener {
            val phone_text=binding.phonetext.toString().trim()
            val action = ProfileFragmentDirections.actionProfileFragmentToPhoneFragment(phone_text)
            findNavController().navigate(action)
        }

        return binding.root
    }

    //different
    private suspend fun getBitmap(): Bitmap {
        val loading = context?.let { ImageLoader(it) }
        val request = context?.let {
            ImageRequest.Builder(it)
                .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
                .build()
        }

        val result = (request?.let { loading?.execute(it) } as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
}