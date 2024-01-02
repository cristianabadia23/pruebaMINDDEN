package com.example.randomuserlist.ui

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
import com.example.randomuserlist.R

class UserAdapter(context:Context) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    var users = mutableListOf<User>()
    val usersaux = mutableListOf<User>()
    private val context = context
    @SuppressLint("NotifyDataSetChanged")
    fun addUsers(newUsers: Set<User>) {
        users.addAll(newUsers)
        usersaux.addAll(newUsers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatesUsers(users: List<User>){
        this.users = users.toMutableList()
        notifyDataSetChanged()
    }

    fun resetFilters(){
        users = usersaux
        val usersSet = users.toSet()
        users = usersSet.toList().toMutableList()
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

        holder.itemView.setOnClickListener{
            val intent = Intent(context, DetailUserActivity::class.java)
            intent.putExtra("userName", "${user.name.first} ${user.name.last}")
            intent.putExtra("userGender", user.gender)
            intent.putExtra("userDate", user.dob.date)
            intent.putExtra("userPhone", user.phone)
            intent.putExtra("userLocation", "${user.location.coordinates}")
            intent.putExtra("userPictureThumbnail", user.picture.thumbnail)
            intent.putExtra("userEmail", user.email)
            context.startActivity(intent)

        }
        
    }

    override fun getItemCount() = users.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
        val userImageView: ImageView = itemView.findViewById(R.id.userImageView)
    }
}
