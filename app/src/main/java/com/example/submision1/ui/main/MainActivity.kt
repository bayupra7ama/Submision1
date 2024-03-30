package com.example.submision1.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submision1.R
import com.example.submision1.data.response.ItemsItem
import com.example.submision1.databinding.ActivityMainBinding
import com.example.submision1.ui.detail.DetailActivity
import com.example.submision1.ui.favorite.FavoriteActivity
import com.example.submision1.ui.model.MainViewModel
import com.example.submision1.ui.setting.SettingActivity


class MainActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {
    private val mainViewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvView.addItemDecoration(itemDecoration)





        mainViewModel.user.observe(this) { userList ->
            setUserGithub(userList)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }


//        menghubungkan searchbar dan search view
        with(binding) {
            searchView.setupWithSearchBar(searchBar)

            searchView
                .editText
                .setOnEditorActionListener { v, actionId, event ->
                    searchBar.setText(searchView.text)
                    val query = searchView.text.toString()
                    mainViewModel.findUserGithub(query)
                    searchView.hide()
                    false
                }
        }


//        menu
        binding.searchBar.inflateMenu(R.menu.main_menu)
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.love_menu -> {
                    val intentFavorite = Intent(this@MainActivity , FavoriteActivity::class.java)
                    startActivity(intentFavorite)
                    true
                }

                R.id.setting -> {
                   val intent = Intent(this@MainActivity , SettingActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }







        private fun showLoading(isLoading: Boolean) {
            if (isLoading) {
                binding.progresBar.visibility = View.VISIBLE

            } else {
                binding.progresBar.visibility = View.GONE
            }
        }

        private fun setUserGithub(UserList: List<ItemsItem>) {
            val adapter = ReviewAdapter()
            adapter.submitList(UserList)
            binding.rvView.adapter = adapter
            adapter.setOnItemClickCallback(object : ReviewAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ItemsItem) {
                    val intentDetail = Intent(this@MainActivity, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.USER_NAME, data)

                    startActivity(intentDetail)
                }

            })
        }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        TODO("Not yet implemented")
    }


}




