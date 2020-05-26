package com.example.finin.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
    }

    fun replaceFragment(
        container: Int,
        fragment: Fragment,
        tag: String,
        addToBackStack: Boolean = false
    ) {

        if (addToBackStack) {
            supportFragmentManager.beginTransaction()
                .replace(container, fragment, tag)
                .addToBackStack(tag)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(container, fragment, tag)
                .commit()
        }

    }

    abstract fun getLayoutRes(): Int
}