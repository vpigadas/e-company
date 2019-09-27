package com.vpigadas.e_company.insert.company

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import com.vpigadas.e_company.R
import com.vpigadas.e_company.abstraction.AbstractBottomSheetFragment
import com.vpigadas.e_company.home.HomeViewModel
import com.vpigadas.e_company.model.LocalCompany
import kotlinx.android.synthetic.main.fragment_add_company.view.*
import java.util.concurrent.atomic.AtomicInteger

private const val ARG_COMPANY = "parameter_company"

class CompanyBottomSheet : AbstractBottomSheetFragment() {
    private var listener: OnAddCompanyInteractionListener? = null

    private lateinit var viewModel: HomeViewModel
    private val companyId: AtomicInteger = AtomicInteger(-1)

    override fun getLayout(): Int = R.layout.fragment_add_company

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAddCompanyInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnAddCompanyInteractionListener")
        }
    }

    override fun initLayout(view: View) {
        viewModel = when (val act = activity) {
            null -> ViewModelProviders.of(this).get(HomeViewModel::class.java)
            else -> ViewModelProviders.of(act).get(HomeViewModel::class.java)
        }

        view.add_company_save.setOnClickListener { saveCompany(view) }

        view.add_company_first_name_value.addTextChangedListener { view.add_company_first_name.error = null }
        view.add_company_address_value.addTextChangedListener { view.add_company_address.error = null }
        view.add_company_phone_value.addTextChangedListener { view.add_company_phone.error = null }
        view.add_company_speciality_value.addTextChangedListener { view.add_company_speciality.error = null }

        arguments?.getParcelable<LocalCompany>(ARG_COMPANY)?.apply {
            companyId.set(this.id)
            view.add_company_first_name_value.setText(this.name)
            view.add_company_address_value.setText(this.address)
            view.add_company_phone_value.setText(this.phone)
            view.add_company_speciality_value.setText(this.speciality)
        }
    }

    override fun resumeLayout(view: View) {}

    override fun destroyLayout() {}

    private fun saveCompany(view: View) {
        var hasError = false

        val nameValue = view.add_company_first_name_value.text ?: ""
        when (nameValue.isEmpty()) {
            true -> {
                hasError = true
                view.add_company_first_name.error = getString(R.string.error_empty_input)
            }
        }

        val addressValue = view.add_company_address_value.text ?: ""
        when (addressValue.isEmpty()) {
            true -> {
                hasError = true
                view.add_company_address.error = getString(R.string.error_empty_input)
            }
        }

        val phoneValue = view.add_company_phone_value.text ?: ""
        when (addressValue.isEmpty()) {
            true -> {
                hasError = true
                view.add_company_phone.error = getString(R.string.error_empty_input)
            }
        }

        val specialityValue = view.add_company_speciality_value.text ?: ""
        when (specialityValue.isEmpty()) {
            true -> {
                hasError = true
                view.add_company_speciality.error = getString(R.string.error_empty_input)
            }
        }

        when {
            !hasError && companyId.get() < 0 -> {
                viewModel.insertCompany(
                    LocalCompany(
                        name = nameValue.toString(),
                        address = addressValue.toString(),
                        phone = phoneValue.toString(),
                        speciality = specialityValue.toString()
                    )
                )
                dismissAllowingStateLoss()
            }
            !hasError && companyId.get() >= 0 -> {
                viewModel.insertCompany(
                    LocalCompany(
                        id = companyId.get(),
                        name = nameValue.toString(),
                        address = addressValue.toString(),
                        phone = phoneValue.toString(),
                        speciality = specialityValue.toString()
                    )
                )
                dismissAllowingStateLoss()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDismiss(dialog: DialogInterface) {
        listener?.onAddCompanyInteraction()
        super.onDismiss(dialog)
    }

    interface OnAddCompanyInteractionListener {
        fun onAddCompanyInteraction()
    }


    companion object {
        @JvmStatic
        fun newInstance() = CompanyBottomSheet()

        fun newInstance(company: LocalCompany) = CompanyBottomSheet().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_COMPANY, company)
            }
        }
    }
}