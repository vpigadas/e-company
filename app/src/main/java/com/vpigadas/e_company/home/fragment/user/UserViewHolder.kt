package com.vpigadas.e_company.home.fragment.user

import android.view.View
import com.vpigadas.e_company.R
import com.vpigadas.e_company.abstraction.AbstractViewHolder
import com.vpigadas.e_company.listeners.ItemViewClickListener
import com.vpigadas.e_company.model.LocalModel
import com.vpigadas.e_company.model.LocalUser
import kotlinx.android.synthetic.main.holder_user.view.*

class UserViewHolder(itemView: View, private val listener: ItemViewClickListener) :
    AbstractViewHolder<LocalModel>(itemView) {

    init {
        itemView.setOnClickListener { localModel?.apply { listener.onItemClick(this) } }
    }


    override fun bind(data: LocalModel) {
        when (data) {
            is LocalUser -> {
                localModel = data

                itemView.user_name.text = data.getName()
                itemView.user_speciality.text =
                    itemView.context.getString(R.string.speciality_label, data.speciality)
            }
        }
    }
}