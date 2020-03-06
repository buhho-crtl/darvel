package com.adriandp.marvel

import android.app.Application
import android.content.Context
import com.adriandp.marvel.config.component.DaggerGlobalComponent
import com.adriandp.marvel.config.component.GlobalComponent
import com.adriandp.marvel.config.module.NetworkModule
import io.reactivex.annotations.NonNull

class App : Application() {

    val globalComponent: GlobalComponent by lazy {
        DaggerGlobalComponent.builder()
            .networkModule(NetworkModule())
            .build()
    }

    companion object {
        fun globalComponent(@NonNull mContext: Context): GlobalComponent {
            return (mContext.applicationContext as App).globalComponent
        }

    }

}