package com.example.noteapp.fragment.app2

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.databinding.TransactionHomeBinding
import com.example.noteapp.fragment.adapter.TransactionAdapter
import com.example.noteapp.fragment.adapter.TransactionListener
import com.example.noteapp.model.Customer
import com.example.noteapp.viewmodel.TransactionViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class TransactionFragment:Fragment() {
    lateinit var binding:TransactionHomeBinding
    lateinit var viewModel:TransactionViewModel
    lateinit var transactionAdapter: TransactionAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.transaction_home,container,false)
        viewModel=ViewModelProvider(this).get(TransactionViewModel::class.java)
        setRecyclerView()

        binding.fabAddCustomer.setOnClickListener {
            val action=TransactionFragmentDirections.actionTransactionFragmentToAddCustomerFragment()
            findNavController().navigate(action)
        }

        val mMenuSearch=binding.searchView
        mMenuSearch.isSubmitButtonEnabled = false
        mMenuSearch.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchNote(newText)
                }
                return true
            }

        })


        //this is for setting left
        val myCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position: Int = viewHolder.adapterPosition
                val item=transactionAdapter.getPositionAt(position)
                viewModel.deleteCutomer(item)
                viewModel.deleteDetail(item)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                context?.let { ContextCompat.getColor(it, R.color.colorAccent) }?.let {
                    RecyclerViewSwipeDecorator.Builder(
                        c,
                        binding.customerRecyclerview,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                        .addSwipeLeftBackgroundColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.colorAccent
                            )
                        )
                        .addSwipeLeftActionIcon(R.drawable.ic_delete)
                        .addSwipeRightBackgroundColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.design_default_color_on_primary
                            )
                        )
                        .addSwipeRightActionIcon(R.drawable.ic_archive)
                        .addBackgroundColor(it)
                        .addActionIcon(R.drawable.ic_delete)
                        .create()
                        .decorate()
                }

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }
        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(binding.customerRecyclerview)

        return binding.root
    }

    private fun setRecyclerView(){
        transactionAdapter= TransactionAdapter(TransactionListener {customer ->
            customer.let {
                val action=TransactionFragmentDirections.actionTransactionFragmentToCustomerDetailFragment(it)
                view?.findNavController()?.navigate(action)
            }
        })
        binding.customerRecyclerview.apply {
            setHasFixedSize(true)
            adapter=transactionAdapter
        }

        activity?.let {
            viewModel.customerTable.observe(viewLifecycleOwner, Observer {
                it?.let {
                    transactionAdapter.submitList(it)
                    updateUI(it)
                }
            })
        }
    }
    private fun updateUI(customer:List<Customer>){
        if (customer.isNotEmpty()) {
            binding.cardView.visibility = View.GONE
            binding.customerRecyclerview.visibility = View.VISIBLE
        } else {
            binding.cardView.visibility = View.VISIBLE
            binding.customerRecyclerview.visibility = View.GONE
        }
    }

    private fun searchNote(query: String?) {
        val searchQuery = "%$query%"
        viewModel.searchCustomer(searchQuery).observe(
            this, { list ->
                transactionAdapter.submitList(list)
            }
        )
    }

}

