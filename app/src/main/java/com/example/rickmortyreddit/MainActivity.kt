package com.example.rickmortyreddit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.rickmortyreddit.databinding.ActivityMainBinding
import com.example.rickmortyreddit.model.CharacterResult
import com.example.rickmortyreddit.view.CharacterDetailsFragment
import com.example.rickmortyreddit.view.CharacterListFragment

class MainActivity : AppCompatActivity(), CharacterListFragment.OpenDetails {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null)
            initViews(false)
    }

    private fun initViews(retry: Boolean) {
        if (retry)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CharacterListFragment())
                .commit()
        else
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, CharacterListFragment())
                .commit()
    }

    override fun openDetailCharacter(data: CharacterResult) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CharacterDetailsFragment.newInstance(data))
            .addToBackStack(null)
            .commit()
    }

    override fun updateLoading(isLoading: Boolean) {
        if (isLoading) binding.loading.visibility = View.VISIBLE
        else binding.loading.visibility = View.GONE
    }

    override fun retryData() {
        initViews(true)
    }
}