package com.adriandp.marvel.view.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.adriandp.marvel.adapter.AdapterCharacters
import com.adriandp.marvel.model.Item
import com.adriandp.marvel.model.Result
import com.adriandp.marvel.view.listener.ElementListener
import com.google.gson.internal.LinkedTreeMap


class ComicFragmentVM(val data: Result) :BaseObservable(), ElementListener {

    @Bindable
    val adapterCharacters = AdapterCharacters(this,false)

    init {
        val list = if(data.comics==null){
            val list = mutableListOf<Item>()
            data.characters.items.forEach { item->
                list.add(Item(item.resourceURI,item.name,true))
            }
            list.toList()
        }else {
            val list = mutableListOf<Item>()
            data.comics.items.forEach { item->
                item as LinkedTreeMap<*,*>
                val thumbnail = item["resourceURI"] as String
                val name = item["name"] as String
                list.add(Item(thumbnail,name,false))
            }
            list.toList()
        }

        adapterCharacters.addItems(list)
    }

    override fun onClick(requestPath: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}