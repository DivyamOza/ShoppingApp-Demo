package com.divyamoza.assesmentdemo.data

import com.divyamoza.assesmentdemo.BuildConfig
import com.divyamoza.assesmentdemo.base.AssesmentDemoApp
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.*
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


object ApiClient {
    val service: ApiService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        getRetrofit().create(ApiService::class.java)
    }


    /**
     * Get retrofit
     *
     * @return
     */
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    /**
     * Get ok http client
     *
     * @return
     */
    private fun getOkHttpClient(): OkHttpClient {
        val loggingInterceptor = LoggingInterceptor.Builder()
            //.loggable(BuildConfig.DEBUG)
            .setLevel(Level.BODY)
            .log(Platform.INFO)
            .request(ApiConstants.REQUEST)
            .response(ApiConstants.RESPONSE)
            .addHeader(ApiConstants.VERSION, BuildConfig.VERSION_NAME)
            .build()
        val cacheFile = File(AssesmentDemoApp.context.cacheDir, ApiConstants.CACHE)
        val cache = Cache(cacheFile, 1024 * 1024 * 50)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(getHeaderInterceptor())
            .addInterceptor(ErrorInterceptor())
            .cache(cache)
            .cookieJar(APICookieJar())
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .build()
    }


    /**
     * Get header interceptor
     *
     * @return
     */
    private fun getHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request =
                chain.request().newBuilder()
                    .header(ApiConstants.HEADER_ACCEPT, ApiConstants.ACCEPT_VALUE)
                    .build()
            chain.proceed(request)
        }
    }


    /**
     * API cookie jar
     *
     * @constructor Create empty A p i cookie jar
     */
    private class APICookieJar : CookieJar {

        private val cookies = mutableListOf<Cookie>()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            this.cookies.clear()
            this.cookies.addAll(cookies)
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> =
            cookies
    }
}