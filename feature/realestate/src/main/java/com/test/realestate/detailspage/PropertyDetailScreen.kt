package com.test.realestate.detailspage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.test.realestate.R
import com.test.realestate.compose.ErrorIndicator
import com.test.realestate.compose.LoadingIndicator
import com.test.realestate.compose.UnknownIndicator
import com.test.realestate.model.PropertyDetailUiModel
import com.test.realestate.listpage.PropertiesScreen
import com.test.realestate.state.PropertyDetailUiState
import com.test.realestate.ui.theme.AvivTaskTheme
import com.test.realestate.ui.theme.PurpleGrey80
import com.test.realestate.viewmodel.PropertyDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: PropertyDetailViewModel = hiltViewModel(),
) {
    val state = viewModel.propertyDetailStateFlow.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.screen_details_title)) },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PurpleGrey80,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        when (state.value) {
            is PropertyDetailUiState.Complete -> {
                (state.value as PropertyDetailUiState.Complete).uiModel?.let {
                    PropertyDetailInfo(
                        modifier = Modifier.padding(innerPadding),
                        propertyDetailUiModel = it
                    )
                }
            }

            PropertyDetailUiState.Error.Connection -> ErrorIndicator(
                modifier = Modifier
                    .padding(
                        innerPadding
                    )
                    .fillMaxSize()
            )

            PropertyDetailUiState.Error.Unknown -> UnknownIndicator(
                modifier = Modifier
                    .padding(
                        innerPadding
                    )
                    .fillMaxSize()
            )

            PropertyDetailUiState.Loading -> LoadingIndicator(
                modifier = Modifier
                    .padding(
                        innerPadding
                    )
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun PropertyDetailInfo(
    propertyDetailUiModel: PropertyDetailUiModel,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            AsyncImage(
                model = propertyDetailUiModel.url,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                propertyDetailUiModel.bedrooms?.let {
                    Text(text = stringResource(R.string.screen_details_bedrooms, it))
                }
                propertyDetailUiModel.rooms?.let {
                    Text(
                        stringResource(
                            R.string.screen_details_rooms,
                            it
                        )
                    )
                }
                Text(
                    text = stringResource(
                        R.string.screen_details_area,
                        propertyDetailUiModel.area
                    )
                )
                Text(stringResource(R.string.screen_details_price, propertyDetailUiModel.price))
                Text(
                    stringResource(
                        R.string.screen_details_type,
                        propertyDetailUiModel.propertyType
                    )
                )
                Text(
                    stringResource(
                        R.string.screen_details_professional,
                        propertyDetailUiModel.professional
                    )
                )
                Text(
                    stringResource(
                        R.string.screen_details_offer,
                        propertyDetailUiModel.offerType
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AvivTaskTheme {
        PropertiesScreen(navController = NavHostController(LocalContext.current))
    }
}
