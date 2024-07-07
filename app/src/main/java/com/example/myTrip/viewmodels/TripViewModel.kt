package com.example.myTrip.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myTrip.dao.TripDao
import com.example.myTrip.database.AppDatabase
import com.example.myTrip.models.Trip
import com.example.myTrip.models.TripType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TripViewModelFactory(val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return TripViewModel(db.tripDao) as T;
    }
}

class TripViewModel(val tripDao: TripDao) : ViewModel() {
    private val _uiState = MutableStateFlow(Trip())
    val uiState: StateFlow<Trip> = _uiState.asStateFlow()

    fun updateId(id: Long) {
        _uiState.update {
            it.copy(id = id)
        }
    }

    fun updateSource(newSource: String) {
        _uiState.update {
            it.copy(source = newSource)
        }
    }

    fun updateStartDate(newStartDate: String) {
        _uiState.update {
            it.copy(startDate = newStartDate)
        }
    }

    fun updateEndDate(newEndDate: String) {
        _uiState.update {
            it.copy(endDate = newEndDate)
        }
    }

    fun updateBudget(newBudget: Double) {
        _uiState.update {
            it.copy(budget = newBudget)
        }
    }

    fun updateType(newType: TripType) {
        _uiState.update {
            it.copy(type = newType)
        }
    }

    private fun new() {
        if (uiState.value.isEmpty()) return
        _uiState.update {
            it.copy(
                id = 0,
                source = "",
                startDate = "",
                endDate = "",
                budget = 0.0,
                type = TripType.LEISURE
            );
        }
    }

    fun save() {
        if (uiState.value.isEmpty()) return
        viewModelScope.launch {
            val id = tripDao.upsert(uiState.value)
            if (id > 0) {
                updateId(id)
            }
        }
    }

    fun saveNew() {
        save()
        new()
    }

    fun delete(trip: Trip) {
        viewModelScope.launch {
            tripDao.delete(trip)
        }
    }

    fun getAll() = tripDao.getAll()

    fun findById(userId: Long) {
        viewModelScope.launch {
            val trip = tripDao.findById(userId)
            if (trip != null) {
                updateId(trip.id)
                updateSource(trip.source)
                updateStartDate(trip.startDate)
                updateEndDate(trip.endDate)
                updateBudget(trip.budget)
                updateType(trip.type)
            }
        }
    }
}