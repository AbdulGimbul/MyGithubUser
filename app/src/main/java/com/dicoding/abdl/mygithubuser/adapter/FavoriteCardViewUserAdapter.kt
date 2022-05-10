package com.dicoding.abdl.mygithubuser.adapter

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.abdl.mygithubuser.CustomOnClickListener
import com.dicoding.abdl.mygithubuser.R
import com.dicoding.abdl.mygithubuser.activity.DetailsActivity
import com.dicoding.abdl.mygithubuser.db.DatabaseContract
import com.dicoding.abdl.mygithubuser.db.UserHelper
import com.dicoding.abdl.mygithubuser.helper.MappingHelper
import com.dicoding.abdl.mygithubuser.model.User
import kotlinx.android.synthetic.main.activity_favorite.view.*
import kotlinx.android.synthetic.main.item_cardview_user.view.*
import kotlin.collections.ArrayList

class FavoriteCardViewUserAdapter: RecyclerView.Adapter<FavoriteCardViewUserAdapter.FavoriteCardViewViewHolder>() {
    var TAG = FavoriteCardViewUserAdapter::class.java.simpleName
    private var onItemClickCallback: OnItemClickCallback? = null

    var listFavUser = ArrayList<User>()
        set(listFavUser) {
            if (listFavUser.size > 0) {
                this.listFavUser.clear()
            }
            this.listFavUser.addAll(listFavUser)
            notifyDataSetChanged()
        }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback?){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoriteCardViewViewHolder {
        val mView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_cardview_user, viewGroup, false)
        return FavoriteCardViewViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return listFavUser.size
    }

    override fun onBindViewHolder(favoriteCardViewUserAdapter: FavoriteCardViewViewHolder, position: Int) {
        favoriteCardViewUserAdapter.bind(listFavUser[position])

        favoriteCardViewUserAdapter.itemView.setOnClickListener { onItemClickCallback!!.onItemClicked(listFavUser[favoriteCardViewUserAdapter.adapterPosition]) }
    }

    inner class FavoriteCardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                Glide.with(context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(55, 55))
                    .into(img_item_photo)

                tv_item_name.text = user.name
                tv_user_detail.text = "type: ${user.location}"
                btn_company.text = "id: ${user.username}"

//                rv_github_fav.setOnClickListener(
//                    CustomOnClickListener(adapterPosition, object : CustomOnClickListener.OnItemClickCallback{
//                        override fun onItemClicked(v: View, position: Int) {
//                            val intentFav = Intent(activity, DetailsActivity::class.java)
//                            intentFav.putExtra(DetailsActivity.EXTRA_USER, user)
//                            activity.startActivity(intentFav)
//                        }
//                    })
//                )

//                Log.d(TAG, user.toString())


//                var statusFavorite = false
//                var userHelper: UserHelper
//
//                userHelper = UserHelper.getInstance(context)
//                userHelper.open()
//
//                fun setStatusFavorite(statusFavorite: Boolean) {
//                    if (statusFavorite) {
//                        btn_fav.setImageResource(R.drawable.ic_baseline_favorite_24)
//                    } else {
//                        btn_fav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
//                    }
//                }
//
//
//                val favUserCheck = userHelper.queryById(user.username.toString())
//                val favUser = MappingHelper.mapCursorToArrayList(favUserCheck)
//                for (data in favUser) {
//                    if (user.id == data.id) {
//                        Log.d("ini", "cek Favorit: $favUser")
//                        Log.d("ini", "cek data: $data")
//                        statusFavorite = true
//                        Log.d("ini", "cek dataFav: $statusFavorite")
//                    }
//                }
//
//
//                val cursor: Cursor = userHelper.queryById(user.username.toString())
//                if (cursor.moveToNext()) {
//                    statusFavorite = true
//                    setStatusFavorite(statusFavorite)
//                }
//
//
//                setStatusFavorite(statusFavorite)
//                userHelper = UserHelper(context)
//                btn_fav.setOnClickListener {
//                    if (!statusFavorite) {
//                        val values = ContentValues()
//                        values.put(DatabaseContract.UserColumns.USERNAME, user.username)
//                        values.put(DatabaseContract.UserColumns.NAME, user.name)
//                        values.put(DatabaseContract.UserColumns.LOCATION, user.location)
//                        values.put(DatabaseContract.UserColumns.AVATAR, user.avatar)
//
//                        userHelper.insert(values)
//                        statusFavorite = !statusFavorite
//                        setStatusFavorite(statusFavorite)
//                        Log.d("cek", "Insert : $values")
//                    } else {
//                        val namFav = user.username.toString()
//                        userHelper.deleteById(namFav)
//                        statusFavorite = !statusFavorite
//                        setStatusFavorite(statusFavorite)
//                        Log.d("delete", "data terhapus")
//                    }
//                }
            }
        }
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}