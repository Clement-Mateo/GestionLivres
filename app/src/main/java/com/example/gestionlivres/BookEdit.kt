package com.example.gestionlivres

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BookEdit  : AppCompatActivity() {

    lateinit var book: Book
    lateinit var editTextName: EditText
    lateinit var editTextAuthor: EditText
    lateinit var editTextTextImageUrl: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_edit)

        book = BookUtils.getBookFromExtra(intent)

        editTextName = findViewById(R.id.editTextName_edit)
        editTextAuthor = findViewById(R.id.editTextAuthor_edit)
        editTextTextImageUrl = findViewById(R.id.editTextTextImageUrl_edit)

        editTextName.setText(book.name)
        editTextAuthor.setText(book.author)
        editTextTextImageUrl.setText(book.imageUrl)
    }

    fun editBook(view: View) {

        book.name = editTextName.text.toString()
        book.author = editTextAuthor.text.toString()
        book.imageUrl = editTextTextImageUrl.text.toString()

        val bookMap: Map<String, Any> = BookUtils.mapping(book)

        BookList.db?.collection("books")?.document(book.id)
                ?.set(bookMap)
                ?.addOnSuccessListener { documentReference ->
                    Log.i(BookList.TAG, bookMap["name"].toString() + " edited")

                    Toast.makeText(this, "Edition du livre " + book.name + " réussie !", Toast.LENGTH_SHORT).show()

                    BookList.refreshListOfBook()
                }
                ?.addOnFailureListener { e ->
                    Log.i(BookList.TAG, "Error editing " + bookMap["name"].toString(), e)
                }
    }
}