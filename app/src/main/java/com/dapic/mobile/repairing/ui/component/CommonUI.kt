package com.dapic.mobile.repairing.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Common UI components and styles used throughout the app
 */

/**
 * Standard continue button with consistent styling
 */
@Composable
fun ContinueButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String = "Continue",
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.White,
            disabledContentColor = Color.DarkGray,
            disabledContainerColor = Color.LightGray
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text)
    }
}

/**
 * Standard primary button with consistent styling
 */
@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            disabledContentColor = Color.DarkGray,
            disabledContainerColor = Color.LightGray
        ),
        modifier = modifier
    ) {
        Text(text)
    }
}

/**
 * Standard secondary button with consistent styling
 */
@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.White,
            disabledContentColor = Color.DarkGray,
            disabledContainerColor = Color.LightGray
        ),
        modifier = modifier
    ) {
        Text(text)
    }
}


