package com.example.daggerwork.common

import com.example.daggerwork.dagger.BicycleViewModel

object BicycleViewUtils {
    @JvmStatic
    fun profileDescription(state: BicycleViewModel.State?): String? =
        (state as? BicycleViewModel.State.Data)?.let { "${it.profile.logo.id}" }

    @JvmStatic
    fun stateIsLoading(state: BicycleViewModel.State?) = state is BicycleViewModel.State.Loading

    @JvmStatic
    fun stateIsError(state: BicycleViewModel.State?) = state is BicycleViewModel.State.Error

    @JvmStatic
    fun stateIsData(state: BicycleViewModel.State?) = state is BicycleViewModel.State.Data
}