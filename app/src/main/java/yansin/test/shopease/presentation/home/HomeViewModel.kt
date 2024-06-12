package yansin.test.shopease.presentation.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import yansin.test.shopease.core.util.Resource
import yansin.test.shopease.core.util.Utils
import yansin.test.shopease.domain.HomeRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val application: Application
) : ViewModel() {

    private val _homeState = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val homeState = _homeState.asStateFlow()


    init {
        getProductList()
    }

    val getBanners = homeRepository.getBanners()
    val getAdvBanners = homeRepository.getAdvBanners()
    val getFeaturedProducts = homeRepository.getFeaturedProducts()
    val getCategory = homeRepository.getCategory()
    val getMostPopularProducts = homeRepository.getMostPopularProducts()


    private fun getProductList() = viewModelScope.launch {
        homeRepository.getProductList(Utils.isInternetAvailable(application)).collectLatest {
            when (it) {
                Resource.Empty -> {}
                is Resource.Error -> _homeState.emit(HomeUiState.Error(it.error))
                Resource.Loading -> _homeState.emit(HomeUiState.Loading)
                is Resource.Success -> _homeState.emit(HomeUiState.Success(it.value))
            }
        }
    }
}


