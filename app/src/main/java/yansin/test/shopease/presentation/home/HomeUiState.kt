package yansin.test.shopease.presentation.home

import yansin.test.shopease.data.remote.dto.ProductResponseDtoItem
import yansin.test.shopease.domain.model.ProductResponseModel


sealed class HomeUiState {
    data class Success(val productDataData: List<ProductResponseModel>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
    object Loading : HomeUiState()
    object Empty : HomeUiState()
}