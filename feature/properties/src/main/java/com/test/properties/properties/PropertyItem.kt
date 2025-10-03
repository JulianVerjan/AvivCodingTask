package com.test.properties.properties

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.test.properties.R
import com.test.properties.model.PropertyUiModel
import com.test.properties.ui.theme.AvivTaskTheme
import com.test.properties.ui.theme.PurpleGrey80

@Composable
fun PropertyItem(
    propertyUiModel: PropertyUiModel,
    onItemClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .clickable {
                onItemClicked(propertyUiModel.id)
            },
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(modifier = Modifier.background(PurpleGrey80)) {
            PropertyImage(imageUrl = propertyUiModel.url)
            PropertyInfo(propertyUiModel = propertyUiModel)
        }
    }
}

@Composable
private fun PropertyInfo(
    propertyUiModel: PropertyUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 4.dp, vertical = 8.dp)
    ) {

        Row {
            Icon(
                painter = painterResource(id = R.drawable.bg_house),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = propertyUiModel.propertyType,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }

        Row {
            Icon(
                painter = painterResource(id = R.drawable.bg_location),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = propertyUiModel.city,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }

        Row {
            Icon(
                painter = painterResource(id = R.drawable.bg_price),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = propertyUiModel.price,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
fun PropertyImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        modifier = modifier.fillMaxWidth(),
        model = imageUrl,
        contentDescription = null,
        placeholder = painterResource(R.drawable.bg_placeholder),
        error = painterResource(R.drawable.bg_placeholder),
        fallback = painterResource(R.drawable.bg_placeholder),
        contentScale = ContentScale.FillWidth,
    )
}

@Preview(showBackground = true)
@Composable
fun PropertyItemPreview() {
    AvivTaskTheme {
        PropertyItem(
            propertyUiModel = PropertyUiModel(
                city = "city",
                id = 1,
                url = "",
                price = "1",
                propertyType = "propertyType",
            ),
            onItemClicked = {}
        )
    }
}
