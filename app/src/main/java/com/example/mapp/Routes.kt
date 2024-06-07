package com.example.mapp


sealed class Routes(val route: String) {

    object MapScreen : Routes("map_screen")
    object ListMarkersScreen : Routes("list_mark")
    object Camara : Routes("camera_screen")
    object TakePhotoScreen : Routes("take_photo")
    object EditMarker : Routes("edit_marker")
    object LogScreen : Routes("login_screen")
    object RegisterScreen : Routes("register_screen")
    object ProfileScreen : Routes("user_screen")
}
