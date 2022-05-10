package com.dicoding.abdl.mygithubuser.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.abdl.mygithubuser.R
import com.dicoding.abdl.mygithubuser.model.UserFollowing
import kotlinx.android.synthetic.main.item_row_user.view.*


class FollowingListUserAdapter : RecyclerView.Adapter<FollowingListUserAdapter.ListViewHolder>() {
    private val dataFollowing = ArrayList<UserFollowing>()

    fun setData(followingItems: ArrayList<UserFollowing>){
        dataFollowing.clear()
        dataFollowing.addAll(followingItems)
        notifyDataSetChanged()
    }
    

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataFollowing.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(dataFollowing[position])
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind (user: UserFollowing){
            with(itemView){
                Glide.with(context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(55,55))
                    .into(img_item_photo)

                tv_item_name.text = user.name
                tv_user_detail.text = "id: ${user.username}"
            }
        }
    }
}