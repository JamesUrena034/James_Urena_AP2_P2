package edu.ucne.james_urena_ap2_p2.presentation.form

sealed interface GastoFormUiEvent {
    data class FechaChanged(val value: String) : GastoFormUiEvent
    data class SuplidorChanged(val value: String) : GastoFormUiEvent
    data class NcfChanged(val value: String) : GastoFormUiEvent
    data class ItbisChanged(val value: String) : GastoFormUiEvent
    data class MontoChanged(val value: String) : GastoFormUiEvent
    data object Save : GastoFormUiEvent
}