package edu.ucne.james_urena_ap2_p2.data.remote.remotedatasource

import edu.ucne.james_urena_ap2_p2.data.remote.GastoApi
import edu.ucne.james_urena_ap2_p2.data.remote.dto.GastoRequest
import edu.ucne.james_urena_ap2_p2.data.remote.dto.GastoResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GastoRemoteDataSource @Inject constructor(
    private val api: GastoApi
) {

    suspend fun getGastos(): Result<List<GastoResponse>> {
        return try {
            val response = api.getGastos()
            if (!response.isSuccessful) {
                return Result.failure(Exception("Error HTTP: ${response.code()}"))
            }
            Result.success(response.body() ?: emptyList())
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor", e))
        } catch (e: IOException) {
            Result.failure(Exception("Error de red: Verifica tu conexión a internet", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error desconocido: ${e.message}", e))
        }
    }

    suspend fun getGastoDetail(id: Int): Result<GastoResponse> {
        return try {
            val response = api.getGastoDetail(id)
            if (!response.isSuccessful) {
                return Result.failure(Exception("Error HTTP: ${response.code()}"))
            }
            val body = response.body() ?: throw Exception("La respuesta del servidor está vacía")
            Result.success(body)
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor", e))
        } catch (e: IOException) {
            Result.failure(Exception("Error de red: Verifica tu conexión a internet", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error desconocido: ${e.message}", e))
        }
    }

    suspend fun createGasto(gasto: GastoRequest): Result<GastoResponse> {
        return try {
            val response = api.createGasto(gasto)
            if (!response.isSuccessful) {
                return Result.failure(Exception("Error HTTP: ${response.code()}"))
            }
            val body = response.body() ?: throw Exception("La respuesta del servidor está vacía")
            Result.success(body)
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor", e))
        } catch (e: IOException) {
            Result.failure(Exception("Error de red: Verifica tu conexión a internet", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error desconocido: ${e.message}", e))
        }
    }

    suspend fun updateGasto(id: Int, request: GastoRequest): Result<Unit> {
        return try {
            val response = api.updateGasto(id, request)
            if (!response.isSuccessful)
                return Result.failure(Exception("Error de red ${response.code()}"))
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error desconocido", e))
        }
    }
}