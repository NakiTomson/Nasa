package com.example.dmiryz.nasa

import android.app.Application
import com.example.dmiryz.nasa.api.NasaService
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration


class App : Application() {

    private lateinit var nasaService: NasaService

    override fun onCreate() {
        super.onCreate()
        nasaService = NasaService()

        val defaultOptions = DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .build()

        val config = ImageLoaderConfiguration.Builder(this)
            .defaultDisplayImageOptions(defaultOptions)
            .memoryCache(LruMemoryCache(10 * 1024 * 1024))
            .memoryCacheSize(10 * 1024 * 1024)
            .build()

        ImageLoader.getInstance().init(config)

    }

    fun getNasaService(): NasaService {
        return nasaService
    }

}