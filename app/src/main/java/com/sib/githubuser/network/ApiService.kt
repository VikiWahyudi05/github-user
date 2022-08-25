package com.sib.githubuser.network

import com.sib.githubuser.model.DetailUserResponse
import com.sib.githubuser.model.User
import com.sib.githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>


}