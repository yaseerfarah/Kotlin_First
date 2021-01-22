package eg.com.invive.kotlin_first.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.com.invive.kotlin_first.Data.ImageApi
import eg.com.invive.kotlin_first.POJO.ImageResponse
import eg.com.invive.kotlin_first.Utils.Status
import kotlinx.coroutines.launch

class MainViewModel(private val context: Context,private val imageApi:ImageApi):ViewModel() {

    private val _imageLiveData= MutableLiveData<Status<List<ImageResponse>>>()
    val imageLiveData: LiveData<Status<List<ImageResponse>>>
    get() = _imageLiveData



     fun getImage(){

        _imageLiveData.postValue(Status.Loading())

        viewModelScope.launch {
            val result= kotlin.runCatching { imageApi.getImageListByID(41) }

           result.onSuccess { _imageLiveData.postValue(Status.Success(it.arrayResponse)) }
            result.onFailure {  _imageLiveData.postValue(Status.Failed(it.message?:"Failed")) }


        }

    }


    override fun onCleared() {
        super.onCleared()
        Toast.makeText(context,"clear",Toast.LENGTH_SHORT).show()
    }
}