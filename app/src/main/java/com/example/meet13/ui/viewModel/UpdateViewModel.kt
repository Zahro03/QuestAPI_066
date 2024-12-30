package com.example.meet13.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meet13.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class UpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val mahasiswaRepository: MahasiswaRepository
) : ViewModel() {
    var updateMhsUiState by mutableStateOf(InsertUiState())
        private set
    val nim: String = checkNotNull(savedStateHandle["nim"])

    init {
        viewModelScope.launch {
            try {
                val mahasiswa = mahasiswaRepository.getMahasiswaByNim(nim)
                updateMhsUiState = mahasiswa.toUiStateMhs()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertMhsState(insertUiEvent: InsertUiEvent) {
        updateMhsUiState = updateMhsUiState.copy(insertUiEvent = insertUiEvent)
    }

    suspend fun updateMhs() {
        try {
            mahasiswaRepository.updateMahasiswa(nim, updateMhsUiState.insertUiEvent.toMhs())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
