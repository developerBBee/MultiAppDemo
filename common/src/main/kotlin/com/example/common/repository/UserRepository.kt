package com.example.common.repository

import com.example.common.data.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor() {
    private val users: MutableList<User> = mutableListOf()

    fun getUsers(): Flow<List<User>> = flow {
        delay(100)
        emit(users)
    }

    fun saveUser(user: User): Flow<Unit> = flow {
        delay(100)
        users.add(user)
        emit(Unit)
    }
}