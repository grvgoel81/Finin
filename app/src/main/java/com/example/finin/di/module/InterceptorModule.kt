package com.example.finin.di.module

import android.util.Log
import com.example.finin.Application
import com.example.finin.api.ApiConstant.Companion.HEADER_CACHE_CONTROL
import com.example.finin.api.ApiConstant.Companion.HEADER_PRAGMA
import com.example.finin.di.scope.AppScope
import com.example.finin.utils.ApplicationUtil
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

@Module
class InterceptorModule {

    @Provides
    @AppScope
    fun provideOkHttpClient(
        context: Application,
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { provideOfflineCacheInterceptor(context, it) }
            .addNetworkInterceptor { provideCacheInterceptor(context, it) }
            .cache(provideCache(context))
            .build()
    }


    @Provides
    @AppScope
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }


    private fun provideCache(context: Application): Cache? {
        var cache: Cache? = null
        try {
            cache = Cache(File(context.cacheDir, "http-cache"), 10 * 1024 * 1024)
        } catch (e: Exception) {
            Log.e("Cache", "Could not create Cache!")
        }
        return cache
    }

    private fun provideOfflineCacheInterceptor(
        context: Application,
        chain: Interceptor.Chain
    ): Response {
        var request = chain.request()

        if (!ApplicationUtil.hasNetwork(context)) {
            val cacheControl = CacheControl.Builder()
                .maxStale(1, TimeUnit.DAYS)
                .build()

            request = request.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .cacheControl(cacheControl)
                .build()
        }

        return chain.proceed(request)
    }

    private fun provideCacheInterceptor(
        context: Application,
        chain: Interceptor.Chain
    ): Response {
        val response = chain.proceed(chain.request())
        val cacheControl: CacheControl = if (ApplicationUtil.hasNetwork(context)) {
            CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build()
        } else {
            CacheControl.Builder()
                .maxStale(1, TimeUnit.DAYS)
                .build()
        }

        return response.newBuilder()
            .removeHeader(HEADER_PRAGMA)
            .removeHeader(HEADER_CACHE_CONTROL)
            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
            .build()
    }
}