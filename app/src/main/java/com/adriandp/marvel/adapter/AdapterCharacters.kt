package com.adriandp.marvel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.adriandp.marvel.BR
import com.adriandp.marvel.R
import com.adriandp.marvel.databinding.ItemChipSelectedBinding
import com.adriandp.marvel.databinding.ItemElementBinding
import com.adriandp.marvel.model.Item
import com.adriandp.marvel.model.Result
import com.adriandp.marvel.view.listener.ElementListener
import com.adriandp.marvel.view.viewmodel.ItemChipHolder
import com.adriandp.marvel.view.viewmodel.ItemComicVM

private const val CHIP = 0
private const val ELEMENT = 1
private const val COMIC = 2

class AdapterCharacters(private val elementListener: ElementListener, needChips: Boolean = true) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listComics = mutableListOf<Any>()

    init {
        if (needChips) listComics.add(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CHIP -> ChipHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_chip_selected,
                    parent,
                    false
                )
            )
            ELEMENT -> ResultHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_element,
                    parent,
                    false
                )
            )
            else -> ResultHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_element,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return listComics.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (listComics[position]) {
            is Boolean -> CHIP
            is Result, is Item -> ELEMENT
            else -> COMIC
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ChipHolder) {
            holder.mBinding.setVariable(
                BR.model,
                ItemChipHolder(listComics[position] as Boolean, elementListener)
            )
            holder.mBinding.executePendingBindings()
        } else if (holder is ResultHolder) {
            if (listComics[position] is Result) {
                val element = listComics[position] as Result
                holder.mBinding.setVariable(
                    BR.model,
                    ItemComicVM(
                        element.name ?: element.title,
                        element.thumbnail.path,
                        element,
                        isHeroe = element.name != null
                    )
                )
            } else {
                val element = listComics[position] as Item
                holder.mBinding.setVariable(
                    BR.model,
                    ItemComicVM(
                        element.name,
                        element.resourceURI,
                        needRequest = true,
                        context = holder.mBinding.root.context,
                        isHeroe = element.heroe
                    )
                )
            }
            holder.mBinding.executePendingBindings()
        }
    }

    fun addItems(comics: List<Any>) {
        val lastPosition = listComics.size
        listComics.addAll(comics)
        notifyItemRangeInserted(lastPosition, listComics.size)
    }

    fun resetList() {
        val totalList = listComics.size
        listComics.removeAll { it !is Boolean }
        notifyItemRangeRemoved(1, totalList)
    }

    class ChipHolder(inflate: View) : RecyclerView.ViewHolder(inflate) {
        val mBinding: ItemChipSelectedBinding = DataBindingUtil.bind(itemView)!!
    }

    class ResultHolder(inflate: View) : RecyclerView.ViewHolder(inflate) {
        val mBinding: ItemElementBinding = DataBindingUtil.bind(itemView)!!
    }

}
