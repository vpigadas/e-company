package com.vpigadas.e_company.insert.user

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vpigadas.e_company.R
import com.vpigadas.e_company.abstraction.AbstractBottomSheetFragment
import com.vpigadas.e_company.home.HomeViewModel
import com.vpigadas.e_company.model.LocalCompany
import com.vpigadas.e_company.model.LocalUser
import kotlinx.android.synthetic.main.fragment_add_user.view.*
import java.util.concurrent.atomic.AtomicInteger

private const val ARG_USER = "parameter_user"

class UserBottomSheet : AbstractBottomSheetFragment() {
    private lateinit var viewModel: HomeViewModel

    private var listener: OnAddUserInteractionListener? = null
    private val userId: AtomicInteger = AtomicInteger(-1)

    override fun getLayout(): Int = R.layout.fragment_add_user

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAddUserInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnAddUserInteractionListener")
        }
    }

    override fun initLayout(view: View) {
        viewModel = when (val act = activity) {
            null -> ViewModelProviders.of(this).get(HomeViewModel::class.java)
            else -> ViewModelProviders.of(act).get(HomeViewModel::class.java)
        }

        view.add_user_save.setOnClickListener { saveUser(view) }

        view.add_user_first_name_value.addTextChangedListener { view.add_user_first_name.error = null }
        view.add_user_last_name_value.addTextChangedListener { view.add_user_last_name.error = null }
        view.add_user_address_value.addTextChangedListener { view.add_user_address.error = null }
        view.add_user_phone_value.addTextChangedListener { view.add_user_phone.error = null }
        view.add_user_speciality_value.addTextChangedListener { view.add_user_speciality.error = null }

        arguments?.getParcelable<LocalUser>(ARG_USER)?.apply {
            userId.set(this.id)
            view.add_user_first_name_value.setText(this.firstName)
            view.add_user_last_name_value.setText(this.lastName)
            view.add_user_address_value.setText(this.address)
            view.add_user_phone_value.setText(this.phone)
            view.add_user_speciality_value.setText(this.speciality)
        }
    }

    override fun resumeLayout(view: View) {
        viewModel.getStreamCompany().observe(this, Observer { list ->
            val adapter = ArrayAdapter(
                view.context,
                android.R.layout.simple_spinner_item,
                list.filterIsInstance<LocalCompany>().map { (it).name }.toMutableList()
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            view.add_user_company.setAdapter(adapter)
        })
        viewModel.getCompanyList()
    }

    override fun destroyLayout() {
        viewModel.destroy(this)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDismiss(dialog: DialogInterface) {
        listener?.onAddUserInteraction()
        super.onDismiss(dialog)
    }

    private fun saveUser(view: View) {
        var hasError = false

        val firstNameValue = view.add_user_first_name_value.text ?: ""
        when (firstNameValue.isEmpty()) {
            true -> {
                hasError = true
                view.add_user_first_name.error = getString(R.string.error_empty_input)
            }
        }

        val lastNameValue = view.add_user_last_name_value.text ?: ""
        when (lastNameValue.isEmpty()) {
            true -> {
                hasError = true
                view.add_user_last_name.error = getString(R.string.error_empty_input)
            }
        }

        val addressValue = view.add_user_address_value.text ?: ""
        when (addressValue.isEmpty()) {
            true -> {
                hasError = true
                view.add_user_address.error = getString(R.string.error_empty_input)
            }
        }

        val phoneValue = view.add_user_phone_value.text ?: ""
        when (phoneValue.isEmpty()) {
            true -> {
                hasError = true
                view.add_user_phone.error = getString(R.string.error_empty_input)
            }
        }

        val specialityValue = view.add_user_speciality_value.text ?: ""
        when (specialityValue.isEmpty()) {
            true -> {
                hasError = true
                view.add_user_speciality.error = getString(R.string.error_empty_input)
            }
        }

        var companyValue = ""
        when (val company = viewModel.getStreamCompany().value
            ?.getOrNull(view.add_user_company.selectedItemPosition)) {
            null -> {
                hasError = true
            }
            is LocalCompany -> {
                companyValue = company.name
            }
        }

        when {
            !hasError && userId.get() < 0 -> {
                viewModel.insertUser(
                    LocalUser(
                        firstName = firstNameValue.toString(),
                        lastName = lastNameValue.toString(),
                        phone = phoneValue.toString(),
                        address = addressValue.toString(),
                        speciality = specialityValue.toString(),
                        company = companyValue
                    )
                )
                dismissAllowingStateLoss()
            }
            !hasError && userId.get() >= 0 -> {
                viewModel.insertUser(
                    LocalUser(
                        id = userId.get(),
                        firstName = firstNameValue.toString(),
                        lastName = lastNameValue.toString(),
                        phone = phoneValue.toString(),
                        address = addressValue.toString(),
                        speciality = specialityValue.toString(),
                        company = companyValue
                    )
                )
                dismissAllowingStateLoss()
            }
        }
    }

    interface OnAddUserInteractionListener {
        fun onAddUserInteraction()
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserBottomSheet()

        fun newInstance(user: LocalUser) = UserBottomSheet().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_USER, user)
            }
        }
    }
}