package com.tengiz.itstepacademy_finalproject_android.ui.adapter

import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tengiz.itstepacademy_finalproject_android.R
import com.tengiz.itstepacademy_finalproject_android.databinding.BookItemBinding
import com.tengiz.itstepacademy_finalproject_android.extensions.toLanguageString
import com.tengiz.itstepacademy_finalproject_android.model.Author
import com.tengiz.itstepacademy_finalproject_android.model.Book

class HomeBooksAdapter(
    private val onItemClick: (Book) -> Unit,
    private val onAuthorClick: (Author) -> Unit  // Pass a callback for author clicks
) : PagingDataAdapter<Book, HomeBooksAdapter.BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = BookItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class BookViewHolder(private val binding: BookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            binding.bookNameTV.text = book.title
            binding.bookLanguageTV.text = "Language:" + " " + book.language.toLanguageString()
            binding.bookAvailableQuantityTV.text = "Available: ${book.quantity.toString()}"

            // Make each author clickable
            val spannable = SpannableString(book.authors.joinToString(", ") { it.name })
            var start = 0
            book.authors.forEach { author ->
                val end = start + author.name.length
                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        onAuthorClick(author) // call the fragment callback
                    }
                }
                spannable.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                start = end + 2 // +2 for comma and space
            }
            binding.bookAuthorTV.text = spannable
            binding.bookAuthorTV.movementMethod = LinkMovementMethod.getInstance()


            // Load book image
            Glide.with(binding.root.context)
                .load(book.imageURL)
                .placeholder(R.drawable.book)
                .error(R.drawable.error)
                .into(binding.frontCoverIV)

            // Click on the book card
            binding.root.setOnClickListener { onItemClick(book) }
        }
    }
}

class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Book, newItem: Book) = oldItem == newItem
}