package com.dapic.mobile.repairing.ui.screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dapic.mobile.repairing.DataRepository
import com.dapic.mobile.repairing.SplashViewModel
import com.dapic.mobile.repairing.ui.theme.DapicAttendanceTheme
@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onSplashComplete: () -> Unit
) {

    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var showErrorDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.prefetchData { success ->
            isLoading = false
            if (!success) {
                showErrorDialog = true
            }
        }
    }

    // Splash Screen UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Box(Modifier.weight(1f)) {
            // Error Dialog
            if (showErrorDialog) {
                AlertDialog(
                    onDismissRequest = {
                        /* Prevent dismissal */
                    },
                    title = {
                        Text(text = "Error", style = MaterialTheme.typography.titleLarge)
                    },
                    text = {
                        Text("Failed to fetch data. Please check your internet connection and try again.")
                    },
                    confirmButton = {
                        Button(onClick = {
                            isLoading = true
                            showErrorDialog = false
                            viewModel.prefetchData { success ->
                                isLoading = false
                                if (success) onSplashComplete()
                                else showErrorDialog = true
                            }
                        }) {
                            Text("Retry")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { (context as? Activity)?.finish() }) {
                            Text("Exit")
                        }
                    }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Dapic Attendance App",
                    fontSize = 34.sp,
                    color = Color.White,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Welcome",
                    color = Color.White,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White,
                disabledContentColor = Color.DarkGray,
                disabledContainerColor = Color.LightGray
            ),
                       onClick = { if (!isLoading && !showErrorDialog) onSplashComplete() },
            enabled = !isLoading,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            if (isLoading)
                CircularProgressIndicator(color = Color.White)
            else
                Text(text = "Start", color = Color.White, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, fontSize = 20.sp, modifier = Modifier.padding(vertical = 6.dp))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    val context = LocalContext.current
    val fakeViewModel = object : SplashViewModel(object : DataRepository(context) {
        override suspend fun fetchSheetData() {
            // no-op
        }
    }) {
        override fun prefetchData(onComplete: (Boolean) -> Unit) {
            onComplete(true)
        }
    }
    DapicAttendanceTheme(dynamicColor = false) {
        SplashScreen(viewModel = fakeViewModel as SplashViewModel, onSplashComplete = {})
    }
}