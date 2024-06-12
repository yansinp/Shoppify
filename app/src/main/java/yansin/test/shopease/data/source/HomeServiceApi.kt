package yansin.test.shopease.data.source

import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import yansin.test.shopease.data.remote.dto.ProductResponseDtoItem
import yansin.test.shopease.domain.model.ProductResponseModel

interface HomeServiceApi {
    @GET("Todo")
    suspend fun getProductList(): ApiResponse<List<ProductResponseDtoItem>>
}