package com.noname.digisapp

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.multidex.MultiDexApplication
import com.noname.digisapp.injection.AppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerCompo
            .builder()
            .application(this)
            .build()
            .inject(this)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setupDagger()

    }


    private fun setupDagger() {
//        DaggerAppComponent
//            .builder()
//            .application(this)
//            .build()
//            .inject(this)
    }

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }


    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

}
val Activity.injector get() = (application as App).component
val Fragment.injector get() = (context as App).component
val FragmentActivity.injector get() = (application as App).component
