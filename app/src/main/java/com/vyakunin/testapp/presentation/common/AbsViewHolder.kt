package com.vyakunin.testapp.presentation.common

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class AbsViewHolder<T>(view: View, itemClick: ((Int) -> Unit)? = null) : RecyclerView.ViewHolder(view) {

    init {
        if (itemClick != null) itemView.setOnClickListener { itemClick(adapterPosition) }
    }

    abstract fun onBind(item: T)
}