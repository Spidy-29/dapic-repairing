package com.dapic.mobile.repairing.ui.component

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun FormSubmitButton(
    modifier: Modifier = Modifier,
    isClickable: Boolean = false,
    apiCall: suspend () -> Boolean,
    onComplete: () -> Unit,
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.White,
            disabledContentColor = Color.DarkGray,
            disabledContainerColor = Color.LightGray
        ),
        onClick = {
            isLoading = true
            // Launch coroutine to handle the submission process
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiCall()
                isLoading = false
                if (response)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Data Submitted... Thank You.",
                            Toast.LENGTH_LONG
                        ).show()
                        onComplete()
                    }
                else
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Something Went Wrong. Please Try again.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        },
        enabled = isClickable && !isLoading,
    ) {
        if (isLoading)
            CircularProgressIndicator(color = Color.White)
        else
            Text("Submit")
    }
}