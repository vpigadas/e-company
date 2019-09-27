package com.vpigadas.e_company.details.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vpigadas.e_company.model.LocalUser
import com.vpigadas.e_company.utils.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserDetailsViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val streamUser: MutableLiveData<LocalUser> = MutableLiveData()

    private val job = Job()

    private val dbInstanceUser = AppDatabase.getInstance(application).getUserDao()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun getStreamUser(): LiveData<LocalUser> = streamUser

    fun getUser(user: Int) {
        launch(Dispatchers.IO) {
            dbInstanceUser.get(user).let { user ->
                streamUser.postValue(user)
            }
        }
    }

    fun destroy(owner: LifecycleOwner) {
        streamUser.removeObservers(owner)
    }
}