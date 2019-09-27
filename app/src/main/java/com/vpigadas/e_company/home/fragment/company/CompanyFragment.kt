package com.vpigadas.e_company.home.fragment.company

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vpigadas.e_company.R
import com.vpigadas.e_company.abstraction.AbstractFragment
import com.vpigadas.e_company.home.HomeViewModel
import com.vpigadas.e_company.insert.company.CompanyBottomSheet
import com.vpigadas.e_company.listeners.ItemViewClickListener
import com.vpigadas.e_company.model.LocalCompany
import com.vpigadas.e_company.model.LocalModel
import com.vpigadas.e_company.model.LocalMore
import kotlinx.android.synthetic.main.fragment_company.*

class CompanyFragment : AbstractFragment(), ItemViewClickListener {

    private lateinit var viewModel: HomeViewModel

    override fun getLayout(): Int = R.layout.fragment_company

    override fun initLayout(view: View) {
        viewModel = when (val act = activity) {
            null -> ViewModelProviders.of(this).get(HomeViewModel::class.java)
            else -> ViewModelProviders.of(act).get(HomeViewModel::class.java)
        }
    }

    override fun resumeLayout(view: View) {
        viewModel.getStreamCompany().observe(this, Observer {
            when (val adapter = recycler_company.adapter) {
                is CompanyAdapter -> adapter.submitList(it)
                else -> recycler_company.adapter = CompanyAdapter(this).apply { submitList(it) }
            }
        })
        viewModel.getCompanyList()
    }

    override fun destroyLayout() {}

    override fun onItemClick(model: LocalModel) {
        when (model) {
            is LocalMore -> {
                activity?.let { act ->
                    CompanyBottomSheet.newInstance().show(act.supportFragmentManager, "CompanyBottomSheet")
                }
            }
            is LocalCompany -> {
                activity?.let { act ->
                    CompanyBottomSheet.newInstance(model).show(act.supportFragmentManager, "CompanyBottomSheet")
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CompanyFragment()
    }
}
