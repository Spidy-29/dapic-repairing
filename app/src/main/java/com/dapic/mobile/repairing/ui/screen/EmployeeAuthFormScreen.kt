package com.dapic.mobile.repairing.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dapic.mobile.repairing.FormViewModel
import com.dapic.mobile.repairing.ui.component.CompanyLogo
import com.dapic.mobile.repairing.ui.component.FormDropDownField

@Composable
fun EmployeeAuthFormScreen(
    viewModel: FormViewModel = hiltViewModel(),
    onContinue: () -> Unit
) {
    val formState by viewModel.formState.collectAsState()
    val context = LocalContext.current
    val employeeDetails = viewModel.loadEmployeeDetails()
    val employeeNames = employeeDetails.map { data -> data.name }

    var enteredPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CompanyLogo()
        FormDropDownField(
            itemList = employeeNames,
            selectedValue = formState.employeeName,
            onSelect = viewModel::updateEmployeeName,
            label = "Employee Name",
            fieldLabel = "Employee Login"
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = enteredPassword,
            onValueChange = { enteredPassword = it },
            maxLines = 1,
            label = { Text("Enter Password") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Default.Visibility
                else
                    Icons.Default.VisibilityOff

                IconButton (onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = if (passwordVisible) "Hide password" else "Show password")
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White,
                disabledContentColor = Color.DarkGray,
                disabledContainerColor = Color.LightGray
            ),
            onClick = {
                val findEmployeeDetails =
                    employeeDetails.filter { data -> data.name == formState.employeeName }

                if (findEmployeeDetails.isNotEmpty()
                        .and(findEmployeeDetails[0].password.equals(enteredPassword))
                ) {
                    onContinue()
                } else {
                    Toast.makeText(
                        context,
                        "Invalid code. Please reach out to your manager.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            enabled = enteredPassword.isNotEmpty()
        ) {
            Text("Continue")
        }
    }
}