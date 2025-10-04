package com.test.realestate.compose

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.test.realestate.R

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    val lottieFile = LottieCompositionSpec.RawRes(R.raw.loading)
    val composition by rememberLottieComposition(spec = lottieFile)
    LottieAnimation(
        modifier = modifier.size(250.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}

@Composable
fun ErrorIndicator(
    modifier: Modifier = Modifier
) {
    val lottieFile = LottieCompositionSpec.RawRes(R.raw.no_internet)
    val composition by rememberLottieComposition(spec = lottieFile)
    LottieAnimation(
        modifier = modifier.size(250.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}

@Composable
fun UnknownIndicator(
    modifier: Modifier = Modifier
) {
    val lottieFile = LottieCompositionSpec.RawRes(R.raw.unknown_error)
    val composition by rememberLottieComposition(spec = lottieFile)
    LottieAnimation(
        modifier = modifier.size(250.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}
