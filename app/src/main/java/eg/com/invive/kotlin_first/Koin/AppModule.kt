package eg.com.invive.kotlin_first.Koin

import eg.com.invive.kotlin_first.Constants.BASE_URL
import eg.com.invive.kotlin_first.Data.ImageApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module{

    single { provideRetrofit() }
    single { provideImageApi(get()) }

}

private fun provideRetrofit()= Retrofit.Builder()
    .baseUrl("https://whatsapp.digizone.com.kw/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private fun provideImageApi(retrofit: Retrofit):ImageApi{
    return retrofit.create(ImageApi::class.java)
}

