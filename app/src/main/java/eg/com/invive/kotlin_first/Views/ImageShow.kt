package eg.com.invive.kotlin_first.Views

import android.content.Context
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import eg.com.invive.kotlin_first.BroadcastReceiver.NetworkReceiver
import eg.com.invive.kotlin_first.Constants
import eg.com.invive.kotlin_first.POJO.ImageResponse
import eg.com.invive.kotlin_first.R
import eg.com.invive.kotlin_first.Utils.Status
import eg.com.invive.kotlin_first.ViewModels.MainViewModel
import eg.com.invive.kotlin_first.ViewModels.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_image_list.*
import kotlinx.android.synthetic.main.fragment_image_show.*
import org.koin.android.ext.android.inject


class ImageShow : Fragment(R.layout.fragment_image_show) {
    private val viewModelFactory: ViewModelFactory by  inject()
    private val imageResponse by lazy { requireArguments().getParcelable(Constants.IMAGE_RESPONSE) as ImageResponse? }
    private val mainViewModel by  lazy { ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java) }


    val imageListObserve= Observer<Status<List<ImageResponse>>> {
        when(it){
            is Status.Loading -> {}
            is Status.Failed -> {}
            is Status.Success -> { Toast.makeText(context,it.info.size.toString(), Toast.LENGTH_SHORT).show() }

        }
    }



    override fun onStart() {
        super.onStart()
        mainViewModel.imageLiveData.observe(this,imageListObserve)
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.imageLiveData.removeObservers(this)

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageResponse?.let {
            progress.visibility=View.VISIBLE
            showImage.loadImage(requireContext(), it.imageUrl, progress)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {Navigation.findNavController(view).navigateUp()}

    }


    fun ImageView.loadImage(context: Context, url:String, progressBar: ProgressBar){
        Glide.with(context).load(url)
            .apply(RequestOptions.timeoutOf(60*1000))
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    Log.e("Load Image",e!!.message!!)
                    progressBar.visibility= View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    progressBar.visibility= View.GONE
                    return false
                }

            }
            ).into(this)
    }

}