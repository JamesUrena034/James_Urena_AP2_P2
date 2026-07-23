package edu.ucne.james_urena_ap2_p2.presentation.list

sealed interface GastoListUiEvent {
    data object Refresh : GastoListUiEvent
}