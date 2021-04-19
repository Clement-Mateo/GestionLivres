package com.example.gestionlivres

import com.google.firebase.firestore.DocumentId

class Book constructor (@DocumentId val id: String, var name: String, val userId: String, var author: String, var imageUrl: String, var isReaded: Boolean, var resume: String) {
    constructor(id: String, name: String, userId: String, author: String, imageUrl: String, isReaded: Boolean) : this(id, name, userId, author, imageUrl, isReaded, "Pas de resume")
}