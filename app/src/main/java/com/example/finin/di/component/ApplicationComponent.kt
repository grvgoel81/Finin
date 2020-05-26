package com.example.finin.di.component

import com.example.finin.Application
import com.example.finin.di.module.ActivityBindingModule
import com.example.finin.di.module.ApiModule
import com.example.finin.di.module.ViewModelModule
import com.example.finin.di.scope.AppScope
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        ApiModule::class]
)
interface ApplicationComponent : AndroidInjector<Application> {

    @Component.Factory
    abstract class Builder: AndroidInjector.Factory<Application>
}