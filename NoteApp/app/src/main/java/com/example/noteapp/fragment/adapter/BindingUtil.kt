package com.example.noteapp.fragment.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.noteapp.R
import com.example.noteapp.model.Customer


@BindingAdapter("namePrefix")
fun TextView.setNamePrefix(customer:Customer){
    val name=customer.name.substring(0,2)
    text=name.toUpperCase()
}

@BindingAdapter("pricefix")
fun TextView.setPricefix(customer: Customer){
    val currentAmount=customer.currentAmount.toString().toDouble()
    if (currentAmount > 0.0) {
        text=currentAmount.toString()
        setTextColor(resources.getColor(R.color.red))
        //binding.priceText.setText("${it.toString()} expected")
        //binding.priceText.setTextColor(resources.getColor(R.color.red))
    } else if (currentAmount == 0.0) {
        text=currentAmount.toString()
        setTextColor(resources.getColor(R.color.green))
        //binding.priceText.setText("${it.toString()} more")
        //binding.priceText.setTextColor(resources.getColor(R.color.green))
    } else if (currentAmount < 0.0) {
        text=(-currentAmount).toString()
        setTextColor(resources.getColor(R.color.green))
       // binding.priceText.setText("${(-it).toString()} more")
        //binding.priceText.setTextColor(resources.getColor(R.color.green))
    }
}