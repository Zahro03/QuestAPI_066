package com.example.meet13.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.meet13.model.Mahasiswa
import com.example.meet13.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val mahasiswa: List<Mahasiswa>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModel(private val mhs: MahasiswaRepository) : ViewModel() {
    var mhsUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMhs()
    }

    // Mendapatkan data mahasiswa
    fun getMhs() {
        viewModelScope.launch {
            mhsUIState = HomeUiState.Loading
            mhsUIState = try {
                HomeUiState.Success(mhs.getMahasiswa())  // Mendapatkan daftar mahasiswa
            } catch (e: IOException) {
                Log.e("HomeViewModel", "Kesalahan jaringan", e)
                HomeUiState.Error  // Menangani error jaringan
            } catch (e: HttpException) {
                Log.e("HomeViewModel", "Kesalahan HTTP", e)
                HomeUiState.Error  // Menangani error HTTP
            }
        }
    }

    // Menghapus mahasiswa berdasarkan NIM
    fun deleteMhs(nim: String) {
        viewModelScope.launch {
            try {
                mhs.deleteMahasiswa(nim)  // Memanggil metode delete di repository
                getMhs()  // Refresh data setelah penghapusan
            } catch (e: IOException) {
                Log.e("HomeViewModel", "Terjadi kesalahan jaringan saat menghapus mahasiswa", e)
                mhsUIState = HomeUiState.Error  // Setel status error ketika terjadi exception
            } catch (e: HttpException) {
                Log.e("HomeViewModel", "Terjadi kesalahan HTTP saat menghapus mahasiswa", e)
                mhsUIState = HomeUiState.Error  // Setel status error ketika terjadi HttpException
            }
        }
    }
}
