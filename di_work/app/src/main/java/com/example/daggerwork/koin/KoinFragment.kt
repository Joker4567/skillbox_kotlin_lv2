package com.example.daggerwork.koin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.daggerwork.dagger.BicycleViewModel
import com.example.daggerwork.databinding.FragmentBicycleBinding
import org.koin.android.ext.android.get

class KoinFragment : Fragment() {
    private lateinit var binding: FragmentBicycleBinding

    private val viewModel: BicycleViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return BicycleViewModel(repository = get()) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBicycleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.profile.observe(viewLifecycleOwner) { state ->
            (state as? BicycleViewModel.State.Data)?.let { data ->
                val result = "Frame [serialNumber-RGB]: ${data.profile.frame.getColorIdFrame()}\n" +
                        "Logo id: ${data.profile.logo.id}\n" +
                        "Wheels serialNumber: ${data.profile.wheels.serialNumber}\n" +
                        "Wheels 2 serialNumber: ${data.profile.wheels.serialNumber}\n"
                Toast.makeText(requireContext(), result, Toast.LENGTH_LONG).show()
            }
        }
    }
}