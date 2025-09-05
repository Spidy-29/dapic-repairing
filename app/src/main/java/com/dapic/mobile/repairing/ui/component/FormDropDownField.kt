package com.dapic.mobile.repairing.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dapic.mobile.repairing.ui.DropdownTextField

@Composable
fun FormDropDownField(
    modifier: Modifier = Modifier,
    fieldLabel: String = "",
    itemList: List<String>,
    selectedValue: String = "",
    label: String = "",
    canUserType:Boolean = false,
    onSelect: (String) -> Unit
) {
    Text(text = fieldLabel, style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(16.dp))

    DropdownTextField(
        itemList = itemList,
        selectedValue = selectedValue,
        onItemSelected = { onSelect(it) },
        label = label,
        canUserType =canUserType,
    )
}