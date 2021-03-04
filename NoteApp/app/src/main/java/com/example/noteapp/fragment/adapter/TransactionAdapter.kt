package com.example.noteapp.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.databinding.TransactionListItemBinding
import com.example.noteapp.model.Customer


class TransactionAdapter(val clickListener:TransactionListener) : ListAdapter<Customer, TransactionAdapter.ViewHolder>(TransactionAdapter.UserDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TransactionAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener)
    }

    fun getPositionAt(position: Int): Customer {
        val item=getItem(position)
        return item!!
    }

    class ViewHolder(val binding: TransactionListItemBinding, val parent: ViewGroup) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.customerName
        val phoneNumber = binding.phoneNumber
        val card=binding.contactCard

        //it is for animation
        val listContainer=binding.contactCard

        fun bind(
            item: Customer,
            clickListener: TransactionListener
        ) {
            //it is for animation on item show
            listContainer.animation= AnimationUtils.loadAnimation(parent.context, R.anim.fade_scale_animation)

            binding.customer=item
            binding.clickListener=clickListener
            name.text = item.name
            phoneNumber.text = item.phoneNumber.toString()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TransactionListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding,parent)
            }
        }
    }


    class UserDiffCallback : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem.phoneNumber==newItem.phoneNumber
        }

        override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem==newItem
        }
    }




}

class TransactionListener(val clickListener: (customer: Customer) -> Unit) {
    fun onClick(customer: Customer) = clickListener(customer)
}