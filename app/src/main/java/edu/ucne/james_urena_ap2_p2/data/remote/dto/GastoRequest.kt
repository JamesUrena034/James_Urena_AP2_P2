package edu.ucne.james_urena_ap2_p2.data.remote.dto

import edu.ucne.james_urena_ap2_p2.domain.model.Gasto

data class GastoRequest(
    val fecha: String,
    val suplidor: String,
    val ncf: String,
    val itbis: Double,
    val monto: Double
)

fun Gasto.toRequest() = GastoRequest(
    fecha = fecha,
    suplidor = suplidor,
    ncf = ncf,
    itbis = itbis,
    monto = monto
)