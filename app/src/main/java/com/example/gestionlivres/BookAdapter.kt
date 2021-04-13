package com.example.gestionlivres

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import rx.subjects.PublishSubject

class BookAdapter(private var bookList: List<Book>) : RecyclerView.Adapter<BookViewHolder>() {

    private val onClickSubject: PublishSubject<Book> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.updateWithBook(book)

        holder.itemView.setOnClickListener {
            onClickSubject.onNext(book)
            startActivity(holder.itemView.context, Intent(holder.itemView.context, BookDetail::class.java), null)
        }

        holder.itemView.findViewById<ImageButton>(R.id.btnDelete).setOnClickListener {
            BookList.db?.collection("books")?.document(book.id.toString())
                    ?.delete()
                    ?.addOnSuccessListener {
                        Log.i(BookList.TAG, book.name + " successfully deleted!")
                    }
                    ?.addOnFailureListener { e -> Log.i(BookList.TAG, "Error deleting" + book.name, e) }

            BookList.refreshListOfBook()
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = bookList.size

}

class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun updateWithBook(book: Book) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)
        name.text = book.name
        val author = itemView.findViewById<TextView>(R.id.tv_author)
        author.text = "de " + book.author
    }
}