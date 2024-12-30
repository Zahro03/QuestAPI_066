package com.example.meet13.ui.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.meet13.MahasiswaApplications

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // Inisialisasi untuk HomeViewModel
        initializer {
            HomeViewModel(aplikasiMahasiswa().container.mahasiswaRepository
            )
        }

        // Inisialisasi untuk InsertViewModel
        initializer {
            InsertViewModel(aplikasiMahasiswa().container.mahasiswaRepository
            )
        }

        // Inisialisasi untuk DetailViewModel
        initializer {
            DetailViewModel(createSavedStateHandle(), aplikasiMahasiswa().container.mahasiswaRepository
            )
        }

        // Inisialisasi untuk UpdateViewModel
        initializer {
            UpdateViewModel(createSavedStateHandle(), aplikasiMahasiswa().container.mahasiswaRepository
            )
        }
    }
    }

    // Fungsi untuk mengakses MahasiswaApplications dari CreationExtras
    fun CreationExtras.aplikasiMahasiswa(): MahasiswaApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApplications)
