package edu.ucne.james_urena_ap2_p2.presentation.list

import edu.ucne.james_urena_ap2_p2.domain.model.Gasto

data class GastoListUiState(
    val isLoading: Boolean = false,
    val gastos: List<Gasto> = emptyList(),
    val error: String? = null
) {
    val conteo: Int
        get() = gastos.size

    val total: Double
        get() = gastos.sumOf { it.monto }
}