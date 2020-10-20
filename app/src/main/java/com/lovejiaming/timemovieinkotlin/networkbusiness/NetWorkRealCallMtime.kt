package com.lovejiaming.timemovieinkotlin.networkbusiness

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by xiaoxin on 2017/8/24.
 *
 */
@Suppress("SENSELESS_COMPARISON")
class NetWorkRealCallMtime private constructor() {
    //retrofit实例
    private var mRetrofit: Retrofit

    //主构造初始化块
    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        // builder.addInterceptor(interceptor)
        val okhttpClient = OkHttpClient.Builder().addInterceptor(interceptor).
                connectTimeout(6000, TimeUnit.MILLISECONDS)
                .readTimeout(6000, TimeUnit.MILLISECONDS)
                .writeTimeout(6000, TimeUnit.MILLISECONDS).build()
        mRetrofit = Retrofit.Builder().
                baseUrl(BASE_URL).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).
                client(okhttpClient).
                build()
    }

    //
    fun getHotMovieService() = mRetrofit.create(IHotMovieService::class.java)!!

    //
    fun getMovieDetailService() = mRetrofit.create(IMovieDetailService::class.java)!!

    //
    fun getPersonDetailService() = mRetrofit.create(IMoviePersonDetailService::class.java)!!

    //
    fun getFindFunnyService() = mRetrofit.create(IFindFunnyService::class.java)!!

    //
    fun getRankListService() = mRetrofit.create(IRankListService::class.java)!!

    fun getSearchMovieService() = mRetrofit.create(ISearchMovieService::class.java)!!

    //single instance
    companion object {
        //
        var BASE_URL = "https://api-m.mtime.cn/"
        //
        val OBJECT_INSTANCE: NetWorkRealCallMtime by lazy {
            NetWorkRealCallMtime()
        }

        fun newInstance(): NetWorkRealCallMtime {
            return OBJECT_INSTANCE
        }
    }

}