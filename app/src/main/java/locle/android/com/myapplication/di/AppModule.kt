package locle.android.com.myapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import locle.android.com.myapplication.crypt.AndroidKeystore
import locle.android.com.myapplication.crypt.AndroidKeystoreImp
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun providerKeyStore(): AndroidKeystore = AndroidKeystoreImp()
}