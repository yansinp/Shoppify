package yansin.test.shopease.domain


import kotlinx.coroutines.flow.Flow
import yansin.test.shopease.core.util.Resource
import yansin.test.shopease.domain.model.AdvBanner
import yansin.test.shopease.domain.model.Banner
import yansin.test.shopease.domain.model.Category
import yansin.test.shopease.domain.model.FeaturedProduct
import yansin.test.shopease.domain.model.MostPopularProduct
import yansin.test.shopease.domain.model.ProductResponseModel

interface HomeRepository {
    fun getProductList(
        isNetworkAvailable: Boolean
    ) : Flow<Resource<List<ProductResponseModel>>>

    fun getBanners():Flow<List<Banner>>
    fun getAdvBanners():Flow<List<AdvBanner>>
    fun getCategory():Flow<List<Category>>
    fun getMostPopularProducts():Flow<List<MostPopularProduct>>
    fun getFeaturedProducts():Flow<List<FeaturedProduct>>
}