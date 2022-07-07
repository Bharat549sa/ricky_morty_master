package com.example.rickmortyreddit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rickmortyreddit.databinding.CharacterDetailsFragmentBinding
import com.example.rickmortyreddit.model.CharacterResult
import com.squareup.picasso.Picasso

class CharacterDetailsFragment: Fragment() {

    companion object{
        private const val KEY_DATA_ITEM: String = "CharacterDetailsFragment_KEY_DATA_ITEM"

        fun newInstance(detailItem: CharacterResult): CharacterDetailsFragment{
            return CharacterDetailsFragment().apply {
                arguments =
                    Bundle().apply {
                        putParcelable(KEY_DATA_ITEM, detailItem)
                    }
            }
        }
    }

    private lateinit var binding: CharacterDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = CharacterDetailsFragmentBinding.inflate(inflater)
        setupViews()
        return binding.root
    }

    private fun setupViews() {
        arguments?.getParcelable<CharacterResult>(KEY_DATA_ITEM)?.let {
            binding.characterDetailsGender.text = it.gender
            binding.characterDetailsLocation.text = it.location.name
            binding.characterDetailsName.text = it.name
            binding.characterDetailsOrigin.text = it.origin.name
            binding.characterDetailsSpecies.text = it.species
            binding.characterDetailsStatus.text = it.status
            Picasso.get().load(it.image).into(binding.characterDetailsImage)
        }
        binding.closeDetails.setOnClickListener{
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}