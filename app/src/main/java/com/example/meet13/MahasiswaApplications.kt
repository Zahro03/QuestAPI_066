package com.example.meet13

import android.app.Application
import com.example.meet13.repository.AppContainer
import com.example.meet13.repository.MahasiswaContainer

class MahasiswaApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = MahasiswaContainer()
    }
}