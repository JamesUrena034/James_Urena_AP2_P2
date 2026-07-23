package edu.ucne.james_urena_ap2_p2.domain.repository

import edu.ucne.james_urena_ap2_p2.data.remote.Resource
import edu.ucne.james_urena_ap2_p2.domain.model.Gasto
import kotlinx.coroutines.flow.Flow

interface GastoRepository {
    fun getGastos(): Flow<Resource<List<Gasto>>>
    fun getGastoDetail(id: Int): Flow<Resource<Gasto>>
    fun createGasto(gasto: Gasto): Flow<Resource<Gasto>>
    fun updateGasto(id: Int, gasto: Gasto): Flow<Resource<Unit>>
}