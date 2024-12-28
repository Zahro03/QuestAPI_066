package com.example.meet13.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.meet13.model.Mahasiswa
import com.example.meet13.repository.MahasiswaRepository

sealed class HomeUiState{
    data class Success(val mahasiswa: List<Mahasiswa>): HomeUiState()
    object Error:HomeUiState()
    object Loading:HomeUiState()
}

class HomeViewModel(private val mhs:MahasiswaRepository):ViewModel(){
    var mhsUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMhs
    }
}