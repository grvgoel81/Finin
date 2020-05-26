package com.example.finin.repository

import com.example.finin.model.UserResponse
import io.reactivex.Single

interface UserRepository {
    fun getUsersData(page: Int, delay: Int): Single<UserResponse>
}