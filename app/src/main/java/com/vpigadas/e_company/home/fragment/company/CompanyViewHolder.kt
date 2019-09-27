package com.vpigadas.e_company.home.fragment.company

import android.view.View
import com.vpigadas.e_company.R
import com.vpigadas.e_company.abstraction.AbstractViewHolder
import com.vpigadas.e_company.listeners.ItemViewClickListener
import com.vpigadas.e_company.model.LocalCompany
import com.vpigadas.e_company.model.LocalModel
import kotlinx.android.synthetic.main.holder_company.view.*

class CompanyViewHolder(itemView: View, private val listener: ItemViewClickListener) :
    AbstractViewHolder<LocalModel>(itemView) {

    init {
        itemView.setOnClickListener { localModel?.apply { listener.onItemClick(this) } }
    }

    override fun bind(data: LocalModel) {
        when (data) {
            is LocalCompany -> {
                localModel = data

                itemView.company_name.text = data.name
                itemView.company_speciality.text =
                    itemView.context.getString(R.string.speciality_label, data.speciality)
            }
        }
    }
}