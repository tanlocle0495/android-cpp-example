package locle.android.com.myapplication.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import locle.android.com.myapplication.crypt.AndroidKeystore
import locle.android.com.myapplication.crypt.AndroidKeystoreImp

@Module
@InstallIn(ActivityComponent::class)
abstract class StoreModule {
    @Binds
    abstract fun bindCryptAndroidKeyStore(cryptAndroidKeystoreImp: AndroidKeystoreImp): AndroidKeystore
}