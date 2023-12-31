package com.example.randomuserlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.randomuserlist.inferfaces.ApiServiceable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call

class MainActivity : AppCompatActivity() {

    private val apiService = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiServiceable::class.java)

    private val userAdapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {

        lifecycleScope.launch {
            val response = apiService.getUsers()
            userAdapter.addUsers(response.results)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.userRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (lastVisibleItemPosition + 1 == totalItemCount) {
                    lifecycleScope.launch {
                        loadMoreUsers()
                    }
                }
            }
        })
        lifecycleScope.launch {
            loadMoreUsers()
        }
    }

    private suspend fun loadMoreUsers() {
        try {
            val response = apiService.getUserbyResults(10)
            userAdapter.addUsers(response.results)
            for (user in response.results){
                println("user = ${user.name.first}")
            }
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity,"Error al cargar user", Toast.LENGTH_LONG).show()
        }
    }
}
