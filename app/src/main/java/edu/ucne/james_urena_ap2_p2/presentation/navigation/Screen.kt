package edu.ucne.james_urena_ap2_p2.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Screen : NavKey {
    @Serializable
    data object GastoList : Screen()

    @Serializable
    data object GastoCreate : Screen()

    @Serializable
    data class GastoEdit(val id: Int) : Screen()
}