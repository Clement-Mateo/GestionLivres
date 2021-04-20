package com.example.gestionlivres

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class BookAdd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_add)

    }

    fun addBook(view: View) {

        var editTextName = findViewById<EditText>(R.id.editTextName)
        var editTextAuthor = findViewById<EditText>(R.id.editTextAuthor)
        var editTextTextImageId = findViewById<EditText>(R.id.editTextImageUrl)
        var editTextResume = findViewById<EditText>(R.id.editTextResume)

        if(editTextResume.text.length == 0) {
            editTextResume.setText("Pas de resume")
        }

        val book = Book(BookList.db.collection("books").document().id,
                editTextName.text.toString(),
                FirebaseAuth.getInstance().currentUser.uid,
                editTextAuthor.text.toString(),
                editTextTextImageId.text.toString(),
                false,
                editTextResume.text.toString()
        )
        BookList.bookList.add(book)

        val bookMap: Map<String, Any> = BookUtils.mapping(book)

        BookList.db?.collection("books").document(book.id)
                .set(bookMap)
                .addOnSuccessListener {
                    Log.i(BookList.TAG, bookMap["name"].toString() + " added")

                    BookList.bookAdapter.notifyItemInserted(BookList.bookList.size);

                    editTextName.text.clear()
                    editTextAuthor.text.clear()
                    editTextTextImageId.text.clear()
                    editTextResume.text.clear()

                    Toast.makeText(this, "Ajout du livre " + book.name + " réussie !", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this, BookList::class.java))
                }
                .addOnFailureListener { e ->
                    Log.i(BookList.TAG, "Error adding " + bookMap["name"].toString(), e)
                }
    }
}