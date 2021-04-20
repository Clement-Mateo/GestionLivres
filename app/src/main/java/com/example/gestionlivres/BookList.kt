package com.example.gestionlivres

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class BookList : AppCompatActivity() {

    companion object {

        val TAG = "Info"

        val bookList: MutableList<Book> = mutableListOf()

        lateinit var db: FirebaseFirestore

        lateinit var recyclerView: RecyclerView

        lateinit var bookAdapter: BookAdapter

        fun refreshListOfBook() {
            bookList.clear()
            db.collection("books")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            if(FirebaseAuth.getInstance().currentUser.uid == document.data["userId"].toString()) {
                                bookList.add(
                                        Book(document.id,
                                                document.data["name"].toString(),
                                                document.data["userId"].toString(),
                                                document.data["author"].toString(),
                                                document.data["imageUrl"].toString(),
                                                document.data["isReaded"] as Boolean,
                                                document.data["resume"].toString()
                                        )
                                )
                                Log.i(TAG, "${document.id} => ${document.data}")
                            }
                        }
                        if(result.documents.size != 0) {
                            bookAdapter.notifyDataSetChanged()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.i(TAG, "Error getting documents.", exception)
                    }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_list)

        findViewById<ImageButton>(R.id.btnAddBook).setOnClickListener {
            ContextCompat.startActivity(this, Intent(this, BookAdd::class.java), null)
        }

        /*********************************************
         **************** RecyclerView ***************
         *********************************************/

        recyclerView = findViewById(R.id.bookListRecyclerView)

        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = manager

        bookAdapter = BookAdapter(bookList)
        recyclerView.adapter = bookAdapter

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
}