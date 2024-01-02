package com.example.randomuserlist

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.randomuserlist.models.User
import android.content.Context

class UserAdapter(context:Context) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val users = mutableListOf<User>()
    private val context = context
    @SuppressLint("NotifyDataSetChanged")
    fun addUsers(newUsers: Set<User>) {
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.nameTextView.text = "${user.name.first} ${user.name.last}"
        holder.emailTextView.text = user.email

        Glide.with(holder.itemView.context)
            .load(user.picture.thumbnail)
            .transform(CircleCrop())
            .into(holder.userImageView)
        
    }

    override fun getItemCount() = users.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
        val userImageView: ImageView = itemView.findViewById(R.id.userImageView)
    }
}
