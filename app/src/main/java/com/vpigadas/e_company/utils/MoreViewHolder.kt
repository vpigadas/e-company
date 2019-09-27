package com.vpigadas.e_company.utils

import android.view.View
import com.vpigadas.e_company.abstraction.AbstractViewHolder
import com.vpigadas.e_company.listeners.ItemViewClickListener
import com.vpigadas.e_company.model.LocalModel
import com.vpigadas.e_company.model.LocalMore
import kotlinx.android.synthetic.main.holder_more.view.*

class MoreViewHolder(itemView: View, private val listener: ItemViewClickListener) :
    AbstractViewHolder<LocalModel>(itemView) {

    init {
        itemView.setOnClickListener { localModel?.apply { listener.onItemClick(this) } }
    }

    override fun bind(data: LocalModel) {
        when (data) {
            is LocalMore -> {
                localModel = data
                itemView.more_title.text = data.type
            }
        }
    }
}