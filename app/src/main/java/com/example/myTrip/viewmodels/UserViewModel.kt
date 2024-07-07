package com.example.myTrip.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myTrip.dao.UserDao
import com.example.myTrip.database.AppDatabase
import com.example.myTrip.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModelFactory(val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return UserViewModel(db.userDao) as T;
    }
}

class UserViewModel(val userDao: UserDao) : ViewModel() {
    private val _uiState = MutableStateFlow(User())
    val uiState: StateFlow<User> = _uiState.asStateFlow()

    private fun updateId(id: Long) {
        _uiState.update {
            it.copy(id = id)
        }
    }

    fun updateUsername(newUsername: String) {
        _uiState.update {
            it.copy(username = newUsername)
        }
    }

    fun updatePassword(newPassword: String) {
        _uiState.update {
            it.copy(password = newPassword)
        }
    }

    fun updateEmail(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail)
        }
    }

    private fun new() {
        if (uiState.value.isEmpty()) return
        _uiState.update {
            it.copy(id = 0, username = "", email = "", password = "")
        }
    }

    fun save() {
        if (uiState.value.isEmpty()) return
        viewModelScope.launch {
            val id = userDao.upsert(uiState.value)
            if (id > 0) {
                updateId(id)
            }
        }
    }

    fun saveNew() {
        save()
        new()
    }

    fun getAll() = userDao.getAll()

    fun findById(userId: Long) {
        viewModelScope.launch {
            val user = userDao.findById(userId)
            if (user != null) {
                updateId(user.id)
                updateUsername(user.username)
                updateEmail(user.email)
                updatePassword(user.password)
            }
        }
    }
}