package cd.zgeniuscoders.genesis.common.ui.views.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import cd.zgeniuscoders.genesis.core.presentation.theme.backgroundLight
import cd.zgeniuscoders.genesis.core.presentation.theme.errorContainerDark
import cd.zgeniuscoders.genesis.core.presentation.theme.errorContainerLight
import cd.zgeniuscoders.genesis.core.presentation.theme.errorDark
import cd.zgeniuscoders.genesis.core.presentation.theme.errorLight
import cd.zgeniuscoders.genesis.core.presentation.theme.inverseOnSurfaceLight
import cd.zgeniuscoders.genesis.core.presentation.theme.inversePrimaryLight
import cd.zgeniuscoders.genesis.core.presentation.theme.inverseSurfaceLight
import cd.zgeniuscoders.genesis.core.presentation.theme.onBackgroundLight
import cd.zgeniuscoders.genesis.core.presentation.theme.onErrorContainerLight
import cd.zgeniuscoders.genesis.core.presentation.theme.onErrorDark
import cd.zgeniuscoders.genesis.core.presentation.theme.onErrorLight
import cd.zgeniuscoders.genesis.core.presentation.theme.onPrimaryContainerDark
import cd.zgeniuscoders.genesis.core.presentation.theme.onPrimaryContainerLight
import cd.zgeniuscoders.genesis.core.presentation.theme.onPrimaryDark
import cd.zgeniuscoders.genesis.core.presentation.theme.onPrimaryLight
import cd.zgeniuscoders.genesis.core.presentation.theme.onSecondaryContainerDark
import cd.zgeniuscoders.genesis.core.presentation.theme.onSecondaryContainerLight
import cd.zgeniuscoders.genesis.core.presentation.theme.onSecondaryDark
import cd.zgeniuscoders.genesis.core.presentation.theme.onSecondaryLight
import cd.zgeniuscoders.genesis.core.presentation.theme.onSurfaceLight
import cd.zgeniuscoders.genesis.core.presentation.theme.onSurfaceVariantLight
import cd.zgeniuscoders.genesis.core.presentation.theme.onTertiaryContainerDark
import cd.zgeniuscoders.genesis.core.presentation.theme.onTertiaryContainerLight
import cd.zgeniuscoders.genesis.core.presentation.theme.onTertiaryDark
import cd.zgeniuscoders.genesis.core.presentation.theme.onTertiaryLight
import cd.zgeniuscoders.genesis.core.presentation.theme.outlineLight
import cd.zgeniuscoders.genesis.core.presentation.theme.outlineVariantLight
import cd.zgeniuscoders.genesis.core.presentation.theme.primaryContainerDark
import cd.zgeniuscoders.genesis.core.presentation.theme.primaryContainerLight
import cd.zgeniuscoders.genesis.core.presentation.theme.primaryDark
import cd.zgeniuscoders.genesis.core.presentation.theme.primaryLight
import cd.zgeniuscoders.genesis.core.presentation.theme.scrimLight
import cd.zgeniuscoders.genesis.core.presentation.theme.secondaryContainerDark
import cd.zgeniuscoders.genesis.core.presentation.theme.secondaryContainerLight
import cd.zgeniuscoders.genesis.core.presentation.theme.secondaryDark
import cd.zgeniuscoders.genesis.core.presentation.theme.secondaryLight
import cd.zgeniuscoders.genesis.core.presentation.theme.surfaceBrightLight
import cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerHighLight
import cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerHighestLight
import cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerLight
import cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerLowLight
import cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerLowestLight
import cd.zgeniuscoders.genesis.core.presentation.theme.surfaceDimLight
import cd.zgeniuscoders.genesis.core.presentation.theme.surfaceLight
import cd.zgeniuscoders.genesis.core.presentation.theme.surfaceVariantLight
import cd.zgeniuscoders.genesis.core.presentation.theme.tertiaryContainerDark
import cd.zgeniuscoders.genesis.core.presentation.theme.tertiaryContainerLight
import cd.zgeniuscoders.genesis.core.presentation.theme.tertiaryDark
import cd.zgeniuscoders.genesis.core.presentation.theme.tertiaryLight

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onErrorContainerDark,
    background = cd.zgeniuscoders.genesis.core.presentation.theme.backgroundDark,
    onBackground = cd.zgeniuscoders.genesis.core.presentation.theme.onBackgroundDark,
    surface = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceDark,
    onSurface = cd.zgeniuscoders.genesis.core.presentation.theme.onSurfaceDark,
    surfaceVariant = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceVariantDark,
    onSurfaceVariant = cd.zgeniuscoders.genesis.core.presentation.theme.onSurfaceVariantDark,
    outline = cd.zgeniuscoders.genesis.core.presentation.theme.outlineDark,
    outlineVariant = cd.zgeniuscoders.genesis.core.presentation.theme.outlineVariantDark,
    scrim = cd.zgeniuscoders.genesis.core.presentation.theme.scrimDark,
    inverseSurface = cd.zgeniuscoders.genesis.core.presentation.theme.inverseSurfaceDark,
    inverseOnSurface = cd.zgeniuscoders.genesis.core.presentation.theme.inverseOnSurfaceDark,
    inversePrimary = cd.zgeniuscoders.genesis.core.presentation.theme.inversePrimaryDark,
    surfaceDim = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceDimDark,
    surfaceBright = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceBrightDark,
    surfaceContainerLowest = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerLowestDark,
    surfaceContainerLow = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerLowDark,
    surfaceContainer = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerDark,
    surfaceContainerHigh = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerHighDark,
    surfaceContainerHighest = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = cd.zgeniuscoders.genesis.core.presentation.theme.primaryLightMediumContrast,
    onPrimary = cd.zgeniuscoders.genesis.core.presentation.theme.onPrimaryLightMediumContrast,
    primaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.primaryContainerLightMediumContrast,
    onPrimaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onPrimaryContainerLightMediumContrast,
    secondary = cd.zgeniuscoders.genesis.core.presentation.theme.secondaryLightMediumContrast,
    onSecondary = cd.zgeniuscoders.genesis.core.presentation.theme.onSecondaryLightMediumContrast,
    secondaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.secondaryContainerLightMediumContrast,
    onSecondaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onSecondaryContainerLightMediumContrast,
    tertiary = cd.zgeniuscoders.genesis.core.presentation.theme.tertiaryLightMediumContrast,
    onTertiary = cd.zgeniuscoders.genesis.core.presentation.theme.onTertiaryLightMediumContrast,
    tertiaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.tertiaryContainerLightMediumContrast,
    onTertiaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onTertiaryContainerLightMediumContrast,
    error = cd.zgeniuscoders.genesis.core.presentation.theme.errorLightMediumContrast,
    onError = cd.zgeniuscoders.genesis.core.presentation.theme.onErrorLightMediumContrast,
    errorContainer = cd.zgeniuscoders.genesis.core.presentation.theme.errorContainerLightMediumContrast,
    onErrorContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onErrorContainerLightMediumContrast,
    background = cd.zgeniuscoders.genesis.core.presentation.theme.backgroundLightMediumContrast,
    onBackground = cd.zgeniuscoders.genesis.core.presentation.theme.onBackgroundLightMediumContrast,
    surface = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceLightMediumContrast,
    onSurface = cd.zgeniuscoders.genesis.core.presentation.theme.onSurfaceLightMediumContrast,
    surfaceVariant = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceVariantLightMediumContrast,
    onSurfaceVariant = cd.zgeniuscoders.genesis.core.presentation.theme.onSurfaceVariantLightMediumContrast,
    outline = cd.zgeniuscoders.genesis.core.presentation.theme.outlineLightMediumContrast,
    outlineVariant = cd.zgeniuscoders.genesis.core.presentation.theme.outlineVariantLightMediumContrast,
    scrim = cd.zgeniuscoders.genesis.core.presentation.theme.scrimLightMediumContrast,
    inverseSurface = cd.zgeniuscoders.genesis.core.presentation.theme.inverseSurfaceLightMediumContrast,
    inverseOnSurface = cd.zgeniuscoders.genesis.core.presentation.theme.inverseOnSurfaceLightMediumContrast,
    inversePrimary = cd.zgeniuscoders.genesis.core.presentation.theme.inversePrimaryLightMediumContrast,
    surfaceDim = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceDimLightMediumContrast,
    surfaceBright = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceBrightLightMediumContrast,
    surfaceContainerLowest = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerLowLightMediumContrast,
    surfaceContainer = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerLightMediumContrast,
    surfaceContainerHigh = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = cd.zgeniuscoders.genesis.core.presentation.theme.primaryLightHighContrast,
    onPrimary = cd.zgeniuscoders.genesis.core.presentation.theme.onPrimaryLightHighContrast,
    primaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.primaryContainerLightHighContrast,
    onPrimaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onPrimaryContainerLightHighContrast,
    secondary = cd.zgeniuscoders.genesis.core.presentation.theme.secondaryLightHighContrast,
    onSecondary = cd.zgeniuscoders.genesis.core.presentation.theme.onSecondaryLightHighContrast,
    secondaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.secondaryContainerLightHighContrast,
    onSecondaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onSecondaryContainerLightHighContrast,
    tertiary = cd.zgeniuscoders.genesis.core.presentation.theme.tertiaryLightHighContrast,
    onTertiary = cd.zgeniuscoders.genesis.core.presentation.theme.onTertiaryLightHighContrast,
    tertiaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.tertiaryContainerLightHighContrast,
    onTertiaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onTertiaryContainerLightHighContrast,
    error = cd.zgeniuscoders.genesis.core.presentation.theme.errorLightHighContrast,
    onError = cd.zgeniuscoders.genesis.core.presentation.theme.onErrorLightHighContrast,
    errorContainer = cd.zgeniuscoders.genesis.core.presentation.theme.errorContainerLightHighContrast,
    onErrorContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onErrorContainerLightHighContrast,
    background = cd.zgeniuscoders.genesis.core.presentation.theme.backgroundLightHighContrast,
    onBackground = cd.zgeniuscoders.genesis.core.presentation.theme.onBackgroundLightHighContrast,
    surface = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceLightHighContrast,
    onSurface = cd.zgeniuscoders.genesis.core.presentation.theme.onSurfaceLightHighContrast,
    surfaceVariant = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceVariantLightHighContrast,
    onSurfaceVariant = cd.zgeniuscoders.genesis.core.presentation.theme.onSurfaceVariantLightHighContrast,
    outline = cd.zgeniuscoders.genesis.core.presentation.theme.outlineLightHighContrast,
    outlineVariant = cd.zgeniuscoders.genesis.core.presentation.theme.outlineVariantLightHighContrast,
    scrim = cd.zgeniuscoders.genesis.core.presentation.theme.scrimLightHighContrast,
    inverseSurface = cd.zgeniuscoders.genesis.core.presentation.theme.inverseSurfaceLightHighContrast,
    inverseOnSurface = cd.zgeniuscoders.genesis.core.presentation.theme.inverseOnSurfaceLightHighContrast,
    inversePrimary = cd.zgeniuscoders.genesis.core.presentation.theme.inversePrimaryLightHighContrast,
    surfaceDim = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceDimLightHighContrast,
    surfaceBright = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceBrightLightHighContrast,
    surfaceContainerLowest = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerLowLightHighContrast,
    surfaceContainer = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerLightHighContrast,
    surfaceContainerHigh = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = cd.zgeniuscoders.genesis.core.presentation.theme.primaryDarkMediumContrast,
    onPrimary = cd.zgeniuscoders.genesis.core.presentation.theme.onPrimaryDarkMediumContrast,
    primaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.primaryContainerDarkMediumContrast,
    onPrimaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onPrimaryContainerDarkMediumContrast,
    secondary = cd.zgeniuscoders.genesis.core.presentation.theme.secondaryDarkMediumContrast,
    onSecondary = cd.zgeniuscoders.genesis.core.presentation.theme.onSecondaryDarkMediumContrast,
    secondaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.secondaryContainerDarkMediumContrast,
    onSecondaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onSecondaryContainerDarkMediumContrast,
    tertiary = cd.zgeniuscoders.genesis.core.presentation.theme.tertiaryDarkMediumContrast,
    onTertiary = cd.zgeniuscoders.genesis.core.presentation.theme.onTertiaryDarkMediumContrast,
    tertiaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onTertiaryContainerDarkMediumContrast,
    error = cd.zgeniuscoders.genesis.core.presentation.theme.errorDarkMediumContrast,
    onError = cd.zgeniuscoders.genesis.core.presentation.theme.onErrorDarkMediumContrast,
    errorContainer = cd.zgeniuscoders.genesis.core.presentation.theme.errorContainerDarkMediumContrast,
    onErrorContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onErrorContainerDarkMediumContrast,
    background = cd.zgeniuscoders.genesis.core.presentation.theme.backgroundDarkMediumContrast,
    onBackground = cd.zgeniuscoders.genesis.core.presentation.theme.onBackgroundDarkMediumContrast,
    surface = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceDarkMediumContrast,
    onSurface = cd.zgeniuscoders.genesis.core.presentation.theme.onSurfaceDarkMediumContrast,
    surfaceVariant = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceVariantDarkMediumContrast,
    onSurfaceVariant = cd.zgeniuscoders.genesis.core.presentation.theme.onSurfaceVariantDarkMediumContrast,
    outline = cd.zgeniuscoders.genesis.core.presentation.theme.outlineDarkMediumContrast,
    outlineVariant = cd.zgeniuscoders.genesis.core.presentation.theme.outlineVariantDarkMediumContrast,
    scrim = cd.zgeniuscoders.genesis.core.presentation.theme.scrimDarkMediumContrast,
    inverseSurface = cd.zgeniuscoders.genesis.core.presentation.theme.inverseSurfaceDarkMediumContrast,
    inverseOnSurface = cd.zgeniuscoders.genesis.core.presentation.theme.inverseOnSurfaceDarkMediumContrast,
    inversePrimary = cd.zgeniuscoders.genesis.core.presentation.theme.inversePrimaryDarkMediumContrast,
    surfaceDim = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceDimDarkMediumContrast,
    surfaceBright = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerLowDarkMediumContrast,
    surfaceContainer = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = cd.zgeniuscoders.genesis.core.presentation.theme.primaryDarkHighContrast,
    onPrimary = cd.zgeniuscoders.genesis.core.presentation.theme.onPrimaryDarkHighContrast,
    primaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.primaryContainerDarkHighContrast,
    onPrimaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onPrimaryContainerDarkHighContrast,
    secondary = cd.zgeniuscoders.genesis.core.presentation.theme.secondaryDarkHighContrast,
    onSecondary = cd.zgeniuscoders.genesis.core.presentation.theme.onSecondaryDarkHighContrast,
    secondaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.secondaryContainerDarkHighContrast,
    onSecondaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onSecondaryContainerDarkHighContrast,
    tertiary = cd.zgeniuscoders.genesis.core.presentation.theme.tertiaryDarkHighContrast,
    onTertiary = cd.zgeniuscoders.genesis.core.presentation.theme.onTertiaryDarkHighContrast,
    tertiaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.tertiaryContainerDarkHighContrast,
    onTertiaryContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onTertiaryContainerDarkHighContrast,
    error = cd.zgeniuscoders.genesis.core.presentation.theme.errorDarkHighContrast,
    onError = cd.zgeniuscoders.genesis.core.presentation.theme.onErrorDarkHighContrast,
    errorContainer = cd.zgeniuscoders.genesis.core.presentation.theme.errorContainerDarkHighContrast,
    onErrorContainer = cd.zgeniuscoders.genesis.core.presentation.theme.onErrorContainerDarkHighContrast,
    background = cd.zgeniuscoders.genesis.core.presentation.theme.backgroundDarkHighContrast,
    onBackground = cd.zgeniuscoders.genesis.core.presentation.theme.onBackgroundDarkHighContrast,
    surface = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceDarkHighContrast,
    onSurface = cd.zgeniuscoders.genesis.core.presentation.theme.onSurfaceDarkHighContrast,
    surfaceVariant = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceVariantDarkHighContrast,
    onSurfaceVariant = cd.zgeniuscoders.genesis.core.presentation.theme.onSurfaceVariantDarkHighContrast,
    outline = cd.zgeniuscoders.genesis.core.presentation.theme.outlineDarkHighContrast,
    outlineVariant = cd.zgeniuscoders.genesis.core.presentation.theme.outlineVariantDarkHighContrast,
    scrim = cd.zgeniuscoders.genesis.core.presentation.theme.scrimDarkHighContrast,
    inverseSurface = cd.zgeniuscoders.genesis.core.presentation.theme.inverseSurfaceDarkHighContrast,
    inverseOnSurface = cd.zgeniuscoders.genesis.core.presentation.theme.inverseOnSurfaceDarkHighContrast,
    inversePrimary = cd.zgeniuscoders.genesis.core.presentation.theme.inversePrimaryDarkHighContrast,
    surfaceDim = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceDimDarkHighContrast,
    surfaceBright = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceBrightDarkHighContrast,
    surfaceContainerLowest = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerLowDarkHighContrast,
    surfaceContainer = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerDarkHighContrast,
    surfaceContainerHigh = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = cd.zgeniuscoders.genesis.core.presentation.theme.surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun GenesisTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

