package com.example.submision1.ui.detail

import ResponseDetailUsersGithub
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.example.submision1.database.Favorite
import com.example.submision1.databinding.ActivityDetailBinding
import com.example.submision1.helper.ViewModelFactory
import com.example.submision1.ui.detail.insert.FavoriteAddUpdateViewModel
import com.example.submision1.ui.model.DetailModel
import com.example.submision1.ui.model.FollowerFollowingModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

open class DetailActivity : AppCompatActivity() {
    private var _activityDetailBinding : ActivityDetailBinding? = null
    private  val binding get() = _activityDetailBinding
    private lateinit var detailModel : DetailModel
    val followerFollowingModel by viewModels<FollowerFollowingModel>()
    //    hubungkan viewmodel
    private lateinit var favoriteAddUpdateViewModel: FavoriteAddUpdateViewModel



    companion object{
       const val USER_NAME = "user_name"
        const val EXTRA_FAVORITE = "extra_favorite"
        const val AVATAR_URL = "avatar_url"


        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.fllwrs,
            R.string.fllwing
        )
    }
    private val isExist = false
    private var favorite : Favorite? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        favoriteAddUpdateViewModel = obtainViewModel(this@DetailActivity)



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


        favorite = Favorite()

        binding?.fabb?.setOnClickListener{


            favorite.let {favorite ->

             favorite?.login = username?.login.toString()
             favorite?.avatarUrl = username?.avatarUrl.toString()

            }
            favoriteAddUpdateViewModel.insert(favorite as Favorite)
            Toast.makeText(this, getString(R.string.message_ok), Toast.LENGTH_SHORT).show()

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory).get(FavoriteAddUpdateViewModel::class.java)
    }


//    menemukan detail user

//    menemukan detail user


    //    Set detail user
    private fun setDetailUser(detailUser : ResponseDetailUsersGithub){
        binding?.imageDetail?.load(detailUser.avatarUrl){
            transformations(CircleCropTransformation())
        }
        binding?.tvUserName?.text = detailUser.login
        binding?.tvNama?.text = detailUser.name ?: "Nama tidak di set"
        binding?.tvFollower?.text = resources.getString(R.string.followers,detailUser.followers.toString())
        binding?.tvFollowing?.text = resources.getString(R.string.following_n,detailUser.following.toString())




    }
    //    Set detail user

    private fun showLoading(isLoading : Boolean){
        if (isLoading == true){
            binding?.progressBar?.visibility = View.VISIBLE
        }else{
            binding?.progressBar?.visibility = View.GONE
        }
    }
}