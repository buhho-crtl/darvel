package com.adriandp.marvel.config.component

import com.adriandp.marvel.config.module.NetworkModule
import com.adriandp.marvel.core.service.APIService

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface GlobalComponent {
    fun getApiService(): APIService
}