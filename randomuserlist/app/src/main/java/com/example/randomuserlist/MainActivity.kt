package com.example.randomuserlist
import android.view.Menu
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.randomuserlist.databinding.ActivityMainBinding
import com.example.randomuserlist.models.User
import com.example.randomuserlist.ui.UserAdapter
import com.example.randomuserlist.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        title = ""
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userAdapter = UserAdapter(this)

        binding.userRecycler.layoutManager = LinearLayoutManager(this)
        binding.userRecycler.adapter = userAdapter
        binding.filterLine.visibility = GONE
        binding.userRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                    userViewModel.loadMoreUsers()
                }
            }
        })

        userViewModel.userList.observe(this, Observer { users ->
            userAdapter.addUsers(users)
        })

        userViewModel.toastMessage.observe(this, Observer { message ->
            showToast(this, message)
        })

        userViewModel.showToastBasedOnInternetConnection(this)

        lifecycleScope.launch {
            userViewModel.loadMoreUsers()
        }

        binding.arrowIcon.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        binding.close.setOnClickListener{
            binding.filterLine.visibility = GONE
            userAdapter.resetFilters()
        }

        binding.byEmail.setOnClickListener {
            val usersByEmail: List<User> = userAdapter.users.filter { user->
                user.email.uppercase(Locale.ROOT).equals(binding.textFilter.text.toString()
                    .uppercase(Locale.ROOT))
            }
            userAdapter.updatesUsers(usersByEmail)
            binding.textFilter.text
        }

        binding.byName.setOnClickListener {
            val usersByName: List<User> = userAdapter.users.filter { user->
                user.name.first.uppercase(Locale.ROOT).equals(binding.textFilter.text.toString()
                    .uppercase(Locale.ROOT))
            }
            userAdapter.updatesUsers(usersByName)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_filter_users, menu)
        val itemMenu = menu?.findItem(R.id.filter)
        itemMenu?.setOnMenuItemClickListener {
            binding.filterLine.visibility = VISIBLE
            return@setOnMenuItemClickListener true
        }
        return true
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
