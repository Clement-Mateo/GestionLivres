package com.example.gestionlivres

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class BookDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_detail)

        var book: Book = BookUtils.getBookFromExtra(intent)

        var tvName = findViewById<TextView>(R.id.tvName_detail)
        var tvAuthor = findViewById<TextView>(R.id.tvAuthor_detail)
        val bookImage = findViewById<ImageView>(R.id.bookImage_detail)
        val tvResume = findViewById<TextView>(R.id.textViewResume_detail)

        tvName.text = book.name
        tvAuthor.text = book.author
        tvResume.text = book.resume

        if(book.imageUrl.length > 0) {
            Picasso.get().load(book.imageUrl).resize(3000, 2000).centerInside().error(R.drawable.books).into(bookImage);
        } else {
            bookImage.setImageDrawable(resources.getDrawable(R.drawable.books))
        }
    }
}