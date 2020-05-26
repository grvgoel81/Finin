package com.example.finin.repository

import com.example.finin.api.UserAPI
import com.example.finin.model.UserResponse
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val usersApi: UserAPI) :
    UserRepository {
    override fun getUsersData(page: Int, delay: Int): Single<UserResponse> {
        return usersApi.getUsersData(page, delay)
    }
}