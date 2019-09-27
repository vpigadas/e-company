package com.vpigadas.e_company.home

import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.vpigadas.e_company.R
import com.vpigadas.e_company.abstraction.AbstractActivity
import com.vpigadas.e_company.home.fragment.company.CompanyFragment
import com.vpigadas.e_company.home.fragment.user.UserFragment
import com.vpigadas.e_company.insert.company.CompanyBottomSheet
import com.vpigadas.e_company.insert.user.UserBottomSheet
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AbstractActivity(), UserBottomSheet.OnAddUserInteractionListener,
    CompanyBottomSheet.OnAddCompanyInteractionListener {

    private lateinit var viewModel: HomeViewModel
    private val companyFragment: Fragment = CompanyFragment.newInstance()
    private val userFragment: Fragment = UserFragment.newInstance()

    private var active = companyFragment

    override fun getLayout(): Int = R.layout.activity_main

    override fun initLayout() {
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_menu_company -> {
                    supportFragmentManager.beginTransaction().hide(active).show(companyFragment).commit()
                    active = companyFragment
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_menu_users -> {
                    supportFragmentManager.beginTransaction().hide(active).show(userFragment).commit()
                    active = userFragment
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
        bottom_navigation.selectedItemId = R.id.bottom_menu_company

        supportFragmentManager
            .beginTransaction()
            .add(R.id.home_frame_container, userFragment, UserFragment::class.java.simpleName)
            .hide(userFragment)
            .add(R.id.home_frame_container, companyFragment, CompanyFragment::class.java.simpleName)
            .commit()
    }

    override fun resumeLayout() {}

    override fun destroyLayout() {
        viewModel.destroy(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() = when (bottom_navigation.selectedItemId) {
        R.id.bottom_menu_company -> {
            super.onBackPressed()
        }
        R.id.bottom_menu_users -> {
            bottom_navigation.selectedItemId = R.id.bottom_menu_company
        }
        else -> super.onBackPressed()
    }

    override fun onAddUserInteraction() {
        viewModel.getUserList()
    }

    override fun onAddCompanyInteraction() {
        viewModel.getCompanyList()
    }
}
