package ru.skillbox.presentation_patterns.utils.platform

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.skillbox.presentation_patterns.R
import ru.skillbox.presentation_patterns.utils.extension.*

abstract class BaseFragment() : Fragment() {

    open val supportFragmentManager
        get() = requireActivity().supportFragmentManager

    open val screenViewModel: BaseViewModel? = null

    open val showBottomNavWhenFragmentLaunch = true
    open val statusBarColor = R.color.colorTransparent
    open val statusBarLightMode = false

    private var fragmentNavigation: FragmentNavigation? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentNavigation = context as? FragmentNavigation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupStatusBar(statusBarColor, statusBarLightMode)

        if (showBottomNavWhenFragmentLaunch) {
            fragmentNavigation?.showBottomNavigation()
        } else {
            fragmentNavigation?.hideBottomNavigation()
        }

        screenViewModel?.let { vm ->
            observeEvent(vm.mainState, ::handleState)
            observeEvent(vm.fragmentNavigation, ::handleFragmentNavigation)
        }
    }

    open fun onBackPressed(): Boolean = false

    open fun handleState(state: State) {
        when (state) {
//            is State.Loading ->
//            is State.Loaded ->
//            is State.Error ->
        }
    }

    private fun setupStatusBar(statusBarColor: Int, statusBarLightMode: Boolean) {
        activity?.setStatusBarColor(requireActivity().getCompatColor(statusBarColor))
        activity?.setStatusBarLightMode(statusBarLightMode)
    }

    fun hideNavigationBar() {
        fragmentNavigation?.hideBottomNavigation()
    }

    private fun handleFragmentNavigation(event: NavigationEvent) {
        fragmentNavigation?.let { event.doNavigation(it) }
    }

    fun exit() = fragmentNavigation?.popFragment()

    private fun dialogNotAlreadyShown(tag: String) =
            supportFragmentManager.findFragmentByTag(tag) == null
}