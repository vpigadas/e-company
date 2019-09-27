package com.vpigadas.e_company.home.fragment.user

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vpigadas.e_company.R
import com.vpigadas.e_company.abstraction.AbstractFragment
import com.vpigadas.e_company.details.user.UserDetailsFragment
import com.vpigadas.e_company.home.HomeViewModel
import com.vpigadas.e_company.insert.user.UserBottomSheet
import com.vpigadas.e_company.listeners.ItemViewClickListener
import com.vpigadas.e_company.model.LocalModel
import com.vpigadas.e_company.model.LocalMore
import com.vpigadas.e_company.model.LocalUser
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : AbstractFragment(), ItemViewClickListener {

    private lateinit var viewModel: HomeViewModel

    override fun getLayout(): Int = R.layout.fragment_user

    override fun initLayout(view: View) {
        viewModel = when (val act = activity) {
            null -> ViewModelProviders.of(this).get(HomeViewModel::class.java)
            else -> ViewModelProviders.of(act).get(HomeViewModel::class.java)
        }
    }

    override fun resumeLayout(view: View) {
        viewModel.getStreamUsers().observe(this, Observer {
            when (val adapter = recycler_user.adapter) {
                is UserAdapter -> adapter.submitList(it)
                else -> recycler_user.adapter = UserAdapter(this).apply { submitList(it) }
            }
        })
        viewModel.getUserList()
    }

    override fun destroyLayout() {}

    override fun onItemClick(model: LocalModel) {
        when (model) {
            is LocalMore -> {
                activity?.let { act ->
                    UserBottomSheet.newInstance().show(act.supportFragmentManager, "UserBottomSheet")
                }
            }
            is LocalUser -> {
                activity?.let { act ->
                    UserDetailsFragment.newInstance(model.id).show(act.supportFragmentManager, "UserDetailsFragment")
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserFragment()
    }
}
