package edu.ucne.james_urena_ap2_p2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.james_urena_ap2_p2.presentation.navigation.AppNavigationDisplay
import edu.ucne.james_urena_ap2_p2.ui.theme.James_Urena_AP2_P2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            James_Urena_AP2_P2Theme()  {
                AppNavigationDisplay()
            }
        }
    }
}