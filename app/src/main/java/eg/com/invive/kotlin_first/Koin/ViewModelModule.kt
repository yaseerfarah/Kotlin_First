package eg.com.invive.kotlin_first.Koin

import android.content.Context
import androidx.lifecycle.ViewModel
import eg.com.invive.kotlin_first.Data.ImageApi
import eg.com.invive.kotlin_first.ViewModels.MainViewModel
import eg.com.invive.kotlin_first.ViewModels.ViewModelFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule= module {

   // viewModel { MainViewModel(context = androidContext(),imageApi = get()) }
    single { provideViewModel(androidContext(),get()) }
    single { viewModelMap(get()) }
    single { viewModelFactory(get()) }
}

private fun provideViewModel( context: Context, imageApi: ImageApi):MainViewModel{
    return MainViewModel(context,imageApi)
}

private fun viewModelMap(mainViewModel: MainViewModel): Map<Class<out ViewModel>, ViewModel>{
    return mapOf(MainViewModel::class.java to mainViewModel)
}

private fun viewModelFactory(map:Map<Class<out ViewModel>, ViewModel>):ViewModelFactory{
    return ViewModelFactory(map)
}