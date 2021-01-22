package eg.com.invive.kotlin_first

import android.app.Application
import eg.com.invive.kotlin_first.Koin.appModule
import eg.com.invive.kotlin_first.Koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, viewModelModule))
        }
    }
}