package com.test.realestate.compose

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun CenteredAnimation(
    modifier: Modifier = Modifier,
    @RawRes file: Int
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        StateAnimation(file = file)
    }
}

@Composable
private fun StateAnimation(
    modifier: Modifier = Modifier,
    animationSize: Dp = 250.dp,
    @RawRes file: Int
) {
    val lottieFile = LottieCompositionSpec.RawRes(file)
    val composition by rememberLottieComposition(spec = lottieFile)

    LottieAnimation(
        modifier = modifier.size(animationSize),
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}
