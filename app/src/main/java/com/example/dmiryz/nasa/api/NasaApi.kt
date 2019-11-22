package com.example.dmiryz.nasa.api

import com.example.dmiryz.nasa.models.Dates
import com.example.dmiryz.nasa.models.Photo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface NasaApi {

    @GET("natural/all")
    fun getDatesPhoto():Single<List<Dates>>

    @GET("natural/date/{date}")
    fun getPhotosForDate(@Path("date")date: String?):Single<List<Photo>>

}