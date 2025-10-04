package com.test.realestate.state

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.test.realestate.R
import com.test.realestate.model.PropertyDetailUiModel

@Immutable
sealed class PropertyDetailUiState {
    data object Loading : PropertyDetailUiState()

    data class Complete(
        val uiModel: PropertyDetailUiModel?,
    ) : PropertyDetailUiState()

    sealed class Error : PropertyDetailUiState() {

        @get:StringRes
        abstract val errorMessageStringResource: Int

        @get:DrawableRes
        abstract val errorDrawableResource: Int

        data object Connection : Error() {
            override val errorMessageStringResource: Int =
                R.string.screen_properties_error_title
            override val errorDrawableResource: Int =
                R.raw.no_internet
        }

        data object Unknown : Error() {
            override val errorMessageStringResource: Int = R.string.screen_properties_unknown_error_title
            override val errorDrawableResource: Int = R.raw.unknown_error
        }
    }
}