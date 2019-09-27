package com.vpigadas.e_company.utils

import android.view.View
import com.vpigadas.e_company.abstraction.AbstractViewHolder

class EmptyViewHolder<T>(itemView: View) : AbstractViewHolder<T>(itemView) {
    override fun bind(data: T) {}
}