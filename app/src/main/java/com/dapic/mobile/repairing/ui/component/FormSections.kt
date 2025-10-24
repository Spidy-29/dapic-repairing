package com.dapic.mobile.repairing.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dapic.mobile.repairing.FormState
import com.dapic.mobile.repairing.FormViewModel
import com.dapic.mobile.repairing.ui.DatePickerField

/**
 * Date Selection Section Component
 */
@Composable
fun DateSelectionSection(
    formState: FormState,
    viewModel: FormViewModel,
    modifier: Modifier = Modifier
) {
    var isDateRange by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    FormSectionCard(
        title = "Date Selection",
        modifier = modifier
    ) {
        // ✨ FIX: Added verticalAlignment to the Row
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = !isDateRange,
                onClick = {
                    isDateRange = false
                    // When switching back to single date, you might want to clear the range
                    // or use the last selected single date.
                    viewModel.setSingleDate(formState.date)
                }
            )
            Text("Single Date", modifier = Modifier.padding(end = 16.dp))

            RadioButton(
                selected = isDateRange,
                onClick = {
                    isDateRange = true
                    viewModel.setDateRange(startDate, endDate)
                }
            )
            Text("Date Range")
        }

        if (isDateRange) {
            DatePickerField(
                label = "Start Date",
                selectedDate = startDate,
                onDateSelected = {
                    startDate = it
                    viewModel.setDateRange(startDate, endDate)
                }
            )
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
                onDateSelected = { viewModel.setSingleDate(it) },
                label = "Select Date"
            )
        }
    }
}

/**
 * Work Allocation Section Component
 */
@Composable
fun WorkAllocationSection(
    formState: FormState,
    viewModel: FormViewModel,
    titles: List<String>,
    workAllocationCategory: List<String>,
    values: List<String>,
    modifier: Modifier = Modifier
) {
    FormSectionCard(
        title = "${titles.getOrNull(0) ?: "Work Allocation"} Type",
        modifier = modifier
    ) {
        FormDropDownField(
            itemList = workAllocationCategory,
            fieldLabel = "${titles.getOrNull(0) ?: "Work Allocation"} Type",
            selectedValue = formState.workAllocationType,
            label = "category"
        ) { selectedType ->
            viewModel.updateWorkAllocationType(selectedType)
        }

        if (formState.workAllocationType in listOf("Office", "Site Visit", "WFH")) {
            FormDropDownField(
                itemList = values,
                fieldLabel = "",
                selectedValue = formState.workAllocation,
                label = ""
            ) { selectedAllocation ->
                viewModel.updateWorkAllocation(selectedAllocation)
            }
        }
    }
}

/**
 * Call Type Section Component
 */
@Composable
fun CallTypeSection(
    formState: FormState,
    viewModel: FormViewModel,
    titles: List<String>,
    modifier: Modifier = Modifier
) {
    // List of options for the radio buttons
    val callTypeOptions = listOf("FOC (free of charge)", "Chargeable")

    FormSectionCard(
        title = titles.getOrNull(1) ?: "Call Type",
        modifier = modifier
    ) {
        // ✨ FIX 1: Use Row with selectableGroup for accessibility
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ✨ FIX 2: Loop through the options to create each radio button
            callTypeOptions.forEach { optionText ->
//                Row(
//                    // This modifier makes the whole row (button + text) clickable
//                    Modifier
//                        .selectable(
//                            selected = (formState.callType == optionText),
//                            onClick = { viewModel.updateCallType(optionText) },
//                            role = Role.RadioButton
//                        )
//                        .padding(end = 16.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
                RadioButton(
                    selected = (formState.callType == optionText),
                    // onClick is null here because the parent Row handles it
                    onClick = { viewModel.updateCallType(optionText) }
                )
                Text(
                    text = optionText,
                    modifier = Modifier.padding(start = 4.dp)
                )
//                }
            }
        }
    }
}

/**
 * OEM Name Section Component
 */
@Composable
fun OemNameSection(
    formState: FormState,
    viewModel: FormViewModel,
    titles: List<String>,
    oemDetails: List<String>,
    modifier: Modifier = Modifier
) {
    var otherName by remember { mutableStateOf("") }

    FormSectionCard(
        title = titles.getOrNull(2) ?: "OEM Name",
        modifier = modifier
    ) {
        FormDropDownField(
            itemList = oemDetails,
            fieldLabel = titles.getOrNull(2) ?: "OEM Name",
            selectedValue = formState.oemName,
            canUserType = true
        ) { name ->
            viewModel.updateOemName(name)
        }

        if (formState.oemName == "Other") {
            OutlinedTextField(
                value = otherName,
                onValueChange = { otherName = it },
                label = { Text("Enter Other ${titles.getOrNull(2) ?: "OEM Name"}") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * End User Name Section Component
 */
@Composable
fun EndUserNameSection(
    formState: FormState,
    viewModel: FormViewModel,
    titles: List<String>,
    modifier: Modifier = Modifier
) {
    FormSectionCard(
        title = titles.getOrNull(3) ?: "End User",
        modifier = modifier
    ) {
        OutlinedTextField(
            value = formState.endUserName,
            onValueChange = viewModel::updateEndUserName,
            placeholder = { Text("End user") },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Panel Code Section Component
 */
@Composable
fun PanelCodeSection(
    formState: FormState,
    viewModel: FormViewModel,
    titles: List<String>,
    modifier: Modifier = Modifier
) {
    FormSectionCard(
        title = titles.getOrNull(4) ?: "Panel Code",
        modifier = modifier
    ) {
        OutlinedTextField(
            value = formState.panelCode,
            onValueChange = {
                val filteredValue = it.filter { char -> char.isDigit() }
                viewModel.updatePanelCode(filteredValue)
            },
            placeholder = { Text("Panel Code") },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = formState.soNo,
            onValueChange = {
                val filteredValue = it.filter { char -> char.isDigit() }
                viewModel.updateSoNo(filteredValue)
            },
            placeholder = { Text("So No") },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Service Request Number Section Component
 */
@Composable
fun ServiceRequestNumberSection(
    formState: FormState,
    viewModel: FormViewModel,
    titles: List<String>,
    modifier: Modifier = Modifier
) {
    FormSectionCard(
        title = titles.getOrNull(5) ?: "Service Req Number",
        modifier = modifier
    ) {
        OutlinedTextField(
            value = formState.serviceRequestNumber,
            onValueChange = viewModel::updateServiceRequestNumber,
            placeholder = { Text("Code") },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Current Status Section Component
 */
@Composable
fun CurrentStatusSection(
    formState: FormState,
    viewModel: FormViewModel,
    titles: List<String>,
    currentStatus: List<String>,
    modifier: Modifier = Modifier
) {
    FormSectionCard(
        title = titles.getOrNull(6) ?: "Current Status",
        modifier = modifier
    ) {
        FormDropDownField(
            itemList = currentStatus,
            fieldLabel = titles.getOrNull(6) ?: "Current Status",
            selectedValue = formState.currentStatus
        ) { name ->
            viewModel.updateCurrentStatus(name)
        }
    }
}

/**
 * Remark Section Component
 */
@Composable
fun RemarkSection(
    formState: FormState,
    viewModel: FormViewModel,
    titles: List<String>,
    modifier: Modifier = Modifier
) {
    FormSectionCard(
        title = titles.getOrNull(7) ?: "Remark",
        modifier = modifier
    ) {
        OutlinedTextField(
            value = formState.remark,
            onValueChange = { str -> viewModel.updateRemark(str) },
            placeholder = { Text("Remark") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                autoCorrectEnabled = true
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
    }
}


