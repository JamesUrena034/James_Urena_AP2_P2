package edu.ucne.james_urena_ap2_p2.domain.usecase

import edu.ucne.james_urena_ap2_p2.domain.model.Gasto
import edu.ucne.james_urena_ap2_p2.domain.repository.GastoRepository
import javax.inject.Inject

class CreateGastoUseCase @Inject constructor(
    private val repository: GastoRepository
) {
    operator fun invoke(gasto: Gasto) = repository.createGasto(gasto)
}