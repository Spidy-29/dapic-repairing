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
import com.dapic.mobile.repairing.Utils
import com.dapic.mobile.repairing.ui.component.CompanyLogo
import com.dapic.mobile.repairing.ui.component.FormDropDownField

@Composable
fun WorkAllocationFormScreen(
    viewModel: FormViewModel = hiltViewModel(), // Hilt-provided ViewModel,
    onContinue: () -> Unit
) {
    val workAllocationCategory: List<String> = listOf(
        "Office",
        "Site Visit",
        "WFH",
        "AB",
        "PH",
        "Travelling",
        "Exhibition",
    )

    val formState by viewModel.formState.collectAsState()

    // Dynamically update dropdown values based on selectedType
    val values = when (formState.workAllocationType) {
        "Office" -> Utils.OFFICE_TYPE
        "Site Visit" -> Utils.SITE_VISIT_TYPE
        "WFH" -> Utils.WFH_TYPE
        else -> listOf()
    }

    LaunchedEffect(values) {
        if (values.isNotEmpty())
            viewModel.updateWorkAllocation(values[0])
    }

    var title by remember { mutableStateOf("Work Allocation") }

    LaunchedEffect(Unit) {
        val headersList = viewModel.loadHeadersList()
        title = headersList.firstOrNull() ?: "Work Allocation"
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CompanyLogo()

        FormDropDownField(
            itemList = workAllocationCategory,
            fieldLabel = "$title Type",
            selectedValue = formState.workAllocationType,
            label = "category"
        ) { selectedType ->
            viewModel.updateWorkAllocationType(selectedType)
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (formState.workAllocationType in listOf("AB", "PH", "Travelling", "Exhibition")) {
            Button(
                onClick = { onContinue() },
            ) {
                Text("Continue")
            }
        } else if (formState.workAllocationType.isNotEmpty()) {
            FormDropDownField(
                itemList = values,
                fieldLabel = "",
                selectedValue = formState.workAllocation,
                label = ""
            ) { selectedAllocation ->
                viewModel.updateWorkAllocation(selectedAllocation)
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
                enabled = formState.workAllocation.isNotEmpty()
            ) {
                Text("Continue")
            }
        }
    }
}
