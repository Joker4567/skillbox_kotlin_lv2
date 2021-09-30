package ru.skillbox.presentation_patterns.utils.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragment<T : ViewBinding>(
        private val inflateBinding: (
                inflater: LayoutInflater,
                root: ViewGroup?,
                attachToRoot: Boolean
        ) -> T,
        @LayoutRes layoutRes: Int
) : BaseFragment() {

    private var _binding: T? = null

    protected val binding: T
        get() = _binding!!

    final override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}