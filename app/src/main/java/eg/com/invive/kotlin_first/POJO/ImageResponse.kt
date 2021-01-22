package eg.com.invive.kotlin_first.POJO

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageResponse(
    @SerializedName("id")
    val id:Int,
    @SerializedName("Image")
    val imageUrl:String

):Parcelable