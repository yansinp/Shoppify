package yansin.test.shopease.data.repository

import android.util.Log
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import yansin.test.shopease.core.util.Constants
import yansin.test.shopease.core.util.Resource
import yansin.test.shopease.data.local.dao.HomeDao
import yansin.test.shopease.data.local.entity.AdvBannerEntity
import yansin.test.shopease.data.mapper.toAdvBanner
import yansin.test.shopease.data.mapper.toBanner
import yansin.test.shopease.data.mapper.toBannerEntity
import yansin.test.shopease.data.mapper.toCategoryEntity
import yansin.test.shopease.data.mapper.toCategoryModel
import yansin.test.shopease.data.mapper.toFeaturedProduct
import yansin.test.shopease.data.mapper.toFeaturedProductEntity
import yansin.test.shopease.data.mapper.toMostPopularEntity
import yansin.test.shopease.data.mapper.toMostPopularProduct
import yansin.test.shopease.data.source.HomeServiceApi
import yansin.test.shopease.domain.HomeRepository
import yansin.test.shopease.domain.model.AdvBanner
import yansin.test.shopease.domain.model.Banner
import yansin.test.shopease.domain.model.Category
import yansin.test.shopease.domain.model.FeaturedProduct
import yansin.test.shopease.domain.model.MostPopularProduct
import yansin.test.shopease.domain.model.ProductResponseModel
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeServiceApi: HomeServiceApi,
    private val homeDao: HomeDao
) :
    HomeRepository {
    override fun getProductList(isNetworkAvailable: Boolean): Flow<Resource<List<ProductResponseModel>>> =
        flow {

            emit(Resource.Loading)

            if (isNetworkAvailable) {
                homeServiceApi.getProductList().suspendOnSuccess {
                    val response = this.data
                    if (isNetworkAvailable) {

                        Log.d("API Success", "Data: ${response}")
                        if (response.isNotEmpty()) {

                            val bannerEntity =
                                response.firstOrNull { it.title == BANNER }?.contents?.mapNotNull { it?.toBannerEntity() }
                            val advBannerContent =
                                response.firstOrNull { it.title == SINGLE_IMAGE }?.imageUrl
                            val advBannerEntity =
                                advBannerContent?.let { listOf(AdvBannerEntity(0, it)) }
                            val categoryEntity =
                                response.firstOrNull { it.title == CATEGORY }?.contents?.mapNotNull { it?.toCategoryEntity() }
                            val featuredProductEntity =
                                response.firstOrNull { it.title == FEATURED_PRODUCT }?.contents?.mapNotNull { it?.toFeaturedProductEntity() }
                            val mostPopularEntity =
                                response.firstOrNull { it.title == MOST_POPULAR }?.contents?.mapNotNull { it?.toMostPopularEntity() }

                            homeDao.clearAllTables()
                            homeDao.insertProducts(featuredProductEntity)
                            homeDao.insertCategories(categoryEntity)
                            homeDao.insertBanners(bannerEntity)
                            homeDao.insertAdvBanner(advBannerEntity)
                            homeDao.insertMostPopular(mostPopularEntity)


                        } else {
                            Log.d("API Call", "Empty response")
                            emit(Resource.Error(Constants.GENERAL_ERROR_MESSAGE))
                        }

                    } else {
                        Log.e("API Error", "No Network Available")
                        emit(Resource.Error(Constants.NO_INTERNET_ERROR_MESSAGE))
                    }
                }
                    .suspendOnError {
                        try {
                            when (this.statusCode) {
                                StatusCode.InternalServerError -> emit(Resource.Error(Constants.SERVER_ERROR))
                                else -> emit(Resource.Error("Unable fetch to movie data"))
                            }
                        } catch (e: Exception) {
                            emit(Resource.Error(Constants.GENERAL_ERROR_MESSAGE))
                        }
                    }
                    .suspendOnException { emit(Resource.Error(Constants.NO_INTERNET_ERROR_MESSAGE)) }
            }
        }

    override fun getBanners(): Flow<List<Banner>> {
        return homeDao.getBanners().map { it.map { it.toBanner() } }
    }

    override fun getAdvBanners(): Flow<List<AdvBanner>> {
        return homeDao.getAdvBanner().map { it.map { it.toAdvBanner() } }
    }

    override fun getCategory(): Flow<List<Category>> {
        return homeDao.getCategories().map { it.map { it.toCategoryModel() } }
    }

    override fun getMostPopularProducts(): Flow<List<MostPopularProduct>> {
        return homeDao.getMostPopular().map { it.map { it.toMostPopularProduct() } }
    }

    override fun getFeaturedProducts(): Flow<List<FeaturedProduct>> {
        return homeDao.getFeaturedProducts().map { it.map { it.toFeaturedProduct() } }
    }

    companion object {
        const val BANNER = "Top banner"
        const val MOST_POPULAR = "Best Sellers"
        const val SINGLE_IMAGE = "ad banner"
        const val CATEGORY = "Top categories"
        const val FEATURED_PRODUCT = "Most Popular"

    }
}