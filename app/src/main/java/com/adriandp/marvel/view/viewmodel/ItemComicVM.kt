package com.adriandp.marvel.view.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentTransaction
import com.adriandp.marvel.App
import com.adriandp.marvel.BR
import com.adriandp.marvel.model.Result
import com.adriandp.marvel.view.MainActivity
import com.adriandp.marvel.view.fragment.ComicFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ItemComicVM(
    val description: String, @Bindable var thumbnail: String,
    val comics: Result? = null,
    needRequest: Boolean = false,
    context: Context? = null,
    val isHeroe: Boolean = false
) : BaseObservable() {

    init {
        if (needRequest) {
            val web = thumbnail.split("/")
            App.globalComponent(context!!).getApiService()
                .getElementItem(web[web.size - 2], web[web.size - 1])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ element ->
                    thumbnail = element.data.results[0].thumbnail.path
                    notifyPropertyChanged(BR.thumbnail)
                }, { error ->
                    Log.d(this::class.java.simpleName, Log.getStackTraceString(error))
                })
        }
    }

    fun onClick(context: Context) {
        if (comics == null) return
        val fragmentManager = (context as MainActivity).supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.replace(android.R.id.content, ComicFragment.newInstance(comics))
            .addToBackStack(null).commit()

    }
}
