package eg.com.invive.kotlin_first.Data

import eg.com.invive.kotlin_first.POJO.ArrayBaseResponse
import eg.com.invive.kotlin_first.POJO.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageApi {

    @GET("categorys/{id}")
    suspend fun getImageListByID(@Path("id")  id:Int) : ArrayBaseResponse<ImageResponse>

}