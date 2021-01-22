package eg.com.invive.kotlin_first.Views

import android.content.IntentFilter
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import eg.com.invive.kotlin_first.Adapters.ImageCardViewAdapter
import eg.com.invive.kotlin_first.BroadcastReceiver.NetworkReceiver
import eg.com.invive.kotlin_first.Constants
import eg.com.invive.kotlin_first.Interface.NetworkStatus
import eg.com.invive.kotlin_first.POJO.ImageResponse
import eg.com.invive.kotlin_first.R
import eg.com.invive.kotlin_first.Utils.ItemDecoration
import eg.com.invive.kotlin_first.Utils.Status
import eg.com.invive.kotlin_first.ViewModels.MainViewModel
import eg.com.invive.kotlin_first.ViewModels.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_image_list.*
import org.koin.android.ext.android.inject

class ImageList : Fragment(R.layout.fragment_image_list),NetworkStatus {

    private val viewModelFactory:ViewModelFactory by  inject()
    private val mainViewModel by  lazy { ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java) }
    private lateinit var navController:NavController
    private val imageCardViewAdapter: ImageCardViewAdapter by lazy { ImageCardViewAdapter(requireContext(),imageList) {
        val bundle= bundleOf(Constants.IMAGE_RESPONSE to it)
        navController.navigate(R.id.action_global_imageShow,bundle)
    } }
    private val imageList= mutableListOf<ImageResponse>()
    private val networkReceiver=NetworkReceiver(this)

    val imageListObserve= Observer<Status<List<ImageResponse>>> {
        when(it){
           is Status.Loading -> {statefulLayout.showLoading()}
            is Status.Failed -> {statefulLayout.showError("ooooops", View.OnClickListener {  })}
            is Status.Success -> {
                if (it.info.isNotEmpty()){
                    imageList.clear()
                    imageList.addAll(it.info)
                    imageCardViewAdapter.notifyDataSetChanged()
                    statefulLayout.showContent()
                }else{
                    statefulLayout.showEmpty()
                }
               }

        }
    }

    override fun onStart() {
        super.onStart()
        val netFilter = IntentFilter()
        netFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        requireActivity().registerReceiver(networkReceiver, netFilter)

        mainViewModel.imageLiveData.observe(this,imageListObserve)

    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(networkReceiver)
        mainViewModel.imageLiveData.removeObservers(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController=Navigation.findNavController(view)

        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

        val displayWidth = displayMetrics.widthPixels

        recyclerView.apply {
            layoutManager=GridLayoutManager(context,2)
            adapter=imageCardViewAdapter
            addItemDecoration(ItemDecoration(2, ItemDecoration.dpToPx(5,resources),displayWidth,resources.getDimensionPixelSize(R.dimen._140sdp)))
        }

    }

    override fun connect() {
       if (imageList.isEmpty()){
           mainViewModel.getImage()
       }else{
           statefulLayout.showContent()
       }
    }

    override fun notConnect() {
        statefulLayout.showError("oooops check your Connection", View.OnClickListener {  })
    }
}