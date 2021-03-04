package com.example.noteapp.fragment.app2

import android.R.string
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.AddCustomerFragmentBinding
import com.example.noteapp.model.Customer
import com.example.noteapp.viewmodel.TransactionViewModel


class AddCustomerFragment : Fragment() {
    lateinit var mView: View
    lateinit var binding: AddCustomerFragmentBinding
    lateinit var viewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.add_customer_fragment, container, false)
        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        mView = binding.root

        AlertDialog.Builder(activity).apply {
            setTitle("Note")
            setMessage("Please enter a valid phone number because this version has no feature for phone verification. THANK YOU!")
            setNegativeButton("OK", null)
        }.create().show()

        return binding.root
    }

    private fun save() {
        val customerName: String = binding.customerName.text.toString().trim()
        val customerPhone: String = binding.customerPhone.text.toString().trim()
        val customerAddress: String = binding.customerAddress.text.toString().trim()

        val count: Int = editTextStringCount(customerPhone)
        if (count == 13) {
            if (customerName.isNotEmpty() && customerPhone.isNotEmpty() && customerAddress.isNotEmpty()) {
                val customer = Customer(
                    name = customerName,
                    address = customerAddress,
                    phoneNumber = customerPhone
                )
                viewModel.insertCheck(customer)

                viewModel.insertFlag.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        if (it == 1) {
                            AlertDialog.Builder(activity).apply {
                                setTitle("Note")
                                setMessage("This data is already register with ${customer.phoneNumber} please enter different phone number!")
                                setNegativeButton("CANCEL", null)
                            }.create().show()
                        }

                    }
                })

                viewModel.customerTable.observe(viewLifecycleOwner, Observer {
                    val action =
                        AddCustomerFragmentDirections.actionAddCustomerFragmentToTransactionFragment()
                    findNavController().navigate(action)
                })

            } else {
                Toast.makeText(activity, "Some field are empty", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(
                activity,
                "phone number is ${count - 3} less then 10 character",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun editTextStringCount(customerPhone: String): Int {
        var count: Int = 0

        //Counts each character except space
        //Counts each character except space
        for (i in 0 until customerPhone.length) {
            if (customerPhone.get(i) !== ' ') count++
        }
        return count
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.save_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                save()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}