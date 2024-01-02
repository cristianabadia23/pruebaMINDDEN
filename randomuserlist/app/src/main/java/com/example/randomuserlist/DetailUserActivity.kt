package com.example.randomuserlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.randomuserlist.databinding.ActivityDetailUserBinding
import com.google.android.material.snackbar.Snackbar

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}