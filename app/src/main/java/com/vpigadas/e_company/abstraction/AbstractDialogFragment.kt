package com.vpigadas.e_company.abstraction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

abstract class AbstractDialogFragment : DialogFragment() {

    abstract fun getLayout(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout(view)
    }

    abstract fun initLayout(view: View)

    override fun onResume() {
        super.onResume()

        view?.apply { resumeLayout(this) }
    }

    abstract fun resumeLayout(view: View)

    override fun onPause() {
        super.onPause()

        destroyLayout()
    }

    abstract fun destroyLayout()
}