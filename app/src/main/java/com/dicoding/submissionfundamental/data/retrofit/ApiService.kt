package com.dicoding.submissionfundamental.data.retrofit

import com.dicoding.submissionfundamental.data.response.DetailUserResponse
import com.dicoding.submissionfundamental.data.response.FollowersResponseItem
import com.dicoding.submissionfundamental.data.response.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getSearch(
        @Query("q") query: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailGithub(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowersGithub(
        @Path("username") username: String
    ): Call<List<FollowersResponseItem>>

    @GET("users/{username}/following")
    fun getFollowingGithub(
        @Path("username") username: String
    ): Call<List<FollowersResponseItem>>
}