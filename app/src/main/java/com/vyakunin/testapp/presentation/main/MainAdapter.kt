package com.vyakunin.testapp.presentation.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.vyakunin.testapp.R
import com.vyakunin.testapp.data.MovieEntity
import com.vyakunin.testapp.ext.inflate
import com.vyakunin.testapp.presentation.common.AbsViewHolder
import com.vyakunin.testapp.presentation.common.AutoUpdatableAdapter
import kotlin.properties.Delegates

class MainAdapter(private val clickListener: (Int) -> Unit) :
        RecyclerView.Adapter<AbsViewHolder<MovieEntity>>(), AutoUpdatableAdapter {

    var data: List<MovieEntity> by Delegates.observable(arrayListOf()) { _, old, new ->
        autoNotify(old, new) { o, n ->
            o.id == n.id && o.rank == n.rank
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsViewHolder<MovieEntity> =
            ViewHolder(parent.inflate(R.layout.view_main_movie_item)) { clickListener(it) }

    override fun onBindViewHolder(holder: AbsViewHolder<MovieEntity>, position: Int) =
            holder.onBind(data[position])

    class ViewHolder(val view: View, clickListener: (Int) -> Unit) : AbsViewHolder<MovieEntity>(view = view, itemClick = clickListener) {
        private val posterView: ImageView = view.findViewById(R.id.poster_image)
        private val rankView: TextView = view.findViewById(R.id.rank_title)
        private val titleView: TextView = view.findViewById(R.id.movie_title)

        override fun onBind(item: MovieEntity) {
            Picasso.get().cancelRequest(posterView)
            titleView.text = item.name
            rankView.text = "Ranked #${item.rank}"
            Picasso.get().load(item.posterUrl).placeholder(R.drawable.ic_download).into(posterView)
        }
    }
}