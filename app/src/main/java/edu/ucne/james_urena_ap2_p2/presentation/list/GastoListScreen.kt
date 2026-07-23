package edu.ucne.james_urena_ap2_p2.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.james_urena_ap2_p2.domain.model.Gasto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastoListScreen(
    viewModel: GastoListViewModel = hiltViewModel(),
    onGastoClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    GastoListBodyScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onGastoClick = onGastoClick,
        onAddClick = onAddClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastoListBodyScreen(
    state: GastoListUiState,
    onEvent: (GastoListUiEvent) -> Unit,
    onGastoClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Gastos") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Gasto")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            if (state.error != null) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.gastos) { gasto ->
                    GastoItem(
                        gasto = gasto,
                        onClick = { onGastoClick(gasto.gastoId) }
                    )
                }
            }

            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Conteo: ${state.conteo}",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Total: %.2f".format(state.total),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun GastoItem(
    gasto: Gasto,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "#${gasto.gastoId} - ${gasto.suplidor}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Fecha: ${gasto.fecha}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Monto: %.2f".format(gasto.monto),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}