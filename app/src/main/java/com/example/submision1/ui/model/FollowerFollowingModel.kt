package com.example.submision1.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submision1.data.response.ResponseFollowersItem
import com.example.submision1.data.retrofit.ApiClient
import com.example.submision1.ui.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFollowingModel  : ViewModel(){

    private val _userName = MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    fun setUserName (username: String){
        _userName.value = username
    }

    private val _detailUser = MutableLiveData<List<ResponseFollowersItem>>()
    val detailUser : LiveData<List<ResponseFollowersItem>> = _detailUser


    private val _detailFollower = MutableLiveData<List<ResponseFollowersItem>>()
    val detailFollower : LiveData<List<ResponseFollowersItem>> = _detailFollower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

     fun findFollowingGithub(username: String) {
        _isLoading.value = (true)
        val client = ApiClient.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ResponseFollowersItem>> {
            override fun onResponse(
                call: Call<List<ResponseFollowersItem>>,
                response: Response<List<ResponseFollowersItem>>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = (false)
                    val responseBody = response.body()
                    if (responseBody != null){
                        _detailUser.value = (responseBody)
                    }
                }else{
                    Log.e(HomeFragment.TAG,"error : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ResponseFollowersItem>>, t: Throwable) {
                _isLoading.value = (true)
                Log.e(HomeFragment.TAG,"onfailure ${t.message}")
            }

        })

    }

    fun findFollowersGithub(username: String) {
        _isLoading.value = (true)
        val client = ApiClient.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ResponseFollowersItem>> {
            override fun onResponse(
                call: Call<List<ResponseFollowersItem>>,
                response: Response<List<ResponseFollowersItem>>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = (false)
                    val responseBody = response.body()
                    if (responseBody != null){
                        _detailFollower.value = (responseBody)
                    }
                }else{
                    Log.e(HomeFragment.TAG,"error : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ResponseFollowersItem>>, t: Throwable) {
                _isLoading.value = (true)
                Log.e(HomeFragment.TAG,"onfailure ${t.message}")
            }

        })

    }


}