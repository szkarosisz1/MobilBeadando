package com.example.mobilbeadando

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class UpdateActivity : AppCompatActivity() {

    private lateinit var title_input: EditText
    private lateinit var author_input: EditText
    private lateinit var pages_input: EditText
    private lateinit var update_button: Button
    private lateinit var delete_button: Button

    private var id: String? = null
    private var title: String? = null
    private var author: String? = null
    private var pages: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update)

        title_input = findViewById(R.id.title_input2)
        author_input = findViewById(R.id.author_input2)
        pages_input = findViewById(R.id.pages_input2)
        update_button = findViewById(R.id.update_button)

        getAndSetIntentData()

        supportActionBar?.title = title

        update_button.setOnClickListener {
            val updatedTitle = title_input.text.toString()
            val updatedAuthor = author_input.text.toString()
            val updatedPages = pages_input.text.toString()

            if (id != null) {
                val db = DatabaseHandler(this@UpdateActivity)
                val result = db.updateData(id!!, updatedTitle, updatedAuthor, updatedPages.toIntOrNull() ?: 0)

                if (result) {
                    Toast.makeText(this, "successfully Updated!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to Update.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "ID is null.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getAndSetIntentData() {
        if (intent.hasExtra("id") && intent.hasExtra("title") &&
            intent.hasExtra("author") && intent.hasExtra("pages")) {

            id = intent.getStringExtra("id")
            title = intent.getStringExtra("title")
            author = intent.getStringExtra("author")
            pages = intent.getStringExtra("pages")

            title_input.setText(title)
            author_input.setText(author)
            pages_input.setText(pages)
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }
    }
}
