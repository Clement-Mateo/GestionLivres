package com.example.gestionlivres

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class BookAdapter(private var bookList: List<Book>) : RecyclerView.Adapter<BookViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.updateWithBook(book)

        var checkBoxIsreaded: CheckBox = holder.itemView.findViewById(R.id.checkBoxIsReaded)

        checkBoxIsreaded.setOnClickListener {
            book.isReaded = checkBoxIsreaded.isChecked

            val bookMap: Map<String, Any> = BookUtils.mapping(book)

            BookList.db.collection("books")?.document(book.id)
                    .set(bookMap)
                    .addOnSuccessListener {
                        Log.i(BookList.TAG, bookMap["name"].toString() + " checked as readed")
                    }
                    .addOnFailureListener { e ->
                        Log.i(BookList.TAG, "Error saving " + bookMap["name"].toString() + " as readed", e)
                    }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, BookDetail::class.java)
            intent.putExtra("book", BookUtils.mapping(book) as HashMap)
            startActivity(holder.itemView.context, intent, null)
        }

        holder.itemView.findViewById<ImageButton>(R.id.btnDetail).setOnClickListener {
            val intent = Intent(holder.itemView.context, BookDetail::class.java)
            intent.putExtra("book", BookUtils.mapping(book) as HashMap)
            startActivity(holder.itemView.context, intent, null)
        }

        holder.itemView.findViewById<ImageButton>(R.id.btnEdit).setOnClickListener {
            val intent = Intent(holder.itemView.context, BookEdit::class.java)
            intent.putExtra("book", BookUtils.mapping(book) as HashMap)
            startActivity(holder.itemView.context, intent, null)
        }

        holder.itemView.findViewById<ImageButton>(R.id.btnDelete).setOnClickListener {

            BookList.db.collection("books").document(book.id.toString())
                    .delete()
                    .addOnSuccessListener {
                        Log.i(BookList.TAG, book.name + " successfully deleted!")

                        Toast.makeText(holder.itemView.context, "Suppression réussie de " + book.name + " !", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e -> Log.i(BookList.TAG, "Error deleting" + book.name, e) }

            BookList.refreshListOfBook()
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = bookList.size

}

class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun updateWithBook(book: Book) {
        val tvName = itemView.findViewById<TextView>(R.id.tvName)
        val tvAuthor = itemView.findViewById<TextView>(R.id.tvAuthor)
        val checkBoxIsReaded: CheckBox = itemView.findViewById(R.id.checkBoxIsReaded)
        val bookImage = itemView.findViewById<ImageView>(R.id.bookImage)


        tvName.text = book.name
        tvAuthor.text = "de " + book.author
        checkBoxIsReaded.isChecked = book.isReaded

        if(book.imageUrl.length > 0) {
            Picasso.get().load(book.imageUrl).resize(3000, 2000).centerInside().error(R.drawable.books).into(bookImage);
        }
    }
}