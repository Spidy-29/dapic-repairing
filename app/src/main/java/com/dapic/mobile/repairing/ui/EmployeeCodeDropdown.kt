package com.dapic.mobile.repairing.ui
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeCodeDropdown(
    employeeCodes: List<String>,
    selectedEmployeeCode: String?,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) } // Tracks whether the menu is expanded
    var selectedText by remember { mutableStateOf(selectedEmployeeCode ?: "") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // TextField to display the selected value
        OutlinedTextField(
            value = selectedText,
            onValueChange = { /* No-op, since selection happens through dropdown */ },
            readOnly = true, // Make it read-only
            label = { Text("Select Employee Code") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor() // Align the dropdown with the text field
        )

        // Dropdown menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            employeeCodes.forEach { code ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = code
                        onItemSelected(code)
                        expanded = false // Collapse the menu after selection
                    },
                    text = { Text(code) }
                )
            }
        }
    }
}
