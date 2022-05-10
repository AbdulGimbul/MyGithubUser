package com.dicoding.abdl.userfavoritegithub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.abdl.userfavoritegithub.R
import com.dicoding.abdl.userfavoritegithub.model.User
import kotlinx.android.synthetic.main.item_cardview_user.view.*
import kotlin.collections.ArrayList

class FavoriteCardViewUserAdapter: RecyclerView.Adapter<FavoriteCardViewUserAdapter.FavoriteCardViewViewHolder>() {

    var listFavUser = ArrayList<User>()
        set(listFavUser) {
            if (listFavUser.size > 0) {
                this.listFavUser.clear()
            }
            this.listFavUser.addAll(listFavUser)
            notifyDataSetChanged()
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

    }

    inner class FavoriteCardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(120, 120))
                    .into(img_item_photo)

                tv_item_name.text = user.name
                tv_user_detail.text = "type: ${user.location}"
                btn_company.text = "id: ${user.username}"

            }
        }
    }

}