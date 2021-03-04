package com.example.noteapp.fragment.app2

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.database.TransactionDao
import com.example.noteapp.database.UserDatabase
import com.example.noteapp.databinding.CustomerDetailFragmentBinding
import com.example.noteapp.fragment.adapter.CustomerDetailAdapter
import com.example.noteapp.fragment.adapter.DetailListener
import com.example.noteapp.model.Customer
import com.example.noteapp.model.CustomerDetail
import com.example.noteapp.model.User
import com.example.noteapp.viewmodel.DetailFragmentFactory
import com.example.noteapp.viewmodel.DetailFragmentViewModel
import java.net.URLEncoder


class CustomerDetailFragment:Fragment() {
    lateinit var binding: CustomerDetailFragmentBinding
    lateinit var viewModelFactory:DetailFragmentFactory
    lateinit var viewModel:DetailFragmentViewModel
    lateinit var phoneNumber:String
    lateinit var customer:Customer
    lateinit var price:String
    lateinit var message:String
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val argument=CustomerDetailFragmentArgs.fromBundle(requireArguments())
        customer=argument.customer
        phoneNumber=customer.phoneNumber
        binding=DataBindingUtil.inflate(
            inflater,
            R.layout.customer_detail_fragment,
            container,
            false
        )

        val application= requireNotNull(this.activity).application
        val dao: TransactionDao=UserDatabase.getInstance(application).transactionDao

        viewModelFactory=DetailFragmentFactory(phoneNumber, dao)
        viewModel=ViewModelProvider(this, viewModelFactory).get(DetailFragmentViewModel::class.java)

        binding.priceText.setText(customer.phoneNumber)

        setRecyclerView()

        binding.fabGet.setOnClickListener {
            val action=CustomerDetailFragmentDirections.actionCustomerDetailFragmentToGaveFragment(
                customer
            )
            findNavController().navigate(action)
        }

        binding.fabGave.setOnClickListener {
            val action=CustomerDetailFragmentDirections.actionCustomerDetailFragmentToGetFragment(
                customer
            )
            findNavController().navigate(action)
        }

        //whatsApp call
        binding.whatsppIcon.setOnClickListener {
            //val view=binding.root
            //onClickWhatsApp(view)
            whatsappCall()
        }

        binding.callIcon.setOnClickListener {
            mobileCall()
        }


       viewModel.currentAmount.observe(viewLifecycleOwner, Observer {
           it?.let {
               if (it > 0.0) {
                   binding.priceText.setText("${it.toString()} expected")
                   binding.priceText.setTextColor(resources.getColor(R.color.red))

                   message="Hello ${customer.name}, we are expecting ${it.toString()} rupees from you. Please clear your khata account as soon as possible. THANK YOU!"

                   price = it.toString()
               } else if (it == 0.0) {
                   binding.priceText.setText("${it.toString()} more")
                   binding.priceText.setTextColor(resources.getColor(R.color.green))

                   message="Here ${customer.name}, you have ${it.toString()} rupees in your khata account."

                   price = it.toString()
               } else if (it < 0.0) {
                   binding.priceText.setText("${(-it).toString()} more")
                   binding.priceText.setTextColor(resources.getColor(R.color.green))

                   message="Hello ${customer.name}, you have ${(-it).toString()} more rupees in your khata account. THANK YOU!"

                   price = (-it).toString()
               }
           }

       })

        /*viewModel.updateDetail(currentAmount = priceUpdate,phoneNumber = phoneNumber)
        Toast.makeText(activity,"price: $priceUpdate",Toast.LENGTH_SHORT).show()*/

        return binding.root
    }


   /* private fun onClickWhatsApp(view: View?) {
        val pm: PackageManager = requireContext().packageManager
        try {
            val waIntent = Intent(Intent.ACTION_SEND)
            waIntent.type = "text/plain"
            val text = "Your price is : $price having too many time to renew. please deposit it as soon as possible"
            val info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp")
            waIntent.putExtra(Intent.EXTRA_TEXT, text)
            startActivity(Intent.createChooser(waIntent, "Share with"))
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(activity, "whatsApp is not installed", Toast.LENGTH_LONG).show()
        }
    }*/

    private fun mobileCall(){
        /*val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + customer.phoneNumber))
        startActivity(intent)*/

        val permissionCheck =
            context?.let { ContextCompat.checkSelfPermission(
                it,
                android.Manifest.permission.CALL_PHONE
            ) }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(android.Manifest.permission.CALL_PHONE),
                    123
                )
            }
        } else {
            startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:${customer.phoneNumber}")))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            123 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mobileCall()
            } else {
                Log.d("TAG", "Call Permission Not Granted")
            }
            else -> {
            }
        }
    }

    private fun whatsappCall(){
        val packageManager = requireContext().packageManager
        val i = Intent(Intent.ACTION_VIEW)

        //val message = "Hello ${customer.name}, your price is : $price having too many time to renew. please deposit it as soon as possible"

        try {
            val url =
                "https://api.whatsapp.com/send?phone=" + customer.phoneNumber.toString() + "&text=" + URLEncoder.encode(
                    message,
                    "UTF-8"
                )
            i.setPackage("com.whatsapp")
            i.data = Uri.parse(url)
            if (i.resolveActivity(packageManager) != null) {
                requireContext().startActivity(i)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setRecyclerView(){
        val detailAdapter=CustomerDetailAdapter(DetailListener {
            Toast.makeText(activity, "Transaction Reference ID: ${it.customer_id}", Toast.LENGTH_LONG).show()
        })

        binding.customerDetailRecycler.apply {
            setHasFixedSize(true)
            adapter=detailAdapter
        }

        viewModel.detailTable.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    detailAdapter.submitList(it)
                    currentAmountUpdate()
                    updateUI(it)
                }
            }
        })
    }

    private fun currentAmountUpdate(){
        viewModel.startCalculateCurrentAmount(customer.phoneNumber)
    }

    private fun updateUI(user:List<CustomerDetail>){
        if (user.isNotEmpty()) {
            binding.cardScroll.visibility = View.GONE
            binding.customerDetailRecycler.visibility = View.VISIBLE
        } else {
            binding.cardScroll.visibility = View.VISIBLE
            binding.customerDetailRecycler.visibility = View.GONE
        }
    }
}