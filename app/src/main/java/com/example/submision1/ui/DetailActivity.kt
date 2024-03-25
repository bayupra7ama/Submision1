package com.example.submision1.ui

import ResponseDetailUsersGithub
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import coil.load
import coil.transform.CircleCropTransformation
import com.example.submision1.R
import com.example.submision1.data.response.ItemsItem
import com.example.submision1.databinding.ActivityDetailBinding
import com.example.submision1.ui.model.DetailModel
import com.example.submision1.ui.model.FollowerFollowingModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

open class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailModel : DetailModel
    val followerFollowingModel by viewModels<FollowerFollowingModel>()


    companion object{
       const val USER_NAME = "user_name"


        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.fllwrs,
            R.string.fllwing
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val sectionsPagerAdapter = SectionsPageAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f





        detailModel = ViewModelProvider(this)[DetailModel::class.java]

        detailModel.detailUser.observe(this){
                userList ->
            setDetailUser(userList)
        }
        detailModel.isLoading.observe(this){
            showLoading(it)
        }
        val username = if(Build.VERSION.SDK_INT >=33){
            intent.getParcelableExtra(USER_NAME, ItemsItem::class.java)
        }else{
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(USER_NAME)
        }
        detailModel.setUserName(username?.login.toString())
        followerFollowingModel.setUserName(username?.login.toString())



        // Set username to ViewModel


        detailModel.findDetailUser(username?.login.toString())

        detailModel.detailUser.observe(this){
                userList ->
            setDetailUser(userList)
        }
        detailModel.isLoading.observe(this){
            showLoading(it)
        }

    }



//    menemukan detail user

//    menemukan detail user


    //    Set detail user
    private fun setDetailUser(detailUser : ResponseDetailUsersGithub){
        binding.imageDetail.load(detailUser.avatarUrl){
            transformations(CircleCropTransformation())
        }
        binding.tvUserName.text = detailUser.login
        binding.tvNama.text = detailUser.name ?: "Nama tidak di set"
        binding.tvFollower.text = resources.getString(R.string.followers,detailUser.followers.toString())
        binding.tvFollowing.text = resources.getString(R.string.following_n,detailUser.following.toString())




    }
    //    Set detail user

    private fun showLoading(isLoading : Boolean){
        if (isLoading == true){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}