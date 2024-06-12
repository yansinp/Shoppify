package yansin.test.shopease.domain.model

import yansin.test.shopease.domain.model.AdvBanner
import yansin.test.shopease.domain.model.Banner
import yansin.test.shopease.domain.model.Category
import yansin.test.shopease.domain.model.FeaturedProduct
import yansin.test.shopease.domain.model.MostPopularProduct

data class ProductResponseModel(
    val category: List<Category>,
    val banner: List<Banner>,
    val advBanner: List<AdvBanner>,
    val mostPopularProduct: List<MostPopularProduct>,
    val featuredProduct: List<FeaturedProduct>
)
