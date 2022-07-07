package com.example.rickmortyreddit.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmortyreddit.R
import com.example.rickmortyreddit.databinding.CharacterLayoutBinding
import com.example.rickmortyreddit.databinding.CharacterLayoutNoDataBinding
import com.example.rickmortyreddit.model.CharacterResponse
import com.example.rickmortyreddit.model.CharacterResult
import com.squareup.picasso.Picasso

class CharacterAdapter(
    private var dataset: CharacterResponse?,
    private val callback: CharacterListFragment.OpenDetails
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CharacterViewHolderData(private val binding: CharacterLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(dataItem: CharacterResult, callback: CharacterListFragment.OpenDetails) {
            Picasso.get().load(dataItem.image).into(binding.characterImage)
            binding.characterName.text = dataItem.name
            binding.characterStatus.text = dataItem.status

            binding.root.setOnClickListener { callback.openDetailCharacter(dataItem) }
        }
    }

    class CharacterViewHolderNoData(private val binding: CharacterLayoutNoDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(callback: CharacterListFragment.OpenDetails) {
            binding.characterRetry.setOnClickListener { callback.retryData() }
        }
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return if (dataset != null) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            1 -> CharacterViewHolderData(
                CharacterLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> CharacterViewHolderNoData(
                CharacterLayoutNoDataBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CharacterViewHolderData -> dataset?.results?.get(position)
                ?.let { holder.onBind(it, callback) }
            is CharacterViewHolderNoData -> holder.onBind(callback)
        }
    }

    override fun getItemCount() = dataset?.results?.size ?: 1

    fun setDataSet(dataset: CharacterResponse?) {
        this.dataset?.let {oldData->
            dataset?.let { newData->
                oldData.results.toMutableList().addAll(newData.results)
            }
            return
        }
        this.dataset = dataset
    }
}
