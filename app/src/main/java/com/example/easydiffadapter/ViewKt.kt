package com.example.easydiffadapter

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.justSetup(
    layoutManager: RecyclerView.LayoutManager,
    adapter: RecyclerView.Adapter<*>,
    itemDecoration: RecyclerView.ItemDecoration? = null
) {
    this.layoutManager = layoutManager
    this.adapter = adapter
    itemDecoration?.let { addItemDecoration(it) }
}