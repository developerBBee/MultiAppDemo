package com.example.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.data.User
import com.example.common.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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
    val uiStateFlow: Flow<UiState> = _uiStateFlow.asStateFlow()

    fun getUser(index: Int) = userRepository.getUser(index = index)
        .onStart { _uiStateFlow.value = UiState(isLoading = true) }
        .onEach { user -> _uiStateFlow.value = UiState(user = user) }
        .catch { e -> _uiStateFlow.value = UiState(error = e.message ?: "Unknown error") }
        .launchIn(viewModelScope)

    fun saveUser(name: String, email: String) = userRepository.saveUser(User(name, email))
        .onStart { _uiStateFlow.value = _uiStateFlow.value.copy(isLoading = true) }
        .onEach { _ -> _uiStateFlow.value = _uiStateFlow.value.copy(isLoading = false) }
        .catch { e ->
            _uiStateFlow.value = _uiStateFlow.value.copy(error = e.message ?: "Unknown error")
        }
        .launchIn(viewModelScope)
}

data class UiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val user: User? = null
)