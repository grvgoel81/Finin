package com.example.finin.di.module

import com.example.finin.ui.fragment.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentProviderModule {

    @ContributesAndroidInjector
    abstract fun provideUserFragment(): UserFragment
}