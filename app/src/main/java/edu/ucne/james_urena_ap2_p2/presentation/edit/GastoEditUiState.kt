package edu.ucne.james_urena_ap2_p2.presentation.edit

data class GastoEditUiState(
    val gastoId: Int = 0,
    val fecha: String = "",
    val suplidor: String = "",
    val ncf: String = "",
    val itbis: String = "",
    val monto: String = "",

    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null,

    val suplidorError: String? = null,
    val montoError: String? = null
) {
    val isEditMode: Boolean
        get() = gastoId != 0
}