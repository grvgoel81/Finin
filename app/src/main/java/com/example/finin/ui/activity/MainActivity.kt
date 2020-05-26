package com.example.finin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finin.R
import com.example.finin.base.BaseActivity
import com.example.finin.ui.fragment.UserFragment

class MainActivity : BaseActivity() {

    override fun getLayoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openUserFragment()
    }

    private fun openUserFragment() {
        replaceFragment(R.id.container, UserFragment.newInstance(), UserFragment.TAG)
    }
}
