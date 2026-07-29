package edu.ucne.james_urena_ap2_p2.presentation.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GastoEditScreen(
    gastoId: Int,
    viewModel: GastoEditViewModel = hiltViewModel(),
    onSaved: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(gastoId) {
        viewModel.init(gastoId)
    }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onSaved()
        }
    }

    GastoEditBodyScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastoEditBodyScreen(
    state: GastoEditUiState,
    onEvent: (GastoEditUiEvent) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isEditMode) "Editar Gasto" else "Nuevo Gasto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = state.fecha,
                onValueChange = { onEvent(GastoEditUiEvent.FechaChanged(it)) },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.suplidor,
                onValueChange = { onEvent(GastoEditUiEvent.SuplidorChanged(it)) },
                label = { Text("Suplidor") },
                isError = state.suplidorError != null,
                supportingText = {
                    state.suplidorError?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.ncf,
                onValueChange = { onEvent(GastoEditUiEvent.NcfChanged(it)) },
                label = { Text("NCF") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.itbis,
                onValueChange = { onEvent(GastoEditUiEvent.ItbisChanged(it)) },
                label = { Text("ITBIS") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.monto,
                onValueChange = { onEvent(GastoEditUiEvent.MontoChanged(it)) },
                label = { Text("Monto") },
                isError = state.montoError != null,
                supportingText = {
                    state.montoError?.let { Text(it) }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            if (state.error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onEvent(GastoEditUiEvent.Save) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                } else {
                    Text("Guardar")
                }
            }
        }
    }
}