package com.example.mobilbeadando

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var libraryBooksImageView: ImageView
    private lateinit var noData: TextView

    private lateinit var db: DatabaseHandler
    private lateinit var bookId: ArrayList<String>
    private lateinit var bookTitle: ArrayList<String>
    private lateinit var bookAuthor: ArrayList<String>
    private lateinit var bookPages: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.add_button)
        libraryBooksImageView = findViewById(R.id.library_books)
        noData = findViewById(R.id.no_data)

        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }

        db = DatabaseHandler(this@MainActivity)
        bookId = ArrayList()
        bookTitle = ArrayList()
        bookAuthor = ArrayList()
        bookPages = ArrayList()

        storeDataInArrays()

        var customAdapter = CustomAdapter(this@MainActivity, this, bookId, bookTitle, bookAuthor, bookPages)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1){
            recreate()
        }
    }

    private fun storeDataInArrays() {
        db.readAllData()?.let { cursor ->
            if (cursor.count == 0) {
                libraryBooksImageView.visibility = View.VISIBLE
                noData.visibility = View.VISIBLE
            } else {
                while (cursor.moveToNext()) {
                    bookId.add(cursor.getString(0))
                    bookTitle.add(cursor.getString(1))
                    bookAuthor.add(cursor.getString(2))
                    bookPages.add(cursor.getString(3))
                }
                libraryBooksImageView.visibility = View.GONE
                noData.visibility = View.GONE
            }
            cursor.close()
        } ?: run {
            libraryBooksImageView.visibility = View.VISIBLE
            noData.visibility = View.VISIBLE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all -> {
                confirmDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Valóban törölni szeretnéd?")
        builder.setMessage("Biztos, hogy minden adatot törölni szeretnél?")
        builder.setPositiveButton("Igen") { _: DialogInterface, _: Int ->
            val db = DatabaseHandler(this@MainActivity)
            db.deleteAllData()
            Toast.makeText(this, "Minden adat törölve", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("Nem") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        builder.create().show()
    }

}


