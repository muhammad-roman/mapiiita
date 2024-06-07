package com.example.mapp.model

import android.graphics.Bitmap

data class Marker(
    var owner: String?,
    var markerId: String?,
    var latitude: Double,
    var longitude: Double,
    var title: String,
    var description: String,
    var category: Category,
    var photo: Bitmap?,
    var photoReference: String?
) {

    constructor() : this(
        null,
        null,
        0.0,
        0.0,
        "",
        "",
        Category(""),
        null,
        null
    )

    fun modificarTitle(newTitle: String) {
        title = newTitle
    }


    fun modificarDescription(newDescription: String) {
        description = newDescription
    }


    fun modificarPhoto(newPhoto: Bitmap) {
        photo = newPhoto
    }


    fun modificarPhotoReference(newReference: String) {
        photoReference = newReference
    }

    fun modificarCategoria(newReference: String) {
        category.name = newReference
    }
}