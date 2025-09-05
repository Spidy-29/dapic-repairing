package com.dapic.mobile.repairing.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.dapic.mobile.repairing.ui.component.FormDropDownField


@Composable
fun CurrentStatusFormScreen(
    viewModel: FormViewModel = hiltViewModel(), // Hilt-provided ViewModel,
    onContinue: () -> Unit
) {

    var title by remember { mutableStateOf("Current Status") }
    LaunchedEffect(Unit) {
        val headersList = viewModel.loadHeadersList()
        title = headersList.takeIf { it.size > 6 }?.get(6) ?: "Current Status"
    }

    val formState by viewModel.formState.collectAsState()
    val currentStatus = viewModel.loadCurrentStatus()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CompanyLogo()
        FormDropDownField(
            itemList = currentStatus,
            fieldLabel = title,
            selectedValue = formState.currentStatus
        ) { name ->
            viewModel.updateCurrentStatus(name)

        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White,
                disabledContentColor = Color.DarkGray,
                disabledContainerColor = Color.LightGray
            ),
            onClick = { onContinue() },
            enabled = formState.currentStatus.isNotEmpty()
        ) {
            Text("Continue")
        }
    }
}
