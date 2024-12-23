package io.github.livenlearnaday.mytictactoe.ui.component


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CommonButton(onClick: () -> Unit, displayText: String,  modifier: Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = displayText,
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun ButtonWithIcon(onClick: () -> Unit, displayText: String,  modifier: Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = displayText,
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.fillMaxWidth()
        )
    }
}