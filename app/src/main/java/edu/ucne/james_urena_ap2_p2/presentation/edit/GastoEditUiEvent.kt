package edu.ucne.james_urena_ap2_p2.presentation.edit

sealed interface GastoEditUiEvent {
    data class FechaChanged(val value: String) : GastoEditUiEvent
    data class SuplidorChanged(val value: String) : GastoEditUiEvent
    data class NcfChanged(val value: String) : GastoEditUiEvent
    data class ItbisChanged(val value: String) : GastoEditUiEvent
    data class MontoChanged(val value: String) : GastoEditUiEvent
    data object Save : GastoEditUiEvent
}