package edu.ucne.james_urena_ap2_p2.presentation.form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.james_urena_ap2_p2.data.remote.Resource
import edu.ucne.james_urena_ap2_p2.domain.model.Gasto
import edu.ucne.james_urena_ap2_p2.domain.usecase.CreateGastoUseCase
import edu.ucne.james_urena_ap2_p2.domain.usecase.GetGastoDetailUseCase
import edu.ucne.james_urena_ap2_p2.domain.usecase.UpdateGastoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class GastoFormViewModel @Inject constructor(
    private val getGastoDetailUseCase: GetGastoDetailUseCase,
    private val createGastoUseCase: CreateGastoUseCase,
    private val updateGastoUseCase: UpdateGastoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(GastoFormUiState())
    val state = _state.asStateFlow()

    init {
        val id = savedStateHandle.get<Int>("id") ?: 0
        if (id != 0) {
            loadGasto(id)
        } else {
            _state.update { it.copy(fecha = LocalDateTime.now().toString()) }
        }
    }

    fun onEvent(event: GastoFormUiEvent) {
        when (event) {
            is GastoFormUiEvent.FechaChanged ->
                _state.update { it.copy(fecha = event.value) }

            is GastoFormUiEvent.SuplidorChanged ->
                _state.update { it.copy(suplidor = event.value, suplidorError = null) }

            is GastoFormUiEvent.NcfChanged ->
                _state.update { it.copy(ncf = event.value) }

            is GastoFormUiEvent.ItbisChanged ->
                _state.update { it.copy(itbis = event.value) }

            is GastoFormUiEvent.MontoChanged ->
                _state.update { it.copy(monto = event.value, montoError = null) }

            GastoFormUiEvent.Save -> save()
        }
    }

    private fun loadGasto(id: Int) {
        viewModelScope.launch {
            getGastoDetailUseCase(id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { gasto ->
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    gastoId = gasto.gastoId,
                                    fecha = gasto.fecha,
                                    suplidor = gasto.suplidor,
                                    ncf = gasto.ncf,
                                    itbis = gasto.itbis.toString(),
                                    monto = gasto.monto.toString()
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(isLoading = false, error = result.message) }
                    }
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun validate(): Boolean {
        val current = _state.value
        var isValid = true

        if (current.suplidor.isBlank()) {
            _state.update { it.copy(suplidorError = "El suplidor no puede estar vacío") }
            isValid = false
        }

        val montoValue = current.monto.toDoubleOrNull()
        if (montoValue == null || montoValue <= 0) {
            _state.update { it.copy(montoError = "El monto es obligatorio y debe ser mayor a 0") }
            isValid = false
        }

        return isValid
    }

    private fun save() {
        if (!validate()) return

        val current = _state.value
        val gasto = Gasto(
            gastoId = current.gastoId,
            fecha = current.fecha,
            suplidor = current.suplidor,
            ncf = current.ncf,
            itbis = current.itbis.toDoubleOrNull() ?: 0.0,
            monto = current.monto.toDoubleOrNull() ?: 0.0
        )

        viewModelScope.launch {
            val flow = if (current.isEditMode) {
                updateGastoUseCase(current.gastoId, gasto)
            } else {
                createGastoUseCase(gasto)
            }

            flow.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update { it.copy(isLoading = false, isSaved = true) }
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(isLoading = false, error = result.message) }
                    }
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }
}