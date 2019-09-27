package com.vpigadas.e_company.abstraction

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vpigadas.e_company.model.LocalModel

abstract class AbstractViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    protected var localModel: LocalModel? = null
    abstract fun bind(data: T)
}