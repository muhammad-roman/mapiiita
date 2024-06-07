package com.example.mapp.model

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


class Repository {
    // Instancia de la base de datos Firestore
    private val database = FirebaseFirestore.getInstance()

    fun getMarkers(): CollectionReference {
        return database.collection("markers")
    }

    fun getUserImageUri(): CollectionReference {
        return database.collection("user")
    }
}