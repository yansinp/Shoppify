package yansin.test.shopease.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import yansin.test.shopease.data.local.dao.HomeDao
import yansin.test.shopease.data.local.entity.AdvBannerEntity
import yansin.test.shopease.data.local.entity.BannerEntity
import yansin.test.shopease.data.local.entity.CategoryEntity
import yansin.test.shopease.data.local.entity.FeaturedProductEntity
import yansin.test.shopease.data.local.entity.MostPopularEntity

@Database(
    entities = [BannerEntity::class,
        FeaturedProductEntity::class,
        CategoryEntity::class,
        AdvBannerEntity::class,
        MostPopularEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ShopDatabase : RoomDatabase() {
    abstract val dao: HomeDao
}