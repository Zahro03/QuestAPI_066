package com.example.meet13.ui.view

import com.example.meet13.navigation.DestinasiNavigasi

object DestinasiUpdate : DestinasiNavigasi{
    override val route = "Update_mhs"
    const val titleRes = "Edit Mahasiswa"
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}