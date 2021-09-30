package com.simplemobiletools.clock.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import com.simplemobiletools.clock.BuildConfig
import com.simplemobiletools.clock.R
import com.simplemobiletools.clock.adapters.ViewPagerAdapter
import com.simplemobiletools.clock.extensions.*
import com.simplemobiletools.clock.helpers.*
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.ensureBackgroundThread
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : SimpleActivity() {
    private var storedTextColor = 0
    private var storedBackgroundColor = 0
    private var storedPrimaryColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appLaunched(BuildConfig.APPLICATION_ID)
        storeStateVariables()
        initFragments()

        if (getNextAlarm().isEmpty()) {
            ensureBackgroundThread {
                rescheduleEnabledAlarms()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val configTextColor = config.textColor
        if (storedTextColor != configTextColor) {
            getInactiveTabIndexes(view_pager.currentItem).forEach {
                main_tabs_holder.getTabAt(it)?.icon?.applyColorFilter(configTextColor)
            }
        }

        val configBackgroundColor = config.backgroundColor
        if (storedBackgroundColor != configBackgroundColor) {
            main_tabs_holder.background = ColorDrawable(configBackgroundColor)
        }

        val configPrimaryColor = config.primaryColor
        if (storedPrimaryColor != configPrimaryColor) {
            main_tabs_holder.setSelectedTabIndicatorColor(getAdjustedPrimaryColor())
            main_tabs_holder.getTabAt(view_pager.currentItem)?.icon?.applyColorFilter(getAdjustedPrimaryColor())
        }

        if (config.preventPhoneFromSleeping) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    override fun onPause() {
        super.onPause()
        storeStateVariables()
        if (config.preventPhoneFromSleeping) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    override fun onStop() {
        super.onStop()
        config.lastUsedViewPagerPage = view_pager.currentItem
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu.apply {
            updateMenuItemColors(this)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> launchSettings()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onNewIntent(intent: Intent) {
        if (intent.extras?.containsKey(OPEN_TAB) == true) {
            val tabToOpen = intent.getIntExtra(OPEN_TAB, TAB_CLOCK)
            view_pager.setCurrentItem(tabToOpen, false)
        }
        super.onNewIntent(intent)
    }

    private fun storeStateVariables() {
        config.apply {
            storedTextColor = textColor
            storedBackgroundColor = backgroundColor
            storedPrimaryColor = primaryColor
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == PICK_AUDIO_FILE_INTENT_ID && resultCode == RESULT_OK && resultData != null) {
            storeNewAlarmSound(resultData)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_LOCATION) {
            StatePermissionLocation.changeStateAppInstall(
                isSuccess = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            )
        }
    }

    private fun storeNewAlarmSound(resultData: Intent) {
        val newAlarmSound = storeNewYourAlarmSound(resultData)

        when (view_pager.currentItem) {
            TAB_ALARM -> getViewPagerAdapter()?.updateAlarmTabAlarmSound(newAlarmSound)
        }
    }

    fun updateClockTabAlarm() {
        getViewPagerAdapter()?.updateClockTabAlarm()
    }

    private fun getViewPagerAdapter() = view_pager.adapter as? ViewPagerAdapter

    private fun initFragments() {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        view_pager.adapter = viewPagerAdapter
        view_pager.onPageChangeListener {
            main_tabs_holder.getTabAt(it)?.select()
            invalidateOptionsMenu()
        }

        val tabToOpen = intent.getIntExtra(OPEN_TAB, config.lastUsedViewPagerPage)
        intent.removeExtra(OPEN_TAB)
        view_pager.currentItem = tabToOpen
        view_pager.offscreenPageLimit = TABS_COUNT - 1
        main_tabs_holder.onTabSelectionChanged(
            tabUnselectedAction = {
                it.icon?.applyColorFilter(config.textColor)
            },
            tabSelectedAction = {
                view_pager.currentItem = it.position
                it.icon?.applyColorFilter(getAdjustedPrimaryColor())
            }
        )

        setupTabColors(tabToOpen)
    }

    private fun setupTabColors(lastUsedTab: Int) {
        main_tabs_holder.apply {
            background = ColorDrawable(config.backgroundColor)
            setSelectedTabIndicatorColor(getAdjustedPrimaryColor())
            getTabAt(lastUsedTab)?.apply {
                select()
                icon?.applyColorFilter(getAdjustedPrimaryColor())
            }

            getInactiveTabIndexes(lastUsedTab).forEach {
                getTabAt(it)?.icon?.applyColorFilter(config.textColor)
            }
        }
    }

    private fun getInactiveTabIndexes(activeIndex: Int) = arrayListOf(0, 1, 2, 3).filter { it != activeIndex }

    private fun launchSettings() {
        startActivity(Intent(applicationContext, SettingsActivity::class.java))
    }
}
