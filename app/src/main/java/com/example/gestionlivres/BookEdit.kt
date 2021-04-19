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
    lateinit var editTextImageUrl: EditText
    lateinit var editTextResume: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_edit)

        book = BookUtils.getBookFromExtra(intent)

        editTextName = findViewById(R.id.editTextName_edit)
        editTextAuthor = findViewById(R.id.editTextAuthor_edit)
        editTextImageUrl = findViewById(R.id.editTextTextImageUrl_edit)
        editTextResume = findViewById(R.id.editTextResume_edit)

        editTextName.setText(book.name)
        editTextAuthor.setText(book.author)
        editTextImageUrl.setText(book.imageUrl)
        editTextResume.setText(book.resume)
    }

    fun editBook(view: View) {

        book.name = editTextName.text.toString()
        book.author = editTextAuthor.text.toString()
        book.imageUrl = editTextImageUrl.text.toString()
        book.resume = editTextResume.text.toString()

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

        BookList.refreshListOfBook()
    }
}