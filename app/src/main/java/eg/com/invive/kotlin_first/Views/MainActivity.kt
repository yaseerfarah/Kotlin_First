package eg.com.invive.kotlin_first.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eg.com.invive.kotlin_first.Koin.viewModelModule
import eg.com.invive.kotlin_first.R
import eg.com.invive.kotlin_first.ViewModels.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

//private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }




}