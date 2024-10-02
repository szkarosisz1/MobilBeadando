package com.example.mobilbeadando

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(
    private val activity: MainActivity,
    private val context: Context,
    private val bookId: ArrayList<String>,
    private val bookTitle: ArrayList<String>,
    private val bookAuthor: ArrayList<String>,
    private val bookPages: ArrayList<String>
) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.my_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bookIdTxt.text = bookId[position]
        holder.bookTitleTxt.text = bookTitle[position]
        holder.bookAuthorTxt.text = bookAuthor[position]
        holder.bookPagesTxt.text = bookPages[position]

        val translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim)
        holder.mainLayout.startAnimation(translateAnim)

        holder.mainLayout.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java).apply {
                putExtra("id", bookId[position])
                putExtra("title", bookTitle[position])
                putExtra("author", bookAuthor[position])
                putExtra("pages", bookPages[position])
            }
            activity.startActivityForResult(intent, 1)
        }
    }

    override fun getItemCount(): Int {
        return bookId.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookIdTxt: TextView = itemView.findViewById(R.id.book_id_txt)
        val bookTitleTxt: TextView = itemView.findViewById(R.id.book_title_txt)
        val bookAuthorTxt: TextView = itemView.findViewById(R.id.book_author_txt)
        val bookPagesTxt: TextView = itemView.findViewById(R.id.book_pages_txt)
        val mainLayout: LinearLayout = itemView.findViewById(R.id.mainLayout)
    }
}
