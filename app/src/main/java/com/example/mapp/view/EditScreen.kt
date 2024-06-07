package com.example.mapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ResetTv
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mapp.MyDrawer
import com.example.mapp.Routes
import com.example.mapp.model.Category
import com.example.mapp.model.Marker
import com.example.mapp.viewmodel.MapViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EditMarkerScreen(navigationController: NavHostController, mapViewModel: MapViewModel) {
    // Observa el marcador que se está editando
    val marker by mapViewModel.editingMarkers.observeAsState()

    // Inicializa los valores del título, el fragmento y la categoría del marcador editado
    mapViewModel.modificarEditedTitle(marker!!.title)
    mapViewModel.modificarEditedDescription(marker!!.description)
    mapViewModel.modificarCategoryName(marker!!.category.name)

    // Observa el estado del menú desplegable y la lista de categorías
    val textoDropdown: String by mapViewModel.textoDropdown.observeAsState("Mostrar Todos")
    val categories: List<Category> by mapViewModel.categories.observeAsState(emptyList())

    // Verifica si el usuario está conectado
    if (!mapViewModel.userLogged()) {
        mapViewModel.signOut(context = LocalContext.current, navigationController)
    }

    // Muestra la interfaz de usuario principal
    MyDrawer(navController = navigationController, mapViewModel = mapViewModel) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box {
                // Menú desplegable para seleccionar la categoría del marcador
                OutlinedTextField(
                    value = textoDropdown,
                    onValueChange = {},
                    enabled = false,
                    readOnly = true,
                    modifier = Modifier
                        .clickable { mapViewModel.modifyExpandedMapa(true) }
                        .fillMaxWidth()
                )

                DropdownMenu(
                    expanded = mapViewModel.pillarExpandedMapa(),
                    onDismissRequest = { mapViewModel.modifyExpandedMapa(false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                ) {
                    categories.forEach { categoria ->
                        DropdownMenuItem(text = { Text(text = categoria.name) }, onClick = {
                            mapViewModel.modificarCategoryName(categoria.name)
                            mapViewModel.modifyExpandedMapa(false)
                            mapViewModel.modificarTextoDropdown(categoria.name)
                        })
                    }
                }
            }

            // Muestra la imagen del marcador o la imagen editada si está disponible
            if (mapViewModel.editedPhoto != null) {
                Image(
                    bitmap = mapViewModel.editedPhoto!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(333.dp)
                        .padding(top = 10.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                GlideImage(
                    model = marker!!.photoReference,
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(330.dp)
                        .alpha(0.50f)
                        .padding(10.dp)
                )
            }

            // Botón para cambiar la foto del marcador
            OutlinedButton(
                onClick = {
                    mapViewModel.modificarShowTakePhotoScreen(true)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Filled.Photo,
                    contentDescription = null,
                    tint = Color(0xFF2196F3)
                )
                Text("  Change", color = Color(0xFF2196F3))
            }
            Spacer(modifier = Modifier.height(10.dp))

            // Campo de texto para editar el título del marcador
            OutlinedTextField(
                value = mapViewModel.editedTitle,
                onValueChange = { mapViewModel.modificarEditedTitle(it) },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Campo de texto para editar el fragmento del marcador
            OutlinedTextField(
                value = mapViewModel.editedDescription,
                onValueChange = { mapViewModel.modificarEditedDescription(it) },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Botones de guardar y eliminar el marcador
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = {
                        marker?.apply {
                            modificarTitle(mapViewModel.editedTitle)
                            modificarDescription(mapViewModel.editedDescription)
                            if (mapViewModel.editedPhoto != null) {
                                modificarPhoto(mapViewModel.editedPhoto!!)
                            }
                            modificarCategoria(mapViewModel.editedCategoryName)
                            mapViewModel.updateMarker(this)
                        }
                        navigationController.navigate(Routes.ListMarkersScreen.route)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .width(140.dp)
                ) {
                    Icon(
                        Icons.Filled.Save,
                        contentDescription = null,
                        tint = Color(0xFF2196F3)
                    )
                    Text(text = "  Save", color = Color(0xFF2196F3))
                }
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedButton(
                    onClick = {
                        mapViewModel.modificarShowDialog(true)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .width(140.dp)
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = null,
                        tint = Color(0xFF2196F3)
                    )
                    Text(text = "  Delete", color = Color(0xFF2196F3))
                }
            }
        }

        // Muestra la pantalla de toma de foto si es necesario
        if (mapViewModel.showTakePhotoScreen) {
            TakePhotoScreen(
                mapViewModel = mapViewModel
            ) { photo ->
                mapViewModel.modificarEditedPhoto(photo)
                mapViewModel.modificarShowTakePhotoScreen(false)
            }
        }

        // Muestra el diálogo de confirmación para eliminar el marcador
        marker?.let {
            MyDialogConfirmErase(
                navigationController,
                it,
                mapViewModel,
                mapViewModel.showDialog
            ) { mapViewModel.modificarShowDialog(false) }
        }
    }
}

@Composable
fun MyDialogConfirmErase(
    navigationController: NavController,
    marker: Marker,
    mapViewModel: MapViewModel,
    show: Boolean,
    onDismiss: () -> Unit
) {
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.White)
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 13.dp),
                ) {
                    // Botón para confirmar la eliminación del marcador
                    OutlinedButton(onClick = {
                        onDismiss()
                        navigationController.navigate(Routes.ListMarkersScreen.route)
                        marker.markerId?.let { mapViewModel.deleteMarker(it) }
                    }) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = null,
                            tint = Color(0xFF2196F3)
                        )
                        Text(text = "  Yes", color = Color(0xFF2196F3))
                    }

                    // Botón para cancelar la eliminación del marcador
                    OutlinedButton(onClick = {
                        onDismiss()
                    }) {
                        Icon(
                            Icons.Outlined.ResetTv,
                            contentDescription = null,
                            tint = Color(0xFF2196F3)
                        )
                        Text(text = "  No", color = Color(0xFF2196F3))
                    }
                }
            }
        }
    }
}