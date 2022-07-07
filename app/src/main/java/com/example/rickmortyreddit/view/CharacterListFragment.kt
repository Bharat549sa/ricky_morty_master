package com.example.rickmortyreddit.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmortyreddit.RickApplication
import com.example.rickmortyreddit.databinding.CharactersFragmentLayoutBinding
import com.example.rickmortyreddit.model.*
import com.example.rickmortyreddit.viewmodel.CharacterViewModel
import javax.inject.Inject

class CharacterListFragment: Fragment() {

    interface OpenDetails{
        fun openDetailCharacter(data: CharacterResult)
        fun updateLoading(isLoading: Boolean)
        fun retryData()
    }

    @Inject
    lateinit var repository: Repository

    private val viewModel by lazy {
        CharacterViewModel.CharacterViewmodelProvider(repository)
            .create(CharacterViewModel::class.java)
    }

    private val adapter by lazy {
        CharacterAdapter(null, listener)
    }

    private lateinit var listener: OpenDetails

    private lateinit var binding: CharactersFragmentLayoutBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when(context){
            is OpenDetails -> listener = context
            else -> throw  ExceptionInInitializerError("Incorrect Activity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        RickApplication.component.inject(this)
        binding = CharactersFragmentLayoutBinding.inflate(inflater)
        initViews()
        initObservers()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getCharacters()
    }

    private fun initObservers() {
        viewModel.characterLiveData.observe(viewLifecycleOwner){
            when(it){
                is RepositoryImpl.AppState.SUCCESS -> updateAdapter(it.data)
                is RepositoryImpl.AppState.ERROR -> showError(it.errorMessage)
                is RepositoryImpl.AppState.LOADING -> isLoading()
            }
        }
    }

    private fun isLoading() {
        listener.updateLoading(true)
    }

    private fun showError(errorMessage: Data) {
        listener.updateLoading(false)
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun updateAdapter(data: CharacterResponse?) {
        listener.updateLoading(false)
        binding.characterList.adapter = adapter
        adapter.setDataSet(data)
    }

    private fun initViews() {
        binding.characterList.layoutManager = LinearLayoutManager(context)
        binding.characterList.addOnScrollListener(ScrollListener())
    }

    private fun loadMore(nextPage: Int){
        viewModel.getCharacters(nextPage)
    }

    private inner class ScrollListener: RecyclerView.OnScrollListener() {
        private var isLoading = false
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lManager = recyclerView.layoutManager as LinearLayoutManager
            var visibleItemCount = binding.characterList.childCount
            var totalItemCount = lManager.itemCount
            var firstVisibleItemPosition = lManager.findFirstVisibleItemPosition()
            var previousTotal = totalItemCount

            if(isLoading)
                if(totalItemCount >= previousTotal){
                    isLoading = false
                    previousTotal = totalItemCount
                }
//            if(!isLoading){
//                    if(lManager.findLastCompletelyVisibleItemPosition() ==
//                            visibleItemCount){
//                        loadMore(totalItemCount / 20 + 1)
//                        isLoading = true
//                    }
//            }
            if(!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + 5)){
                loadMore(totalItemCount / 20 + 1)
                isLoading = true
            }
        }
    }
}