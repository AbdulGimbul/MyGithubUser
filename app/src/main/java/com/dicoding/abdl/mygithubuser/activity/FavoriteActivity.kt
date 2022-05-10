package com.dicoding.abdl.mygithubuser.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.abdl.mygithubuser.R
import com.dicoding.abdl.mygithubuser.adapter.FavoriteCardViewUserAdapter
import com.dicoding.abdl.mygithubuser.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.dicoding.abdl.mygithubuser.helper.MappingHelper
import com.dicoding.abdl.mygithubuser.model.User
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: FavoriteCardViewUserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        val titleBar = "Favorite User"
        supportActionBar?.title = titleBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adapter = FavoriteCardViewUserAdapter()


        GlobalScope.launch(Dispatchers.Main) {
            val deferredFav = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val favUser = deferredFav.await()
            if (favUser.size > 0) {
                adapter.listFavUser = favUser
            } else {
                adapter.listFavUser = ArrayList()
                Toast.makeText(this@FavoriteActivity, "Belum ada favorit user", Toast.LENGTH_LONG)
                    .show()
            }
        }

        showRecyclerView()
        showSelectedUser()
    }

    private fun showRecyclerView() {
        rv_github_fav.adapter = adapter
        rv_github_fav.layoutManager = LinearLayoutManager(this)
        rv_github_fav.setHasFixedSize(true)
    }

    private fun showSelectedUser() {
        adapter.setOnItemClickCallback(object : FavoriteCardViewUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intentDetailUser = Intent(this@FavoriteActivity, DetailsActivity::class.java)
                intentDetailUser.putExtra(DetailsActivity.EXTRA_USER, data)

                startActivity(intentDetailUser)
            }
        })
    }
}