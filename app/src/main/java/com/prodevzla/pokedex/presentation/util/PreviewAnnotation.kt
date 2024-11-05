package com.prodevzla.pokedex.presentation.util

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme", showBackground = true)
//@PreviewLightDark - Airbnb Showkase doesn't support this annotation as of  version 1.0.3
annotation class ThemePreviews
