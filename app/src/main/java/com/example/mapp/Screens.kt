package com.example.mapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String, val title: String) {

    sealed class DrawerScreens(
        route: String,
        val icon: ImageVector,
        title: String
    ) : Screens(route, title) {

        object Mapa : DrawerScreens(
            Routes.MapScreen.route,
            Icons.Filled.Home,
            "Map"
        )

        object Listar : DrawerScreens(
            Routes.ListMarkersScreen.route,
            Icons.Filled.List,
            "Markers"
        )

        object ProfileScreen : DrawerScreens(
            Routes.ProfileScreen.route,
            Icons.Filled.PersonPin,
            "User"
        )

        object CerrarSesion : DrawerScreens(
            "logout",
            Icons.Filled.Close,
            "Log Out"
        )
    }
}

val screensFromDrawer = listOf(
    Screens.DrawerScreens.Mapa,
    Screens.DrawerScreens.Listar,
    Screens.DrawerScreens.ProfileScreen,
    Screens.DrawerScreens.CerrarSesion,
)