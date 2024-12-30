package com.example.meet13.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meet13.costumWidget.CostumeTopAppBar
import com.example.meet13.model.Mahasiswa
import com.example.meet13.navigation.DestinasiNavigasi
import com.example.meet13.ui.viewModel.DetailMhsUiState
import com.example.meet13.ui.viewModel.DetailViewModel
import com.example.meet13.ui.viewModel.PenyediaViewModel
import com.example.meet13.ui.viewModel.toMhs

object DestinasiDetail : DestinasiNavigasi {
    override val route = "Detail_mhs"
    override val titleRes = "Detail Mahasiswa"
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMhsScreen(
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    detailViewModel.getMahasiswaByNim()
                }
            )
        },
    )
    { innerPadding ->
        DetailStatus(
            mhsUiState = detailViewModel.detailMhsUiState,
            retryAction = {detailViewModel.getMahasiswaByNim() },
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize(),
            onEditClick = onEditClick

        )
    }
}

@Composable
fun DetailStatus(
    mhsUiState: DetailMhsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
){
    when(mhsUiState){
        is DetailMhsUiState.Success -> {
            DetailMhsLayout(
                mahasiswa = mhsUiState.mahasiswa,
                modifier = modifier,
                onEditClick = {onEditClick(it)}
            )
        }
        is DetailMhsUiState.Loading -> OnLoading(modifier = modifier)
        is DetailMhsUiState.Error -> OnError(retryAction, modifier = modifier)
    }
}

@Composable
fun DetailMhsLayout(
    mahasiswa: Mahasiswa,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Membungkus seluruh detail mahasiswa dengan Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEEEEEE)) // Warna abu-abu terang
                .clip(RoundedCornerShape(8.dp))
                .padding(16.dp) // Padding di dalam Box
        ) {
            // Komponen Detail Mahasiswa
            ItemDetailMhs(
                mahasiswa = mahasiswa,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp)) // Spacer setelah detail

        // Tombol Edit
        Button(
            onClick = { onEditClick(mahasiswa.nim) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF424242) // Warna abu-abu gelap
            )
        ) {
            Text(text = "Edit", fontSize = 16.sp, color = Color.White)
        }
    }
}

@Composable
fun ItemDetailMhs(
    modifier: Modifier = Modifier,
    mahasiswa: Mahasiswa
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        ComponentDetailMhs(judul = "Nama", isinya = mahasiswa.nama)
        Spacer(modifier = Modifier.height(12.dp)) // Spacer between each detail
        ComponentDetailMhs(judul = "NIM", isinya = mahasiswa.nim)
        Spacer(modifier = Modifier.height(12.dp))
        ComponentDetailMhs(judul = "Jenis Kelamin", isinya = mahasiswa.jenisKelamin)
        Spacer(modifier = Modifier.height(12.dp))
        ComponentDetailMhs(judul = "Alamat", isinya = mahasiswa.alamat)
        Spacer(modifier = Modifier.height(12.dp))
        ComponentDetailMhs(judul = "Kelas", isinya = mahasiswa.kelas)
        Spacer(modifier = Modifier.height(12.dp))
        ComponentDetailMhs(judul = "Angkatan", isinya = mahasiswa.angkatan)
    }
}

@Composable
fun ComponentDetailMhs(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$judul: ",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

