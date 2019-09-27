package com.vpigadas.e_company.home.fragment.user

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vpigadas.e_company.R
import com.vpigadas.e_company.abstraction.AbstractAdapter
import com.vpigadas.e_company.abstraction.AbstractViewHolder
import com.vpigadas.e_company.listeners.ItemViewClickListener
import com.vpigadas.e_company.model.LocalModel
import com.vpigadas.e_company.model.LocalMore
import com.vpigadas.e_company.model.LocalUser
import com.vpigadas.e_company.utils.MoreViewHolder

class UserAdapter(private val listener: ItemViewClickListener) : AbstractAdapter<LocalModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<LocalModel> =
        when (viewType) {
            R.layout.holder_user -> {
                val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
                UserViewHolder(view, listener)
            }
            R.layout.holder_more -> {
                val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
                MoreViewHolder(view, listener)
            }
            else -> super.onCreateViewHolder(parent, viewType)
        }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is LocalUser -> R.layout.holder_user
        is LocalMore -> R.layout.holder_more
        else -> 0
    }
}
