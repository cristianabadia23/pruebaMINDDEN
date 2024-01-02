package com.example.randomuserlist.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.randomuserlist.interfaces.RetrofitServiceFactory
import com.example.randomuserlist.models.User
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = RetrofitServiceFactory.makeRetrofitService()

    val _userSet = MutableLiveData<Set<User>>() // Use your User model
    val userList: LiveData<Set<User>> get() = _userSet

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var page = 1

    fun loadMoreUsers() {
        viewModelScope.launch {
            try {
                val response = apiService.getUserByResults(page = page, results = 10)
                _userSet.value = response.results
            } catch (e: UnknownHostException) {
                _toastMessage.value = "Error al conectarse con la web, vuelva a intentarlo más tarde"
            } catch (e: Exception) {
                println("loadMoreUsers Error ${e}")
                Thread.sleep(200)
                _toastMessage.value= "Intentando conectar con el server"
                loadMoreUsers()
            }
        }
    }

    fun showToastBasedOnInternetConnection(context: Context) {
        val isConnected = isInternetConnected(context)
        _toastMessage.value = if (isConnected) "Connected to the Internet" else "No Internet connection"
    }

    @SuppressLint("ServiceCast")
    private fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }
}
