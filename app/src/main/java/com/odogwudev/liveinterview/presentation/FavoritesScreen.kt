package com.odogwudev.liveinterview.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(viewModel: CharactersViewModel, onGoBack: () -> Unit) {
    val favs by viewModel.favorites.collectAsStateWithLifecycle()
    Scaffold(
        containerColor = BG, topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "ricky and morty live",
                        color = LIme,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                }, navigationIcon = {
                    IconButton(onGoBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "Back",
                            tint = LIme
                        )
                    }
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = BG
                )
            )
        }) { pad ->
        LazyColumn(
            Modifier
                .padding(pad)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("REGISTRY // ARCHIVE", color = CYAN, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
                    Row{
                        Text("SAVED", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 34.sp, fontWeight = FontWeight.Bold)
                        Text("VARIANTS", color = LIme, fontFamily = FontFamily.Monospace, fontSize = 34.sp,fontWeight = FontWeight.Bold)
                    }
                }
            }
            if (favs.isEmpty()){
                item { Centered{Text("NO SUBJECTS SAVED", color = Muted, fontFamily = FontFamily.Monospace)} }
            }
            items(favs.size, key =  {favs[it].id}) { i->
                val c = favs[i]
                CharacterCard(c, selected = false, favorite = true, onClick = {}, onFav = {viewModel.toggleFavorite(c)})
            }
        }
    }
}