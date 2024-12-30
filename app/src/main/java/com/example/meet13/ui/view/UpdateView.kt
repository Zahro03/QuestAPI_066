package com.example.meet13.ui.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meet13.navigation.DestinasiNavigasi
import com.example.meet13.ui.viewModel.PenyediaViewModel

object DestinasiUpdate : DestinasiNavigasi{
    override val route = "Update_mhs"
    const val titleRes = "Edit Mahasiswa"
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateMhsScreen(
    navigateBack:() -> Unit,
    onNavigateUp:() -> Unit,
    modifier: Modifier = Modifier,
    updateViewModel: UpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
){

}