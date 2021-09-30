package ru.skillbox.presentation_patterns.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ncapdevi.fragnav.*
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import dagger.hilt.android.AndroidEntryPoint
import ru.skillbox.presentation_patterns.R
import ru.skillbox.presentation_patterns.ui.fragment.city.CityFragment
import ru.skillbox.presentation_patterns.ui.fragment.search.SearchFragment
import ru.skillbox.presentation_patterns.utils.extension.FragmentNavigation
import ru.skillbox.presentation_patterns.utils.extension.*
import ru.skillbox.presentation_patterns.utils.platform.*

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_launcer), FragmentNavigation,
        FragNavController.RootFragmentListener {

    override val statusBarColor: Int = android.R.color.transparent
    override val screenViewModel by viewModels<MainViewModel>()

    lateinit var bottomNav: BottomNavigationView

    private val fragNavController by lazy {
        FragNavController(
                supportFragmentManager,
                R.id.host_launcher
        )
    }

    private val tabs = arrayListOf(
            R.id.search to SearchFragment(),
            R.id.city to CityFragment()
    )

    override fun initInterface(savedInstanceState: Bundle?) {
        bottomNav = findViewById(R.id.bottom_nav)
        setupBottomNavigation(savedInstanceState)
    }

    //region Navigation
    override val numberOfRootFragments: Int = tabs.size

    override fun getRootFragment(index: Int) = tabs[index].second

    override fun switchTab(index: Int) {
        fragNavController.switchTab(index,
                FragNavTransactionOptions
                        .newBuilder()
                        .build())

        bottomNav.selectedItemId = tabs[index].first
    }

    override fun pushFragment(fragment: BaseFragment) {
        fragNavController.pushFragment(fragment,
                FragNavTransactionOptions
                        .newBuilder()
                        .build())
    }

    override fun popFragment() {
        fragNavController.popFragment()
    }

    override fun popFragments(count: Int) {
        fragNavController.popFragments(count)
    }

    override fun clearStack() {
        fragNavController.clearStack()
    }

    override fun showDialogFragment(dialogFragment: DialogFragment) {
        fragNavController.showDialogFragment(dialogFragment)
    }

    override fun canGoBack(): Boolean {
        return fragNavController.currentStack?.size?.let { size ->
            size > 1
        } ?: false
    }

    override fun onBackPressed() {
        val topFragment = fragNavController.currentStack?.peek()
        when {
            topFragment is BaseFragment && topFragment.onBackPressed() -> Unit
            fragNavController.popFragment() -> Unit
            else -> super.onBackPressed()
        }
    }
    //endregion

    override fun showBottomNavigation() {
        if (bottomNav.visibility != View.VISIBLE) {
            bottomNav.show()
        }
    }

    override fun hideBottomNavigation() {
        if (bottomNav.visibility != View.GONE) {
            bottomNav.gone()
        }
    }

    private fun setupBottomNavigation(savedInstanceState: Bundle?) {

        bottomNav.setBackgroundColor(getColor(R.color.white))

        val navigationItemListener = BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.search -> {
                    fragNavController.switchTab(FragNavController.TAB1)
                }
                R.id.city -> {
                    fragNavController.switchTab(FragNavController.TAB2)
                }
            }
            true
        }

        fragNavController.apply {
            rootFragmentListener = this@MainActivity
            fragmentHideStrategy = FragNavController.DETACH
            navigationStrategy = UniqueTabHistoryStrategy(object : FragNavSwitchController {
                override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
                    bottomNav.selectedItemId = tabs[index].first
                }
            })
        }

        bottomNav.setOnNavigationItemSelectedListener(navigationItemListener)
        bottomNav.setOnNavigationItemReselectedListener { fragNavController.clearStack() }

        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)
    }
}