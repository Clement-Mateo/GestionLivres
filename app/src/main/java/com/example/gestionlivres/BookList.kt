package com.example.gestionlivres

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class BookList : AppCompatActivity() {

    companion object {

        val TAG = "Info"

        val bookList: MutableList<Book> = mutableListOf()

        var db: FirebaseFirestore? = null

        var recyclerView: RecyclerView? = null

        var bookAdapter: BookAdapter? = null

        fun refreshListOfBook() {
            bookList.clear()
            db?.collection("books")
                    ?.get()
                    ?.addOnSuccessListener { result ->
                        for (document in result) {
                            bookList.add(
                                    Book(document.id,
                                            document.data["name"].toString(),
                                            document.data["author"].toString(),
                                            document.data["imageId"].toString(),
                                            document.data["isReaded"] as Boolean
                                    )
                            )
                            Log.i(TAG, "${document.id} => ${document.data}")
                        }
                        if(result.documents.size != 0) {
                            bookAdapter?.notifyDataSetChanged()
                        }
                    }
                    ?.addOnFailureListener { exception ->
                        Log.i(TAG, "Error getting documents.", exception)
                    }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_list)

        /*********************************************
         **************** RecyclerView ***************
         *********************************************/

        recyclerView = findViewById(R.id.bookList)

        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL

        recyclerView?.layoutManager = manager
        bookAdapter = BookAdapter(bookList)
        recyclerView?.adapter = bookAdapter

        /*********************************************
         ****************** FireStore ****************
         *********************************************/

        db = Firebase.firestore
        refreshListOfBook()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.exit(0)
        }
        return true
    }

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

    fun addBook(view: View) {

        var editTextName = findViewById<EditText>(R.id.editTextName)
        var editTextAuthor = findViewById<EditText>(R.id.editTextAuthor)
        var editTextTextImageId = findViewById<EditText>(R.id.editTextTextImageId)

        val book = Book(db?.collection("books")?.document()?.id.toString(),
                editTextName.text.toString(),
                editTextAuthor.text.toString(),
                editTextTextImageId.text.toString(),
                false
        )
        bookList.add(book)

        var bookMap: Map<String, Any> = mapping(book)

        db?.collection("books")?.document(book.id)
            ?.set(bookMap)
            ?.addOnSuccessListener { documentReference ->
                Log.i(TAG, bookMap["name"].toString() + " added")
            }
            ?.addOnFailureListener { e ->
                Log.i(TAG, "Error adding " + bookMap["name"].toString(), e)
            }

        bookAdapter?.notifyItemInserted(bookList.size);

        editTextName.text.clear()
        editTextAuthor.text.clear()
        editTextTextImageId.text.clear()

        Toast.makeText(this, "Ajout du livre " + book.name + " réussie !", Toast.LENGTH_SHORT).show()
    }
}