package ru.skillbox.presentation_patterns.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit


class Pref(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("PatternSkillbox", Context.MODE_PRIVATE)

    private var cityArr: Set<String>
        get() = sharedPreferences.getStringSet(cityKey, emptySet()) ?: emptySet()
        set(value) {
            sharedPreferences.edit {
                putStringSet(cityKey, value)
            }
        }

    fun setApp(name: String, isAdd: Boolean) {
        val mSet: HashSet<String> = HashSet()
        val isAppExist = cityArr.contains(name)
        var oldList = cityArr
        mSet.addAll(oldList)
        if (isAdd)
            mSet.add(name)
        else if (isAppExist)
            mSet.remove(name)
        cityArr = mSet
    }

    fun getListCity() : List<String> = cityArr.toList()

    companion object {
        const val cityKey = "arr_city"
    }
}