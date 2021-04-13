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

        private val TAG = "Info"
    }

    private var db: FirebaseFirestore? = null

    private val bookList: MutableList<Book> = mutableListOf()

    private var recyclerView: RecyclerView? = null

    private var bookAdapter: BookAdapter? = null

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

        db?.collection("books")
            ?.get()
            ?.addOnSuccessListener { result ->
                for (document in result) {
                    bookList.add(
                        Book(
                            document.data["name"].toString(),
                            document.data["author"].toString(),
                            document.data["imageId"].toString(),
                            document.data["isReaded"] as Boolean
                        )
                    )
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                if(result.documents.size != 0) {
                    bookAdapter?.notifyDataSetChanged()
                }
            }
            ?.addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
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

        val book = Book(
            editTextName.text.toString(),
            editTextAuthor.text.toString(),
            editTextTextImageId.text.toString(),
            false
        )
        bookList.add(book)

        val bookMap: Map<String, Any> = mapping(book)

        db?.collection("books")
            ?.add(bookMap)
            ?.addOnSuccessListener { documentReference ->
                Log.d(TAG, bookMap["name"].toString() + " added")
            }
            ?.addOnFailureListener { e ->
                Log.w(TAG, "Error adding " + bookMap["name"].toString(), e)
            }
        bookAdapter?.notifyItemInserted(bookList.size);

        editTextName.text.clear()
        editTextAuthor.text.clear()
        editTextTextImageId.text.clear()

        Toast.makeText(this, "Ajout du livre " + book.name + " réussie !", Toast.LENGTH_SHORT).show()
    }

    fun remove(view: View) {
//        val removeIndex = 2
//        data.remove(removeIndex)
//        adapter.notifyItemRemoved(removeIndex)
    }
}