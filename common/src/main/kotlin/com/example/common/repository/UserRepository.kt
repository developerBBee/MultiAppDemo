package com.example.common.repository

import com.example.common.data.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor() {
    private val users: MutableList<User> = mutableListOf()

    fun getUser(index: Int): Flow<User> = flow {
        delay(100)
        users.getOrNull(index)?.let { emit(it) }
            ?: throw IllegalArgumentException("User not found")
    }

    fun saveUser(user: User): Flow<Unit> = flow {
        delay(100)
        users.add(user)
    }
}