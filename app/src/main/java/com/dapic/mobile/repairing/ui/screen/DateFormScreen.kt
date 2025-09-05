package com.dapic.mobile.repairing.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dapic.mobile.repairing.FormViewModel
import com.dapic.mobile.repairing.ui.DatePickerField
import com.dapic.mobile.repairing.ui.component.CompanyLogo
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun DateFormScreen(
    viewModel: FormViewModel = hiltViewModel(),
    onContinue: () -> Unit
) {
    val formState by viewModel.formState.collectAsState()
//    val selectedDate by viewModel.selectedDate.collectAsState()
    var isDateRange by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    // Date validation logic
    val isValidRange = remember(startDate, endDate) {
        if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
            try {
                val sdf = SimpleDateFormat("M-dd-yyyy", Locale.getDefault())
                val start = sdf.parse(startDate)
                val end = sdf.parse(endDate)
                !start?.equals(end)!! || start.before(end)
            } catch (e: Exception) {
                false
            }
        } else false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CompanyLogo()
        // Toggle between Single Date and Date Range
        Row {
            RadioButton(
                selected = !isDateRange,
                onClick = {
                    isDateRange = false
                    viewModel.setSingleDate(formState.date) // Ensure reset
                }
            )
            Text("Single Date", modifier = Modifier.padding(end = 16.dp))

            RadioButton(
                selected = isDateRange,
                onClick = {
                    isDateRange = true
                    viewModel.setDateRange(startDate, endDate) // Placeholder update
                }
            )
            Text("Date Range")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isDateRange) {
            DatePickerField(
                label = "Start Date",
                selectedDate = startDate,
                onDateSelected = {
                    startDate = it
                    viewModel.setDateRange(startDate, endDate)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            DatePickerField(
                label = "End Date",
                selectedDate = endDate,
                onDateSelected = {
                    endDate = it
                    viewModel.setDateRange(startDate, endDate)
                }
            )
        } else {
            DatePickerField(
                selectedDate = formState.date,
                onDateSelected = {
                    viewModel.setSingleDate(it)
                },
                label = "Select Date"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onContinue() },
            enabled = if (isDateRange) isValidRange && endDate.isNotEmpty()
            else formState.date.isNotEmpty()
        ) {
            Text("Continue")
        }
    }
}
