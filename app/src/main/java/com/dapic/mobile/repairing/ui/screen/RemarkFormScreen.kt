package com.dapic.mobile.repairing.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dapic.mobile.repairing.FormViewModel
import com.dapic.mobile.repairing.ui.component.CompanyLogo
import com.dapic.mobile.repairing.ui.component.FormSubmitButton

@Composable
fun RemarkFormScreen(
    viewModel: FormViewModel = hiltViewModel(), // Hilt-provided ViewModel,
    onSubmit: () -> Unit
) {
    var title by remember { mutableStateOf("Remark") }
    LaunchedEffect(Unit) {
        val headersList = viewModel.loadHeadersList()
        title = headersList.takeIf { it.size > 8 }?.get(8) ?: "Remark"
    }

    val formState by viewModel.formState.collectAsState()

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
            value = formState.remark,
            onValueChange = { str ->
                viewModel.updateRemark(str)
            },
            placeholder = { Text(text = "Remark") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                autoCorrectEnabled = true // Allow spaces and other text-entry behaviors
            ),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 50.dp, max = 200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))


        FormSubmitButton(isClickable = true, apiCall = {
            formState.remark.trim()
            viewModel.submitForm()
        }) {
            onSubmit()
        }

    }
}
