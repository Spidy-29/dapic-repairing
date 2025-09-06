package com.dapic.mobile.repairing.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@Composable
fun DatePickerField(
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    label: String
) {
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }

    // TextField to display the selected date
    OutlinedTextField(
        value = selectedDate,
        onValueChange = { /* No-op, since selection happens through the DatePicker */ },
        label = { Text(label) },
        readOnly = true, // Make it read-only
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select Date"
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    // Show DatePickerDialog when triggered
    if (showDatePicker) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        android.app.DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format the selected date and pass it to the callback
                val formattedDate = "${selectedMonth + 1}-$selectedDay-$selectedYear"
                onDateSelected(formattedDate)
                showDatePicker = false
            },
            year,
            month,
            day
        ).apply {
            setOnDismissListener { showDatePicker = false } // Dismiss dialog on cancel
        }.show()
    }
}