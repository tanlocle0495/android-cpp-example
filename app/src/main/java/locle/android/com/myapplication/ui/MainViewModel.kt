package locle.android.com.myapplication.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import locle.android.com.myapplication.crypt.AndroidKeystore
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val security: AndroidKeystore
) : ViewModel() {
    fun securityDataStore() {
    }

    fun generatorAlias(alias: String) {
        security.createKeyAliasKey(alias)
    }

}