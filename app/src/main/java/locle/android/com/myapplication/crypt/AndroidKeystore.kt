package locle.android.com.myapplication.crypt

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import locle.android.com.myapplication.utils.APP_SECURITY_TYPE
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import javax.inject.Inject


interface AndroidKeystore {
}

class AndroidKeystoreImp @Inject constructor(
    private val context: Context
) : AndroidKeystore {
    private lateinit var keyStore: KeyStore
    private lateinit var keyPair: KeyPair

    // Create  Object
    private fun initKeyStore() {
        try {
            keyStore = KeyStore.getInstance(APP_SECURITY_TYPE)
            keyStore.load(null)// load null for  first
        } catch (e: Exception) {
            Log.e("ERROR", "${e.message}")
        }
    }

    init {
        initKeyStore()
    }

    // create Key alias it will create  public key and private key whe use RSA for encrypt data
    @RequiresApi(Build.VERSION_CODES.M)
    private fun createKey(alias: String) {
        try {
            if (!keyStore.containsAlias(alias)) {
                val keyPairGenerator =
                    KeyPairGenerator.getInstance(
                        KeyProperties.KEY_ALGORITHM_RSA,
                        APP_SECURITY_TYPE
                    )// return key tor have type "AndroidKeyStore"
                /*----------*/
                val paramSpec = KeyGenParameterSpec
                    .Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1).build()
                /*----------*/
                keyPairGenerator.initialize(paramSpec)
                keyPair = keyPairGenerator.genKeyPair()
            } else Log.d("CREATE KEY STORE", "alias exist!!")
        } catch (e: Exception) {
            Log.d("CREATE KEY STORE", "ERROR:${e.message}")
        }
    }


    //key will save and save to keystore
    private fun getKeyInfo(alias: String) {
        val privateKey: PrivateKey =
            (keyStore.getEntry(alias, null) as KeyStore.PrivateKeyEntry).privateKey
        //public key
        val cert = keyStore.getCertificate(alias)
        val publicKey = cert.publicKey
        // private key
        val publicKeyBytes: ByteArray = Base64.encode(publicKey.encoded, Base64.DEFAULT)
        val pubKeyString = String(publicKeyBytes)
        Log.d("KEY STORE", "------------>${pubKeyString} --- $")
    }

}