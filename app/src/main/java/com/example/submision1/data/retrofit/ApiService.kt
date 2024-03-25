package com.example.submision1.data.retrofit

import ResponseDetailUsersGithub

import com.example.submision1.data.response.ResponseFollowersItem
import com.example.submision1.data.response.ResponseUsersGithub
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUserGithub(
        @Query("q") q:String
    ):Call<ResponseUsersGithub>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getUserDetailGithub(
        @Path("username") username:String
    ):Call<ResponseDetailUsersGithub>

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ResponseFollowersItem>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ResponseFollowersItem>>
}