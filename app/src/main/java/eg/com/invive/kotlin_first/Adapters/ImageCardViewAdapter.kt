package eg.com.invive.kotlin_first.Adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import eg.com.invive.kotlin_first.Interface.OnItemClicked
import eg.com.invive.kotlin_first.POJO.ImageResponse
import eg.com.invive.kotlin_first.R

class ImageCardViewAdapter(private val context: Context, private val imageList: List<ImageResponse>, private val onItemClicked: (ImageResponse) -> Unit):
    RecyclerView.Adapter<ImageCardViewAdapter.ImageViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageCardViewAdapter.ImageViewHolder =
        ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
            R.layout.item_cardview, parent,
            false
        ))

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageCardViewAdapter.ImageViewHolder, position: Int) {
        holder.bind(imageList[holder.adapterPosition],onItemClicked)
    }




    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(imageResponse: ImageResponse,onItemClicked: (ImageResponse) -> Unit){
            val progress= itemView.findViewById<ProgressBar>(R.id.progress)
           progress.visibility=View.VISIBLE
            itemView.findViewById<ImageView>(R.id.image_view).loadImage(itemView.context,imageResponse.imageUrl,progress)

            itemView.findViewById<CardView>(R.id.card_view).setOnClickListener { onItemClicked(imageResponse) }

        }

        fun ImageView.loadImage(context:Context,url:String,progressBar: ProgressBar){
            Glide.with(context).load(url)
                .apply(RequestOptions.timeoutOf(60*1000))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        Log.e("Load Image",e!!.message!!)
                        progressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        progressBar.visibility=View.GONE
                        return false
                    }

                }
                ).into(this)
        }

    }


}


