package com.example.finin.utils

import com.example.finin.model.UserResponse
import com.example.finin.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject


interface GetUsersData {
    fun getUsersData(page: Int, delay: Int): Single<UserResponse>
}

class GetUserDataImpl @Inject constructor(private val userRepository: UserRepository) :
    GetUsersData {

    override fun getUsersData(page: Int, delay: Int): Single<UserResponse> =
        userRepository.getUsersData(page, delay)


}