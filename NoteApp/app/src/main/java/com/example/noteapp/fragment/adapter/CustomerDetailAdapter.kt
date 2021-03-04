package com.example.noteapp.fragment.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.databinding.DetailItemBinding
import com.example.noteapp.model.Customer
import com.example.noteapp.model.CustomerDetail

class CustomerDetailAdapter(val clickListener:DetailListener):ListAdapter<CustomerDetail,CustomerDetailAdapter.ViewHolder>(CustomerDetailAdapter.DiffCallBack() ){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding=DetailItemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding,parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        holder.bind(item,clickListener)
    }



    class ViewHolder(val binding:DetailItemBinding,val parent: ViewGroup):RecyclerView.ViewHolder(binding.root){
        var date=binding.date
        var gaveAmount=binding.gaveAmount
        var getAmount=binding.getAmount
        val detailParent=binding.detailItemParent


        fun bind(
            item: CustomerDetail,
        clickListener: DetailListener
        ) {
            detailParent.animation= AnimationUtils.loadAnimation(parent.context, R.anim.fade_scale_animation)
            binding.customerDetail=item
            binding.clickListener=clickListener
            date.text = item.time
            gaveAmount.text = item.gaveAmount.toString()
            getAmount.text = item.getAmount.toString()
        }
    }

    class DiffCallBack: DiffUtil.ItemCallback<CustomerDetail>() {
        override fun areItemsTheSame(oldItem: CustomerDetail, newItem: CustomerDetail): Boolean {
            return oldItem.customer_id==newItem.customer_id
        }

        override fun areContentsTheSame(oldItem: CustomerDetail, newItem: CustomerDetail): Boolean {
            return oldItem==newItem
        }

    }
}

class DetailListener(val clickListener: (customerDetail: CustomerDetail) -> Unit) {
    fun onClick(customerDetail: CustomerDetail) = clickListener(customerDetail)
}