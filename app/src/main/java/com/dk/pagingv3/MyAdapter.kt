package com.dk.pagingv3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dk.pagingv3.data.GithubItems

class MyAdapter : PagingDataAdapter<GithubItems, MyAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubItems>(){
            override fun areItemsTheSame(oldItem: GithubItems, newItem: GithubItems): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GithubItems, newItem: GithubItems): Boolean {
                return oldItem == newItem
            }

        }
    }


    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        val imgArea = view.findViewById<ImageView>(R.id.imgArea)
        val textArea = view.findViewById<TextView>(R.id.textArea)

        fun bind(item : GithubItems) {
            textArea.text = item.id.toString()
            imgArea.load(item.owner.avatar_url)
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = getItem(position)
        if(item != null) {
            holder.bind(item)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return MyViewHolder(view)
    }

}
