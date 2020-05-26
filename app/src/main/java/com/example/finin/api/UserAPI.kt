package com.example.finin.api

import com.example.finin.model.UserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserAPI {

    @GET("users")
    fun getUsersData(
        @Query("page") page: Int,
        @Query("delay") delay: Int
    ): Single<UserResponse>
}