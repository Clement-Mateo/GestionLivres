package com.example.gestionlivres

import com.google.firebase.firestore.DocumentId

class Book constructor (@DocumentId val id: String, var name: String, var author: String, var imageUrl: String, var isReaded: Boolean )