package edu.ucne.james_urena_ap2_p2.data.repository

import edu.ucne.james_urena_ap2_p2.data.remote.Resource
import edu.ucne.james_urena_ap2_p2.data.remote.dto.toRequest
import edu.ucne.james_urena_ap2_p2.data.remote.remotedatasource.GastoRemoteDataSource
import edu.ucne.james_urena_ap2_p2.domain.model.Gasto
import edu.ucne.james_urena_ap2_p2.domain.repository.GastoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GastoRepositoryImpl @Inject constructor(
    private val remoteDataSource: GastoRemoteDataSource
) : GastoRepository {

    override fun getGastos(): Flow<Resource<List<Gasto>>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getGastos()
        response.onSuccess { gastos ->
            emit(Resource.Success(gastos.map { it.toDomain() }))
        }.onFailure {
            emit(Resource.Error(it.message ?: "Error desconocido"))
        }
    }

    override fun getGastoDetail(id: Int): Flow<Resource<Gasto>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getGastoDetail(id)
        response.onSuccess { gasto ->
            emit(Resource.Success(gasto.toDomain()))
        }.onFailure {
            emit(Resource.Error(it.message ?: "Error desconocido"))
        }
    }

    override fun createGasto(gasto: Gasto): Flow<Resource<Gasto>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.createGasto(gasto.toRequest())
        response.onSuccess {
            emit(Resource.Success(it.toDomain()))
        }.onFailure {
            emit(Resource.Error(it.message ?: "Error desconocido"))
        }
    }

    override fun updateGasto(id: Int, gasto: Gasto): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val request = gasto.toRequest()
        remoteDataSource.updateGasto(id, request)
            .onSuccess { emit(Resource.Success(Unit)) }
            .onFailure { emit(Resource.Error(it.message ?: "Error desconocido")) }
    }
}