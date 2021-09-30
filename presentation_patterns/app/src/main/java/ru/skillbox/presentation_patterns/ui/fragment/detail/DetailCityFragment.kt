package ru.skillbox.presentation_patterns.ui.fragment.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import ru.skillbox.presentation_patterns.R
import ru.skillbox.presentation_patterns.data.room.model.WeatherEntity
import ru.skillbox.presentation_patterns.databinding.FragmentDetailBinding
import ru.skillbox.presentation_patterns.ui.fragment.detail.adapter.itemDetailWeather
import ru.skillbox.presentation_patterns.utils.extension.setData
import ru.skillbox.presentation_patterns.utils.platform.ViewBindingFragment

@AndroidEntryPoint
class DetailCityFragment : ViewBindingFragment<FragmentDetailBinding>(
        FragmentDetailBinding::inflate,
        R.layout.fragment_detail
) {

    override val statusBarColor: Int = android.R.color.transparent
    override val screenViewModel by viewModels<DetailCityViewModel>()

    private val title: String by lazy {
        requireArguments().get(TITLE) as String
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
        initList()
        subscribe()
    }

    private val detailCityListAdapter by lazy {
        ListDelegationAdapter(
                itemDetailWeather()
        )
    }

    private fun initList() {
        with(binding.detailRecycler) {
            adapter = detailCityListAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun setAdapter(listCity: List<WeatherEntity>) {
        detailCityListAdapter.setData(listCity)
    }

    private fun subscribe() {
        screenViewModel.weatherList.observe(viewLifecycleOwner) { listWeatherEntity ->
            listWeatherEntity?.let {
                setAdapter(it)
            }
        }
    }

    private fun bind() {
        binding.tvTitle.text = title
        binding.ivBack.setOnClickListener {
            screenViewModel.navigateBack()
        }
        binding.ivRemove.setOnClickListener {
            screenViewModel.removeCity(title)
        }
        screenViewModel.getListWeather(title)
    }

    companion object {

        private const val TITLE = "title_detail"

        fun newInstance(title: String): DetailCityFragment =
                DetailCityFragment().apply {
                    arguments = bundleOf(TITLE to title)
                }

    }

}