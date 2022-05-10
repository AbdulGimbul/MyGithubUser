package com.dicoding.abdl.mygithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.abdl.mygithubuser.R
import com.dicoding.abdl.mygithubuser.model.User
import kotlinx.android.synthetic.main.item_cardview_user.view.*

class CardViewUserAdapter : RecyclerView.Adapter<CardViewUserAdapter.CardViewViewHolder>() {
    private val mData = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(items: ArrayList<User>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback?){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CardViewViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cardview_user, viewGroup, false)
        return CardViewViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(cardViewUserAdapter: CardViewViewHolder, position: Int) {
        cardViewUserAdapter.bind(mData[position])


        cardViewUserAdapter.itemView.setOnClickListener { onItemClickCallback!!.onItemClicked(mData[cardViewUserAdapter.adapterPosition]) }
    }

    inner class CardViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                Glide.with(context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(55, 55))
                    .into(img_item_photo)

                tv_item_name.text = user.name
                tv_user_detail.text = "type: ${user.location}"
                btn_company.text = "id: ${user.username}"

            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

}