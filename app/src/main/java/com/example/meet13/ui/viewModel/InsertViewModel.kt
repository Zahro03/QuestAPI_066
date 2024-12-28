package com.example.meet13.ui.viewModel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import com.example.meet13.repository.MahasiswaRepository

class InsertViewModel (private val mhs: MahasiswaRepository): ViewModel(){
    var uiState by mutableIntStateOf(insertUiState())
        private set

}