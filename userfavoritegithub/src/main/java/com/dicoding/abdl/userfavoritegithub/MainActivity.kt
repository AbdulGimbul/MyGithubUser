package com.dicoding.abdl.userfavoritegithub

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.abdl.userfavoritegithub.adapter.FavoriteCardViewUserAdapter
import com.dicoding.abdl.userfavoritegithub.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.dicoding.abdl.userfavoritegithub.helper.MappingHelper
import com.dicoding.abdl.userfavoritegithub.model.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    companion object{
        private const val EXTRA_STATE = "extra_state"
    }

    private lateinit var adapter: FavoriteCardViewUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "User Favorite Github"

        rv_github.layoutManager = LinearLayoutManager(this)
        rv_github.setHasFixedSize(true)

        adapter = FavoriteCardViewUserAdapter()

        adapter.notifyDataSetChanged()
        rv_github.adapter = adapter

        val handleThread = HandlerThread("Observer")
        handleThread.start()
        val handler = Handler(handleThread.looper)
        val observer = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                loadData()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, observer)

        if (savedInstanceState == null){
            loadData()
        }else{
            val favUser = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            if (favUser != null){
                adapter.listFavUser = favUser
            }
        }
    }

    private fun loadData(){
        GlobalScope.launch(Dispatchers.Main){
            progressBar.visibility = View.VISIBLE
            val defferedFav = async(Dispatchers.IO){
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val userFav =defferedFav.await()
            progressBar.visibility = View.INVISIBLE
            if (userFav.size > 0){
                adapter.listFavUser = userFav
            }else{
                adapter.listFavUser = ArrayList()
                Toast.makeText(this@MainActivity, "Tidak ada data", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavUser)
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}