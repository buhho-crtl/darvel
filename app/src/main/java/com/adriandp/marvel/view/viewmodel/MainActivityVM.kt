package com.adriandp.marvel.view.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.adriandp.marvel.App
import com.adriandp.marvel.BR
import com.adriandp.marvel.adapter.AdapterCharacters
import com.adriandp.marvel.config.Constants
import com.adriandp.marvel.view.listener.ElementListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainActivityVM(context: Context) : BaseObservable(), ElementListener {

    private val apiService = App.globalComponent(context).getApiService()
    private var disposeRequest: Disposable? = null
    private var pathRequest: String = Constants.pathUrl.CHARACTERS
    private var offset = 0
    var total = 0
    @Bindable
    val adapterCharacters = AdapterCharacters(this)
    @Bindable
    var loadingItems = true

    init {
        loadMoreItems(true)
    }

    fun loadMoreItems(init: Boolean = false) {
        loadingItems = true
        notifyPropertyChanged(BR.loadingItems)

        disposeRequest = apiService.getCharacters(
            pathRequest,
            offset = offset
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ comics ->
                adapterCharacters.addItems(comics.data.results)
                loadingItems = false
                notifyPropertyChanged(BR.loadingItems)
                offset += 20
                if (init)
                    total = comics.data.total
            }, { error ->
                Log.d(this::class.java.simpleName, Log.getStackTraceString(error))
            })
    }

    override fun onClick(requestPath: String) {
        adapterCharacters.resetList()
        this.pathRequest = requestPath
        offset = 0
        loadMoreItems(true)
    }

}