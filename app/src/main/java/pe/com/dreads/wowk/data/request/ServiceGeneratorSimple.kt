package pe.com.dreads.wowk.data.request

import okhttp3.logging.HttpLoggingInterceptor
import pe.com.dreads.wowk.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Dreads on 19/11/2017.
 */
object ServiceGeneratorSimple {

    val API_BASE_URL = BuildConfig.BASE

    private  val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())



    fun <S> createServiceRx(serviceClass: Class<S>) : S{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val retrofit = builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        return retrofit.create(serviceClass)
    }
}