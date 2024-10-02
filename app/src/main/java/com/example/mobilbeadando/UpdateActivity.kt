package com.example.mobilbeadando

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class UpdateActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var authorInput: EditText
    private lateinit var pagesInput: EditText
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button

    private var id: String? = null
    private var title: String? = null
    private var author: String? = null
    private var pages: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        titleInput = findViewById(R.id.title_input2)
        authorInput = findViewById(R.id.author_input2)
        pagesInput = findViewById(R.id.pages_input2)
        updateButton = findViewById(R.id.update_button)
        deleteButton = findViewById(R.id.delete_button)

        getAndSetIntentData()

        supportActionBar?.title = title

        updateButton.setOnClickListener {
            val updatedTitle = titleInput.text.toString()
            val updatedAuthor = authorInput.text.toString()
            val updatedPages = pagesInput.text.toString()

            if (id != null) {
                val db = DatabaseHandler(this@UpdateActivity)
                val result = db.updateData(id!!, updatedTitle, updatedAuthor, updatedPages.toIntOrNull() ?: 0)

                if (result) {
                    Toast.makeText(this, "Sikeresen frissítve!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "A frissítés sikertelen.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Az ID üres.", Toast.LENGTH_SHORT).show()
            }
        }

        deleteButton.setOnClickListener {
            confirmDialog()
        }
    }

    private fun getAndSetIntentData() {
        if (intent.hasExtra("id") && intent.hasExtra("title") &&
            intent.hasExtra("author") && intent.hasExtra("pages")) {

            id = intent.getStringExtra("id")
            title = intent.getStringExtra("title")
            author = intent.getStringExtra("author")
            pages = intent.getStringExtra("pages")

            titleInput.setText(title)
            authorInput.setText(author)
            pagesInput.setText(pages)
        } else {
            Toast.makeText(this, "Nincs adat.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Törölni szeretnéd: $title ?")
        builder.setMessage("Biztos, hogy törölni szeretnéd: $title ?")
        builder.setPositiveButton("Igen") { _: DialogInterface, _: Int ->
            val db = DatabaseHandler(this@UpdateActivity)
            db.deleteOneRow(id!!)
            Toast.makeText(this, "$title törölve.", Toast.LENGTH_SHORT).show()
            finish()
        }
        builder.setNegativeButton("Nem") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        builder.create().show()
    }
}
