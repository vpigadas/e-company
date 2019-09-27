package com.vpigadas.e_company.home.fragment.company

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vpigadas.e_company.R
import com.vpigadas.e_company.abstraction.AbstractAdapter
import com.vpigadas.e_company.abstraction.AbstractViewHolder
import com.vpigadas.e_company.listeners.ItemViewClickListener
import com.vpigadas.e_company.model.LocalCompany
import com.vpigadas.e_company.model.LocalModel
import com.vpigadas.e_company.model.LocalMore
import com.vpigadas.e_company.utils.MoreViewHolder

class CompanyAdapter(private val listener: ItemViewClickListener) : AbstractAdapter<LocalModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<LocalModel> =
        when (viewType) {
            R.layout.holder_company -> {
                val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
                CompanyViewHolder(view, listener)
            }
            R.layout.holder_more -> {
                val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
                MoreViewHolder(view, listener)
            }
            else -> super.onCreateViewHolder(parent, viewType)
        }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is LocalCompany -> R.layout.holder_company
        is LocalMore -> R.layout.holder_more
        else -> 0
    }
}
