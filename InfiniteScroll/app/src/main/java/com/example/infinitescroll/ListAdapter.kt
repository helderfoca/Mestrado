package com.example.infinitescroll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * This method is used to inflate a view in a view group
 *
 * @param layoutRes: Layout Resource to be inflated
 * @param items: Items that will be used as list
 * @param onBind: Block that will be executed when the items are binded
 * @param onClick: Block that will be executed when the items are clicked
 */
class ListAdapter<T>(
    @LayoutRes private val layoutRes: Int,
    private val items: List<T>,
    private val onBind: (View, T) -> Unit,
    private val onClick: (View, T) -> Unit
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private val VIEW_ITEM = 1
    private val VIEW_PROG = 0

    class ViewHolder(layoutView: View) : RecyclerView.ViewHolder(layoutView)

    override fun getItemViewType(position: Int): Int {
        return if (items[position] != null) VIEW_ITEM else VIEW_PROG
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = if (viewType == VIEW_ITEM) ViewHolder(LayoutInflater.from(parent.context).inflate(layoutRes, parent,false)) else ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_progress_bar, parent,false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val view = holder.itemView

        onBind(view, item)

        view.setOnClickListener {
            onClick(it, item)
        }
    }
}