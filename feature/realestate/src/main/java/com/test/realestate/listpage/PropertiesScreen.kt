package com.test.realestate.listpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.test.realestate.R
import com.test.realestate.ScreenRoute
import com.test.realestate.compose.ErrorIndicator
import com.test.realestate.compose.LoadingIndicator
import com.test.realestate.compose.UnknownIndicator
import com.test.realestate.states.PropertiesUiState
import com.test.realestate.ui.theme.AvivTaskTheme
import com.test.realestate.ui.theme.PurpleGrey80
import com.test.realestate.ui.theme.White
import com.test.realestate.viewmodel.PropertiesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertiesScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: PropertiesViewModel = hiltViewModel(),
) {
    val state = viewModel.propertiesStateFlow.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.main_screen_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PurpleGrey80,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state.value) {
                is PropertiesUiState.Complete -> PropertyList(
                    propertiesUiState = state.value as PropertiesUiState.Complete,
                    onItemClicked = {
                        navController.navigate(ScreenRoute.PropertyDetailScreenRoute.createRoute(it))
                    }
                )

                PropertiesUiState.Error.Connection -> ErrorIndicator()
                PropertiesUiState.Error.Unknown -> UnknownIndicator()
                PropertiesUiState.Loading -> LoadingIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PropertiesScreenPreview() {
    AvivTaskTheme {
        PropertiesScreen(navController = NavHostController(LocalContext.current))
    }
}
