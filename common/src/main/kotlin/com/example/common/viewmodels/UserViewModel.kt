package com.example.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.data.User
import com.example.common.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiStateFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiStateFlow: StateFlow<UiState> = _uiStateFlow.asStateFlow()

    fun getUsers() = userRepository.getUsers()
        .onStart { _uiStateFlow.setCopyValue(isLoading = true) }
        .onEach { users ->
            _uiStateFlow.setCopyValue(isLoading = false, users = users)
        }
        .catch { e ->
            _uiStateFlow.setCopyValue(isLoading = false, error = e.message ?: "Unknown error")
        }
        .launchIn(viewModelScope)

    fun saveUser(name: String, email: String) = userRepository.saveUser(User(name, email))
        .onStart { _uiStateFlow.setCopyValue(isLoading = true) }
        .onEach { getUsers() }
        .catch { e ->
            _uiStateFlow.setCopyValue(isLoading = false, error = e.message ?: "Unknown error")
        }
        .launchIn(viewModelScope)

    fun clearError() {
        _uiStateFlow.setCopyValue(error = "")
    }
}

data class UiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val users: List<User> = emptyList()
)

private fun MutableStateFlow<UiState>.setCopyValue(
    isLoading: Boolean = value.isLoading,
    error: String = value.error,
    users: List<User> = value.users
) {
    value = value.copy(isLoading = isLoading, error = error, users = users)
}