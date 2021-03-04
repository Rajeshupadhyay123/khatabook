package com.example.noteapp.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.databinding.ListItemBinding
import com.example.noteapp.model.User

class UserAdapter(val clickListener:UserListener) : ListAdapter<User, UserAdapter.ViewHolder>(UserAdapter.UserDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener)
    }

    fun getPositionAt(position: Int):User{
        val item=getItem(position)
        return item!!
    }

    class ViewHolder(val binding: ListItemBinding,val parent: ViewGroup) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvNoteTitle
        val body = binding.tvNoteBody
        val card=binding.cardView
        val time=binding.time

        //it is for animation
        val listContainer=binding.cardView

        fun bind(
            item: User,
            clickListener: UserListener
        ) {
            //it is for animation on item show
            listContainer.animation=AnimationUtils.loadAnimation(parent.context, R.anim.fade_scale_animation)

            binding.user=item
            binding.clickListener=clickListener
            title.text = item.title
            body.text = item.description
            time.text=item.time
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding,parent)
            }
        }
    }


    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.userId==newItem.userId
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem==newItem
        }
    }


}

class UserListener(val clickListener: (user: User) -> Unit) {
    fun onClick(user: User) = clickListener(user)
}