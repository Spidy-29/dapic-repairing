package com.dapic.mobile.repairing.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTextField(
    itemList: List<String>, // The list of items for the dropdown
    selectedValue: String, // The currently selected item
    label: String = "",
    onItemSelected: (String) -> Unit, // Callback for item selection
    canUserType: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf("") }
    var filteredList by remember { mutableStateOf(itemList) }


    // Sync `textFieldValue` with the `selectedValue` prop
    LaunchedEffect(selectedValue) {
        textFieldValue = selectedValue
    }

    // Update filtered list whenever textFieldValue changes
    LaunchedEffect(textFieldValue) {
        if (canUserType) {
            filteredList = if (textFieldValue.isEmpty()) {
                itemList
            } else {
                itemList.filter { it.contains(textFieldValue, ignoreCase = true) }
            }
        }
    }

    // Exposed Dropdown Menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // TextField which acts as a dropdown anchor
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                onItemSelected(it)
                expanded = true // Open the dropdown while typing
            },
            readOnly = !canUserType, // To make it readonly (so the user can only select from the dropdown)
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor() // This ensures the dropdown appears at the right position
        )

        // Dropdown Menu with fixed height
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // Using Column for a fixed-size dropdown menu
            Column(
                modifier = Modifier
                    .heightIn(max = 300.dp)
                    .verticalScroll(rememberScrollState()) // Set a maximum height (Fixed height)
            ) {
                val list = if(canUserType) filteredList else itemList
                list.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            onItemSelected(item) // Call the onItemSelected callback when item is clicked
                            textFieldValue =
                                item // Update the TextField with selected value
                            expanded = false // Close the dropdown
                        }
                    )
                }
            }
        }
    }
}

