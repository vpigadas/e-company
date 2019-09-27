package com.vpigadas.e_company.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vpigadas.e_company.R
import com.vpigadas.e_company.model.LocalCompany
import com.vpigadas.e_company.model.LocalModel
import com.vpigadas.e_company.model.LocalMore
import com.vpigadas.e_company.model.LocalUser
import com.vpigadas.e_company.utils.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val streamCompany: MutableLiveData<List<LocalModel>> = MutableLiveData()
    private val streamUsers: MutableLiveData<List<LocalModel>> = MutableLiveData()

    private val job = Job()

    private val dbInstanceCompany = AppDatabase.getInstance(application).getCompanyDao()
    private val dbInstanceUser = AppDatabase.getInstance(application).getUserDao()


    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun getCompanyList() {
        launch(Dispatchers.IO) {
            val list = mutableListOf<LocalModel>()
            list.addAll(0, dbInstanceCompany.getAll())
            list.add(LocalMore(getApplication<Application>().getString(R.string.add_company)))
            streamCompany.postValue(list)
        }
    }

    fun insertCompany(company: LocalCompany) {
        launch(Dispatchers.IO) {
            dbInstanceCompany.insert(company)
        }
    }

    fun insertUser(user: LocalUser) {
        launch(Dispatchers.IO) {
            dbInstanceUser.insert(user)
        }
    }

    fun getUserList() {
        launch(Dispatchers.IO) {
            val list = mutableListOf<LocalModel>()
            list.addAll(0, dbInstanceUser.getAll())
            list.add(LocalMore(getApplication<Application>().getString(R.string.add_user)))
            streamUsers.postValue(list)
        }
    }

    fun getStreamCompany(): LiveData<List<LocalModel>> = streamCompany
    fun getStreamUsers(): LiveData<List<LocalModel>> = streamUsers

    fun destroy(owner: LifecycleOwner) {
        streamCompany.removeObservers(owner)
        streamUsers.removeObservers(owner)
    }
}