package yansin.test.shopease.core.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yansin.test.shopease.data.local.ShopDatabase
import yansin.test.shopease.data.local.dao.HomeDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideShopDataBase(application: Application) = Room.databaseBuilder(
        application, ShopDatabase::class.java,"shop_database"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideShopDao(shopDatabase: ShopDatabase) : HomeDao{
        return shopDatabase.dao
    }

}