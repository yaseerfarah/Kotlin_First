package eg.com.invive.kotlin_first.ViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val classProviderMap: Map<Class<out ViewModel>, ViewModel>) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return classProviderMap[modelClass] as T
    }

}