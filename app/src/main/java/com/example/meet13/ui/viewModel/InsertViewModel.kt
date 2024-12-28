package com.example.meet13.ui.viewModel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meet13.model.Mahasiswa
import com.example.meet13.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class InsertViewModel (private val mhs: MahasiswaRepository): ViewModel(){
    var uiState by mutableIntStateOf(insertUiState())
        private set

    fun updateInsertMhsState(insertUiEvent:InsertUiEvent){
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun  insertMhs(){
        viewModelScope.launch {
            try {
                mhs.insertMahasiswa(uiState.insertUiEvent.toMhs())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val nim: String ="",
    val nama: String ="",
    val alamat: String ="",
    val jenisKelamin: String ="",
    val kelas: String ="",
    val angkatan: String =""
)

fun InsertUiEvent.toMhs():Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    alamat = alamat,
    jenisKelamin = jenisKelamin,
    kelas = kelas,
    angkatan = angkatan
)

fun Mahasiswa.toUiStateMhs(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent
)