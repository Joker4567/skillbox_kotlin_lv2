package ru.skillbox.presentation_patterns.ui.fragment.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.skillbox.presentation_patterns.R
import ru.skillbox.presentation_patterns.databinding.FragmentSearchBinding
import ru.skillbox.presentation_patterns.ui.fragment.search.adapter.itemSearchCity
import ru.skillbox.presentation_patterns.utils.extension.gone
import ru.skillbox.presentation_patterns.utils.extension.setData
import ru.skillbox.presentation_patterns.utils.extension.show
import ru.skillbox.presentation_patterns.utils.extension.textChangedFlow
import ru.skillbox.presentation_patterns.utils.platform.ViewBindingFragment

@AndroidEntryPoint
class SearchFragment : ViewBindingFragment<FragmentSearchBinding>(
        FragmentSearchBinding::inflate,
        R.layout.fragment_search
) {
    override val statusBarColor: Int = android.R.color.transparent
    override val screenViewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bind()
        updateList()
        subscribe()
    }

    private val searchAdapter by lazy {
        ListDelegationAdapter(
                itemSearchCity(::showCity)
        )
    }

    private fun showCity(city: String) {
        binding.searchSearchView.setQuery(city, true)
    }

    private fun initList() {
        with(binding.searchRecycler) {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun setAdapter(listCity: List<String>) {
        searchAdapter.setData(listCity)
    }

    private fun updateList() {
        val saveCity = screenViewModel.getListCity()
        setAdapter(saveCity)
    }

    private fun subscribe() {
        screenViewModel.weatherStatus.observe(viewLifecycleOwner) { weatherStatus ->
            binding.searchRecycler.gone()
            binding.searchContainerProgress.show()
            binding.searchStatusText.text = weatherStatus
        }
    }

    private fun bind() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.searchSearchView.textChangedFlow()
                    .debounce(500)
                    .distinctUntilChanged()
                    .collect { inputSearch ->
                        if(inputSearch.contains("!")) {
                            val city = inputSearch.trimEnd('!')
                            screenViewModel.saveCity(city)
                            screenViewModel.getCity(city)
                            updateList()
                        } else {
                            binding.searchRecycler.show()
                            binding.searchContainerProgress.gone()
                        }
                        Log.d("LGT_SEARCH", inputSearch)
                    }
        }
    }
}