package com.example.randomuserlist.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.randomuserlist.R
import com.example.randomuserlist.databinding.ActivityDetailUserBinding
import java.text.SimpleDateFormat
import java.util.Locale

class DetailUserActivity : AppCompatActivity(){//, OnMapReadyCallback {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var userLocation:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        title=""
        val userName = intent.getStringExtra("userName")
        val userEmail = intent.getStringExtra("userEmail")
        val userGender = intent.getStringExtra("userGender")
        val userDate = intent.getStringExtra("userDate")
        val userPhone = intent.getStringExtra("userPhone")
        userLocation = intent.getStringExtra("userLocation").toString()
        println(userLocation)
        val userPictureThumbnail = intent.getStringExtra("userPictureThumbnail")
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarTitle.text = userName
        binding.scrollable.phoneTextView?.setText(userPhone)
        binding.scrollable.nameUserTextView?.setText(userName)
        binding.scrollable.emailTextView?.setText(userEmail)
        binding.scrollable.genderTextView?.setText(userGender)
        binding.scrollable.dateTextView?.setText(userDate?.let { formatDate(it) })
        binding.scrollable.phoneTextView?.setText(userPhone)
        binding.scrollable.userPictureImageView?.let {
            Glide.with(this)
                .load(userPictureThumbnail)
                .transform(CircleCrop())
                .into(it)
        }
        binding.arrowIcon.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
        if(userGender.toString().uppercase(Locale.ROOT).equals("MALE")){
            binding.scrollable.genderImageView?.setImageResource(R.drawable.man)
            binding.toolbarLayout.setBackgroundResource(R.drawable.backgroundman)
        }
    }

    fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

        try {
            val date = inputFormat.parse(dateString)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "Invalid date"
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user_detail, menu)
        return true
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show()
            }
            R.id.shareUser -> {
                Toast.makeText(this, "share User", Toast.LENGTH_LONG).show()
            }
            R.id.deleteUser -> {
                Toast.makeText(this, "Delete User", Toast.LENGTH_LONG).show()
            }
        }
        return true
    }
}