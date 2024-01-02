package com.example.randomuserlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.randomuserlist.inferfaces.ApiServiceable
import com.example.randomuserlist.inferfaces.RetrofitServiceFactory
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val apiService = RetrofitServiceFactory.makeRetrofitService()

    private val userAdapter = UserAdapter()
    private var page = 1
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.userRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (visibleItemCount + pastVisibleItems >= totalItemCount ) {
                    page++
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
            val response = apiService.getUserByResults(page = page, results = 10)
            userAdapter.addUsers(response.results)
        } catch (e: Exception) {
            println("loadMoreUsers Error ${e}")
        }
    }
}
