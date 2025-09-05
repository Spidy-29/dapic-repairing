package com.dapic.mobile.repairing.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dapic.mobile.repairing.FormViewModel
import com.dapic.mobile.repairing.ui.component.CompanyLogo

@Composable
fun EndUserNameFormScreen(
    viewModel: FormViewModel = hiltViewModel(), // Hilt-provided ViewModel,
    onContinue: () -> Unit
) {
    var title by remember { mutableStateOf("End User") }
    LaunchedEffect(Unit) {
        val headersList = viewModel.loadHeadersList()
        title = headersList.takeIf { it.size > 3 }?.get(3) ?: "End User"
    }

    val formState by viewModel.formState.collectAsState()
//    val endUserName by viewModel.selectedEndUserName.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CompanyLogo()
        Text(text = "$title", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = formState.endUserName,
            onValueChange = viewModel::updateEndUserName,
            placeholder = { Text(text = "End user") },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White,
                disabledContentColor = Color.DarkGray,
                disabledContainerColor = Color.LightGray
            ),
            onClick = { onContinue() },
        ) {
            Text("Continue")
        }
    }
}
