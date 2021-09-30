package ru.skillbox.presentation_patterns.utils.platform

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import ru.skillbox.presentation_patterns.utils.extension.getCompatColor
import ru.skillbox.presentation_patterns.utils.extension.observeEvent
import ru.skillbox.presentation_patterns.utils.extension.setStatusBarColor
import ru.skillbox.presentation_patterns.utils.extension.setStatusBarLightMode

abstract class BaseActivity(@LayoutRes val layoutResId: Int) : AppCompatActivity(layoutResId)  {

    open val screenViewModel: BaseViewModel?
        get() = null

    open val statusBarColor = android.R.color.transparent
    open val statusBarLightMode = true

    abstract fun initInterface(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setStatusBarColor(this.getCompatColor(statusBarColor))
        this.setStatusBarLightMode(statusBarLightMode)

        initInterface(savedInstanceState)
        observeBaseLiveData()
    }

    open fun observeBaseLiveData() {
        screenViewModel?.let { vm ->
            observeEvent(vm.mainState, ::handleState)
        }
    }

    open fun handleState(state: State) {
        when (state) {
//            is State.Loading -> shimmerProgress.show()
//            is State.Loaded -> shimmerProgress.gone()
//            is State.Error -> {
//                shimmerProgress.gone()
//            }
        }
    }
}