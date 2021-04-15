package com.example.gestionlivres

import android.content.Intent


class BookUtils {
    companion object {
        fun mapping(obj: Any): Map<String, Any> {
            val map: MutableMap<String, Any> = HashMap()
            for (field in obj.javaClass.declaredFields) {
                field.isAccessible = true
                try {
                    map[field.name] = field[obj]
                } catch (e: Exception) {
                }
            }
            return map
        }

        fun getBookFromExtra(intent: Intent): Book {
            var map: HashMap<String, Any> = intent.getSerializableExtra("book") as HashMap<String, Any>
            return Book(
                map["id"].toString(),
                map["name"].toString(),
                map["author"].toString(),
                map["imageUrl"].toString(),
                map["isReaded"] as Boolean
            )
        }
    }
}