package com.example.finin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finin.R
import com.example.finin.model.Data
import kotlinx.android.synthetic.main.user_row_item.view.*

class UserAdapter(
    private val items: List<Data>
) :
    RecyclerView.Adapter<UserAdapter.UsersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.user_row_item, parent, false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class UsersViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Data) {
            with(view) {
                tvName.text = data.first_name + " " + data.last_name
                tvEmail.text = data.email
                Glide.with(itemView.context)
                    .load(data.avatar)
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(ivUser)
            }
        }
    }

    fun addData(listItems: ArrayList<Data>) {
        var size = listItems.size
        listItems.addAll(listItems)
        var sizeNew = listItems.size
        notifyItemRangeChanged(size, sizeNew)
    }
}