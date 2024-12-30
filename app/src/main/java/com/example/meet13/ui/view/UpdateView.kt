package com.example.meet13.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meet13.costumWidget.CostumeTopAppBar
import com.example.meet13.navigation.DestinasiNavigasi
import com.example.meet13.ui.viewModel.PenyediaViewModel
import com.example.meet13.ui.viewModel.UpdateViewModel
import kotlinx.coroutines.launch

object DestinasiUpdate : DestinasiNavigasi{
    override val route = "Update_mhs"
    override val titleRes= "Edit Mahasiswa"
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
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = "Update Mahasiswa",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        EntryBody(
            insertUiState = updateViewModel.updateMhsUiState,
            onSiswaValueChange = updateViewModel::updateInsertMhsState,
            onSaveClick = {
                coroutineScope.launch {
                    updateViewModel.updateMhs()
                    onNavigateUp()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}