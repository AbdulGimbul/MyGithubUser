package com.dicoding.abdl.mygithubuser.activity

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.abdl.mygithubuser.R
import com.dicoding.abdl.mygithubuser.adapter.SectionsPagerAdapter
import com.dicoding.abdl.mygithubuser.db.DatabaseContract
import com.dicoding.abdl.mygithubuser.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.dicoding.abdl.mygithubuser.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.dicoding.abdl.mygithubuser.db.UserHelper
import com.dicoding.abdl.mygithubuser.helper.MappingHelper
import com.dicoding.abdl.mygithubuser.model.MainViewModel
import com.dicoding.abdl.mygithubuser.model.User
import com.dicoding.abdl.mygithubuser.model.UserDetail
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_details.*
import org.json.JSONObject

class DetailsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var userHelper: UserHelper
    private lateinit var uriWithId: Uri
    private var statusFavorite = false
    private var listUser: User? = null


    companion object {
        const val EXTRA_USER = "extra_user"
        private val TAG = DetailsActivity::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar?.title = "Detail User"

        userHelper = UserHelper.getInstance(applicationContext)
        userHelper.open()

        val userData = intent.getParcelableExtra(EXTRA_USER) as User
        val name = userData.name
        tv_name.text = name
        if (name != null) {
            setDataDetailUser(username = name)
        }

        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + userData.username)
        val userFav = contentResolver?.query(uriWithId, null, null, null, null)
        stateFavorite(userFav)


        val cursor: Cursor = userHelper.queryById(userData.username.toString())
        if (cursor.moveToNext()) {
            statusFavorite = true
            setStatusFavorite(statusFavorite)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = userData.name!!
        view_pager.adapter = sectionsPagerAdapter

        tabs.setupWithViewPager(view_pager)

//        setDataFav()
        btn_fav.setOnClickListener(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    fun setDataDetailUser(username: String) {
        val detailItems = ArrayList<UserDetail>()

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username"
        client.addHeader("Authorization", "token d951d8dcf731b17fb88f759da4f886ee595060f9")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {

                //parsing json
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val detailUser = UserDetail()

                    detailUser.name = responseObject.getString("name")
                    detailUser.username = responseObject.getString("login")
                    detailUser.avatar = responseObject.getString("avatar_url")
                    detailUser.location = responseObject.getString("location")
                    detailItems.add(detailUser)

                    tv_name.text = detailUser.name
                    tv_location.text = detailUser.location
                    Glide.with(this@DetailsActivity)
                        .load(detailUser.avatar)
                        .apply(RequestOptions().override(350, 350))
                        .into(img_avatar)

                } catch (e: Exception) {
                    Log.d("Excpetion", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }

//    private fun setDataFav(){
//        val user = intent.getParcelableExtra<User>(EXTRA_USER)
//
//        tv_name.text = user.name
//        tv_location.text = user.location
//        Glide.with(this)
//            .load(user.avatar)
//            .apply(RequestOptions().override(350, 350))
//            .into(img_avatar)
//    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            btn_fav.setImageResource(R.drawable.ic_baseline_favorite_36)
        } else {
            btn_fav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun stateFavorite(favCursor: Cursor?) {
        val objectFav = MappingHelper.mapCursorToArrayList(favCursor)
        for (data in objectFav) {
            if (this.listUser?.id == data.id) {
                statusFavorite = true
            }
        }
    }

    override fun onClick(v: View?) {
        val getIntentFav = intent.getParcelableExtra(EXTRA_USER) as User
        when (v?.id) {
            R.id.btn_fav -> {
                if (!statusFavorite) {
                    val values = ContentValues()
                    values.put(DatabaseContract.UserColumns.USERNAME, getIntentFav.username)
                    values.put(DatabaseContract.UserColumns.NAME, getIntentFav.name)
                    values.put(DatabaseContract.UserColumns.LOCATION, getIntentFav.location)
                    values.put(DatabaseContract.UserColumns.AVATAR, getIntentFav.avatar)

                    contentResolver?.insert(CONTENT_URI, values)
                    statusFavorite = !statusFavorite
                    setStatusFavorite(statusFavorite)
                    Log.d("cek", "Insert : $values")
                } else {
                    val namFav = getIntentFav.username.toString()
                    userHelper.deleteById(namFav)
                    uriWithId = Uri.parse("$CONTENT_URI/$USERNAME")
                    contentResolver.delete(uriWithId, null, null)
                    Log.d("Uri", "$userHelper")
                    statusFavorite = !statusFavorite
                    setStatusFavorite(statusFavorite)
                    Log.d("delete", "data terhapus")
                }
            }
        }
    }
}