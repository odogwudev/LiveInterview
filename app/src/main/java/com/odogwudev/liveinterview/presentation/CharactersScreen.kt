package com.odogwudev.liveinterview.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.odogwudev.liveinterview.data.domain.rrom.CharacterRicks

val BG = Color(0xFF081C17)
val Muted = Color(0xFF9AA5A1)
val CYAN = Color(0xFF5CE1E6)
val Panel = Color(0xFF161B1A)
val LIme = Color(0xFFC8F53A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(viewModel: CharactersViewModel, onOpenFavorites: () -> Unit) {

    val items = viewModel.characters.collectAsLazyPagingItems()
    val favIds = viewModel.favoriteIDS.collectAsStateWithLifecycle()
    val selected = viewModel.selected.collectAsStateWithLifecycle().value


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
                }, actions = {
                    IconButton(onOpenFavorites) {
                        Icon(
                            Icons.Filled.Favorite,
                            "Favourites",
                            tint = LIme
                        )
                    }
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                   containerColor = BG
                )
            )
        }) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize(),
        ) {
            Header(
                selected.size,
                items.itemCount,
                onAdd = viewModel::addSelectedToFavorites
            )
            LazyColumn(

                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {

                }
                val refresh = items.loadState.refresh
                if (refresh is LoadState.Loading && items.itemCount == 0) item {
                    Centered {
                        CircularProgressIndicator(
                            color = LIme
                        )
                    }
                }
                if (refresh is LoadState.Error && items.itemCount == 0) item {
                    Centered {
                        TextButton({ items.retry() }) {
                            Text(
                                "FAILED TO LOAD PLEASE RETRY",
                                color = LIme
                            )
                        }
                    }
                }
                items(items.itemCount, key = items.itemKey { it.id }) { i ->
                    items[i]?.let { character ->
                        CharacterCard(
                            characterRicks = character,
                            selected = character.id in selected,
                            favorite = character.id in favIds.value,
                            onClick = {if(selected.isNotEmpty()) viewModel.toggleSelect(character)},
                            onLongClick = { viewModel.toggleSelect(character) },
                            onFav = { viewModel.toggleFavorite(character) }
                        )
                    }
                }

                when (items.loadState.append) {
                    is LoadState.Loading -> item { Centered { CircularProgressIndicator(color = LIme) } }
                    is LoadState.Error -> item {
                        Centered {
                            TextButton({ items.retry() }) {
                                Text(
                                    "FAILED TO LOAD PLEASE RETRY",
                                    color = LIme
                                )
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }
    }
}


@Composable
private fun Header(selectedCount: Int, total: Int, onAdd: () -> Unit) {

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("EXPERIMENTAL DATA LOG", color = LIme)
        Text("SUBJECTED ARCHIVE_01", color = Color.White)
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.background(Panel).padding(horizontal = 12.dp, vertical = 8.dp)) {
                Text("SELECTED, ENTITIES", color = Muted, fontFamily = FontFamily.Monospace, fontSize = 10.sp)
                Text("$selectedCount / $total", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 20.sp)
            }
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = LIme, contentColor = Color.Black),
                onClick = onAdd, enabled = selectedCount > 0, shape = RoundedCornerShape(2.dp)
            ) {
                Text("ADD TO FAVORITES", fontFamily = FontFamily.Monospace, fontSize = 12.sp)
            }
        }
    }
}


@Composable
  fun CharacterCard(
    characterRicks: CharacterRicks,
    selected: Boolean,
    favorite: Boolean,
    onClick: () -> Unit,
    onFav: () -> Unit,
    onLongClick: () -> Unit = {}
) {

    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .border(
                if (selected) 2.dp else 0.dp,
                if (selected) LIme else Color.Transparent,
                RoundedCornerShape(4.dp)
            )
            .combinedClickable(
                onClick = onClick, onLongClick = onLongClick
            )
    ) {
        Box {
            AsyncImage(
                characterRicks.image,
                characterRicks.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4F / 3f)
            )
            IconButton(
                onFav,
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(36.dp)
                    .background(Color.Black.copy(.2f))
            ) {
                Icon(
                    if (favorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    "Toggle Favorite",
                    tint = if (favorite) LIme else Color.White
                )
            }
        }
        Column(
            Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                characterRicks.name,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            DetailRow("STATUS", characterRicks.status, statusColor(characterRicks.status))
            DetailRow("SPECIES", "${characterRicks.species} / ${characterRicks.gender}", Color.White)
            StackedColumn("LAST KNOWN LOCATION", characterRicks.origin, Color.White)
            StackedColumn("CURRENT LOCATION", characterRicks.location, CYAN)
        }
    }
}


@Composable
  fun DetailRow(label: String, value: String, valueColor: Color) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Muted, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
        Text(value, color = valueColor, fontSize = 13.sp)
    }
}


@Composable
  fun StackedColumn(label: String, value: String, valueColor: Color) {
    Column() {
        Text(label, color = Muted, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
        Text(value, color = valueColor, fontSize = 13.sp)
    }
}

  fun statusColor(s: String) = when (s) {
    "Alive" -> LIme
    "Dead" -> Color(0XFFFF5252)
    else -> Color.Gray
}

@Composable
  fun Centered(content: @Composable () -> Unit) {
    Box(Modifier
        .fillMaxWidth()
        .padding(24.dp), contentAlignment = Alignment.Center){content()}
}