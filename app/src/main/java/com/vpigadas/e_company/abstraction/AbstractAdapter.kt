package com.vpigadas.e_company.abstraction

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.vpigadas.e_company.model.LocalModel
import com.vpigadas.e_company.utils.EmptyViewHolder

abstract class AbstractAdapter<T : LocalModel> : ListAdapter<T, AbstractViewHolder<T>>(DiffCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<T> =
        EmptyViewHolder(LinearLayout(parent.context))


    override fun onBindViewHolder(holder: AbstractViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
    }

    fun getItemData(position: Int): T = getItem(position)
}

private class DiffCallback<T : LocalModel> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(o: T, t1: T): Boolean = o == t1

    override fun areContentsTheSame(o: T, t1: T): Boolean = o.equalContent(t1)
}