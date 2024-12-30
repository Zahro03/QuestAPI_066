package com.example.meet13.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meet13.model.Mahasiswa
import com.example.meet13.repository.MahasiswaRepository
import com.example.meet13.ui.view.DestinasiDetail
import kotlinx.coroutines.launch

// Kelas sealed untuk merepresentasikan berbagai status UI pada DetailMhs
sealed class DetailMhsUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailMhsUiState()
    object Error : DetailMhsUiState()
    object Loading : DetailMhsUiState()
}

// ViewModel untuk mengelola data dan status UI pada DetailMhs
class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val mahasiswaRepository: MahasiswaRepository
) : ViewModel() {
    private val nim: String = checkNotNull(savedStateHandle["nim"])
    var detailMhsUiState: DetailMhsUiState by mutableStateOf(DetailMhsUiState.Loading)
        private set

    init {
        getMahasiswaByNim()
    }

    // Fungsi untuk mengambil data mahasiswa berdasarkan NIM
    fun getMahasiswaByNim() {
        viewModelScope.launch {
            detailMhsUiState = DetailMhsUiState.Loading
            detailMhsUiState = try {
                DetailMhsUiState.Success(mahasiswaRepository.getMahasiswaByNim(nim))
            } catch (e: Exception) {
                DetailMhsUiState.Error
            }
        }

    }

}