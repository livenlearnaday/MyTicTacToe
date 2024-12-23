import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import io.github.livenlearnaday.mytictactoe.ui.theme.backgroundLight
import io.github.livenlearnaday.mytictactoe.ui.theme.errorContainerLight
import io.github.livenlearnaday.mytictactoe.ui.theme.errorLight
import io.github.livenlearnaday.mytictactoe.ui.theme.inverseOnSurfaceLight
import io.github.livenlearnaday.mytictactoe.ui.theme.inversePrimaryLight
import io.github.livenlearnaday.mytictactoe.ui.theme.inverseSurfaceLight
import io.github.livenlearnaday.mytictactoe.ui.theme.onBackgroundLight
import io.github.livenlearnaday.mytictactoe.ui.theme.onErrorContainerLight
import io.github.livenlearnaday.mytictactoe.ui.theme.onErrorLight
import io.github.livenlearnaday.mytictactoe.ui.theme.onPrimaryContainerLight
import io.github.livenlearnaday.mytictactoe.ui.theme.onPrimaryLight
import io.github.livenlearnaday.mytictactoe.ui.theme.onSecondaryContainerLight
import io.github.livenlearnaday.mytictactoe.ui.theme.onSecondaryLight
import io.github.livenlearnaday.mytictactoe.ui.theme.onSurfaceLight
import io.github.livenlearnaday.mytictactoe.ui.theme.onSurfaceVariantLight
import io.github.livenlearnaday.mytictactoe.ui.theme.onTertiaryContainerLight
import io.github.livenlearnaday.mytictactoe.ui.theme.onTertiaryLight
import io.github.livenlearnaday.mytictactoe.ui.theme.outlineLight
import io.github.livenlearnaday.mytictactoe.ui.theme.outlineVariantLight
import io.github.livenlearnaday.mytictactoe.ui.theme.primaryContainerLight
import io.github.livenlearnaday.mytictactoe.ui.theme.primaryLight
import io.github.livenlearnaday.mytictactoe.ui.theme.scrimLight
import io.github.livenlearnaday.mytictactoe.ui.theme.secondaryContainerLight
import io.github.livenlearnaday.mytictactoe.ui.theme.secondaryLight
import io.github.livenlearnaday.mytictactoe.ui.theme.shapes
import io.github.livenlearnaday.mytictactoe.ui.theme.surfaceBrightLight
import io.github.livenlearnaday.mytictactoe.ui.theme.surfaceContainerHighLight
import io.github.livenlearnaday.mytictactoe.ui.theme.surfaceContainerHighestLight
import io.github.livenlearnaday.mytictactoe.ui.theme.surfaceContainerLight
import io.github.livenlearnaday.mytictactoe.ui.theme.surfaceContainerLowLight
import io.github.livenlearnaday.mytictactoe.ui.theme.surfaceContainerLowestLight
import io.github.livenlearnaday.mytictactoe.ui.theme.surfaceDimLight
import io.github.livenlearnaday.mytictactoe.ui.theme.surfaceLight
import io.github.livenlearnaday.mytictactoe.ui.theme.surfaceVariantLight
import io.github.livenlearnaday.mytictactoe.ui.theme.tertiaryContainerLight
import io.github.livenlearnaday.mytictactoe.ui.theme.tertiaryLight
import io.github.livenlearnaday.mytictactoe.ui.theme.typography

private val LightColorScheme  = lightColorScheme(
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



@Composable
internal fun MyTicTacToeTheme(
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = typography,
        shapes = shapes,
        content = {
            Surface(content = content)
        }
    )
}