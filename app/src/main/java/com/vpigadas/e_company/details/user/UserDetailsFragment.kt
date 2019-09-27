package com.vpigadas.e_company.details.user

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vpigadas.e_company.R
import com.vpigadas.e_company.abstraction.AbstractDialogFragment
import com.vpigadas.e_company.insert.user.UserBottomSheet
import kotlinx.android.synthetic.main.fragment_user_details.view.*

private const val ARG_USER = "parameter_user"

class UserDetailsFragment : AbstractDialogFragment() {

    private lateinit var viewModel: UserDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getLayout(): Int = R.layout.fragment_user_details

    override fun initLayout(view: View) {
        viewModel = when (val act = activity) {
            null -> ViewModelProviders.of(this).get(UserDetailsViewModel::class.java)
            else -> ViewModelProviders.of(act).get(UserDetailsViewModel::class.java)
        }

        view.user_details_edit.setOnClickListener {
            viewModel.getStreamUser().value?.apply {
                activity?.let { act ->
                    UserBottomSheet.newInstance(this).show(act.supportFragmentManager, "UserBottomSheet")
                }
            }
        }
    }

    override fun resumeLayout(view: View) {
        viewModel.getStreamUser().observe(this, Observer {
            view.user_details_name.text = it.getName()
            view.user_details_address.text = it.address
            view.user_details_phone.text = it.phone
            view.user_details_speciality.text = it.speciality
            view.user_details_company.text = it.company
        })
        arguments?.let {
            viewModel.getUser(it.getInt(ARG_USER, -1))
        }

    }

    override fun destroyLayout() {}

    companion object {
        @JvmStatic
        fun newInstance(userId: Int) =
            UserDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_USER, userId)
                }
            }
    }
}
