package com.picpay.desafio.android

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListItemViewHolder(
    val mItemView: ListItemUserBinding
) : RecyclerView.ViewHolder(mItemView.root) {

    fun bind(user: User) {
        mItemView.name.text = user.name
        mItemView.username.text = user.username
        mItemView.progressBar.visibility = View.VISIBLE
        Picasso.get()
            .load(user.img)
            .error(R.drawable.ic_round_account_circle)
            .into(mItemView.picture, object : Callback {
                override fun onSuccess() {
                    mItemView.progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    mItemView.progressBar.visibility = View.GONE
                }
            })
    }
}