package edu.ucne.james_urena_ap2_p2.domain.usecase

import edu.ucne.james_urena_ap2_p2.domain.repository.GastoRepository
import javax.inject.Inject

class GetGastoDetailUseCase @Inject constructor(
    private val repository: GastoRepository
) {
    operator fun invoke(id: Int) = repository.getGastoDetail(id)
}