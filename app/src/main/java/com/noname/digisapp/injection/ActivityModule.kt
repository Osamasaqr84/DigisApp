package com.noname.digisapp.injection

import com.noname.digisapp.presentation.view.MainActivity
import dagger.Module

import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}