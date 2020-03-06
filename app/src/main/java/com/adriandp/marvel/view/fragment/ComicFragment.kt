package com.adriandp.marvel.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.adriandp.marvel.BR
import com.adriandp.marvel.R
import com.adriandp.marvel.databinding.DataDetailBinding
import com.adriandp.marvel.model.Result
import com.adriandp.marvel.view.viewmodel.ComicFragmentVM

/**
 * A simple [Fragment] subclass.
 */
class ComicFragment : DialogFragment() {

    private lateinit var binding: DataDetailBinding

    companion object {
        fun newInstance(result: Result) = ComicFragment().apply { arguments = Bundle().apply { putSerializable("comics",result) } }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val data :Result = arguments?.getSerializable("comics") as Result

        binding = DataBindingUtil.inflate(inflater, R.layout.data_detail, container, false)
        binding.setVariable(BR.model,ComicFragmentVM(data))
        binding.executePendingBindings()

        return binding.root
    }


}
