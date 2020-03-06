package com.adriandp.marvel.core.service

import com.adriandp.marvel.model.ResultRaw
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("{path}")
    fun getCharacters(@Path("path") path:String, @Query("offset")offset:Int = 0, @Query("limit")limit:Int= 20): Observable<ResultRaw>

    @GET("{path}/{idItem}")
    fun getElementItem(@Path("path") path:String,@Path("idItem") idItem:String?=null, @Query("offset")offset:Int = 0, @Query("limit")limit:Int= 20): Observable<ResultRaw>
}