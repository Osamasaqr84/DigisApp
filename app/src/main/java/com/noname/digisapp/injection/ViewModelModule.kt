package com.noname.digisapp.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.noname.digisapp.presentation.viewmodel.MainViewModel
import com.noname.digisapp.presentation.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass


@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory?): ViewModelProvider.Factory?

    /*
     * This method basically says
     * inject this object into a Map using the @IntoMap annotation,
     * with the  MainViewModel.class as key,
     * and a Provider that will build a MainViewModel
     * object.
     *
     * */
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    protected abstract fun movieListViewModel(moviesListViewModel: MainViewModel?): ViewModel?
}
@MapKey
@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)