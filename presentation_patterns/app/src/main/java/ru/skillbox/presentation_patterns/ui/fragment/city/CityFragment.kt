package ru.skillbox.presentation_patterns.ui.fragment.city

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import ru.skillbox.presentation_patterns.R
import ru.skillbox.presentation_patterns.databinding.FragmentCityBinding
import ru.skillbox.presentation_patterns.ui.fragment.search.adapter.itemSearchCity
import ru.skillbox.presentation_patterns.utils.extension.setData
import ru.skillbox.presentation_patterns.utils.platform.ViewBindingFragment

@AndroidEntryPoint
class CityFragment : ViewBindingFragment<FragmentCityBinding>(
        FragmentCityBinding::inflate,
        R.layout.fragment_city
) {

    override val statusBarColor: Int = android.R.color.transparent
    override val screenViewModel by viewModels<CityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screenViewModel.getCityLocalList()
        initList()
        subscribe()
    }

    private val searchAdapter by lazy {
        ListDelegationAdapter(
                itemSearchCity(::showCity)
        )
    }

    private fun showCity(city: String) {
        screenViewModel.navigateDetail(city)
    }

    private fun initList() {
        with(binding.cityRecycler) {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun setAdapter(listCity: List<String>) {
        searchAdapter.setData(listCity)
    }

    private fun subscribe() {
        screenViewModel.weatherList.observe(viewLifecycleOwner) { listArrCity ->
            listArrCity?.let {
                setAdapter(listArrCity)
            }
        }
    }
}