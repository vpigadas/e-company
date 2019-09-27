package com.vpigadas.e_company.abstraction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class AbstractActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
    }

    abstract fun getLayout(): Int

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initLayout()
    }

    abstract fun initLayout()

    override fun onPostResume() {
        super.onPostResume()

        resumeLayout()
    }

    abstract fun resumeLayout()

    override fun onPause() {
        super.onPause()

        destroyLayout()
    }

    abstract fun destroyLayout()
}