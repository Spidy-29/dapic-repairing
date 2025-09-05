package com.dapic.mobile.repairing.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dapic.mobile.repairing.R

/**
 * Created by Dhrumil Mevada on 14-01-2025.
 */

@Composable
fun CompanyLogo(
    modifier: Modifier = Modifier,
    @DrawableRes logoId: Int = R.drawable.dapic_light_logo_cliped,
) {
    Image(
        painter = painterResource(id = logoId),
        contentDescription = "company logo",
        alignment = Alignment.Center,
        contentScale = ContentScale.Fit,
        modifier = Modifier.scale(0.75f)
    )

    Spacer(modifier = Modifier.padding(16.dp))

}


//@Composable
//@Preview(showBackground = true, showSystemUi = true)
//fun CompanyLogoPreview() {
//    FormSyncTheme {
//        CompanyLogo()
//    }
//}