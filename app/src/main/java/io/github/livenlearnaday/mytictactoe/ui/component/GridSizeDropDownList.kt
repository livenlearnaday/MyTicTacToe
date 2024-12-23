package io.github.livenlearnaday.mytictactoe.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun GridSizeDropDownList(
    gridSizeInput: String,
    items: List<String> = listOf("3", "4", "5", "6"),
    onSelect: (itemSelected: String) -> Unit
) {
val isDropDownExpanded = remember {
    mutableStateOf(false)
}

var itemPosition by remember {
    mutableIntStateOf(0)
}


Column(
modifier = Modifier.width(30.dp),
horizontalAlignment = Alignment.CenterHorizontally,
verticalArrangement = Arrangement.Center
) {

    Box {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isDropDownExpanded.value = true
            }
        ) {
            Text(text = items[itemPosition])
            Icon(
                contentDescription = null,
                imageVector = Icons.Filled.ArrowDropDown
            )

        }
        DropdownMenu(
            expanded = isDropDownExpanded.value,
            onDismissRequest = {
                isDropDownExpanded.value = false
            }) {
            items.forEachIndexed { index, gridSizeOption ->
                DropdownMenuItem(text = {
                    Text(text = gridSizeOption)
                },
                    onClick = {
                        isDropDownExpanded.value = false
                        itemPosition = index
                        onSelect(gridSizeOption)
                    })
            }
        }
    }

}
}


@Preview(showBackground = true)
@Composable
fun GridSizeDropDownListPreview() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            GridSizeDropDownList(
                gridSizeInput = "3",
                onSelect = {}
            )
        }
}