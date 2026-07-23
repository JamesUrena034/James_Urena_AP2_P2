package edu.ucne.james_urena_ap2_p2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import edu.ucne.james_urena_ap2_p2.presentation.form.GastoFormScreen
import edu.ucne.james_urena_ap2_p2.presentation.list.GastoListScreen

@Composable
fun AppNavigationDisplay() {
    val backStack = rememberNavBackStack(Screen.GastoList)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Screen.GastoList> {
                GastoListScreen(
                    onGastoClick = { gastoId ->
                        backStack.add(Screen.GastoEdit(gastoId))
                    },
                    onAddClick = {
                        backStack.add(Screen.GastoCreate)
                    }
                )
            }
            entry<Screen.GastoCreate> {
                GastoFormScreen(
                    onSaved = { backStack.removeLastOrNull() },
                    onBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Screen.GastoEdit> { key ->
                GastoFormScreen(
                    onSaved = { backStack.removeLastOrNull() },
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}