package com.test.realestate.listpage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.realestate.model.PropertyUiModel
import com.test.realestate.states.PropertiesUiState
import com.test.realestate.ui.theme.AvivTaskTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PropertyList(
    propertiesUiState: PropertiesUiState.Complete,
    onItemClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(propertiesUiState.uiModels) { item ->
            PropertyItem(
                modifier = Modifier.fillMaxWidth(),
                propertyUiModel = item,
                onItemClicked = onItemClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PropertiesListPreview() {
    AvivTaskTheme {
        PropertyList(
            propertiesUiState = PropertiesUiState.Complete(
                persistentListOf(
                    PropertyUiModel(
                        city = "city",
                        id = 1,
                        url = "",
                        price = "1",
                        propertyType = "propertyType",
                    ),
                    PropertyUiModel(
                        city = "city",
                        id = 2,
                        url = "",
                        price = "1",
                        propertyType = "propertyType",
                    ),
                    PropertyUiModel(
                        city = "city",
                        id = 3,
                        url = "",
                        price = "3",
                        propertyType = "propertyType",
                    )
                )
            ),
            onItemClicked = {}
        )
    }
}