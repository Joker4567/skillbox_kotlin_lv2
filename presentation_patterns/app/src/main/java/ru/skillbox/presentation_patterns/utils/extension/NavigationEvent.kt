package ru.skillbox.presentation_patterns.utils.extension

import ru.skillbox.presentation_patterns.utils.platform.BaseFragment

sealed class NavigationEvent {

    //назад
    object Exit : NavigationEvent()

    //очистить стек текущей вкладки
    object ClearStack : NavigationEvent()

    //достать фрагменты
    data class PopFragments(val count: Int) : NavigationEvent()

    //переключить вкладку
    data class SwitchTab(val tabPosition: Int) : NavigationEvent()

    //открыть новый фрагмент
    data class PushFragment(
            val fragment: BaseFragment,
            val clearStack: Boolean = false
    ) : NavigationEvent()
}

fun NavigationEvent.doNavigation(fragmentNavigation: FragmentNavigation) {
    when (this) {
        NavigationEvent.ClearStack -> fragmentNavigation.clearStack()
        NavigationEvent.Exit -> fragmentNavigation.popFragment()
        is NavigationEvent.SwitchTab -> fragmentNavigation.switchTab(this.tabPosition)
        is NavigationEvent.PopFragments -> fragmentNavigation.popFragments(this.count)
        is NavigationEvent.PushFragment -> {
            if (this.clearStack) fragmentNavigation.clearStack()
            fragmentNavigation.pushFragment(this.fragment)
        }
    }
}