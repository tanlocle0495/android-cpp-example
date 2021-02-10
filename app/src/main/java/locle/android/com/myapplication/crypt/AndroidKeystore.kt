package locle.android.com.myapplication.crypt

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
import javax.crypto.Cipher


interface AndroidKeystore {
    fun createKeyAliasKey(alias: String)
    fun getKeyInfo(alias: String)
    fun encryptString(text: String, alias: String): String
    fun deCryptString(cipherText: String, alias: String): ByteArray?
}

class AndroidKeystoreImp : AndroidKeystore {
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun createKeyAliasKey(alias: String) {
        try {
            if (!keyStore.containsAlias(alias)) {
                val keyPairGenerator =
                    KeyPairGenerator.getInstance(
                        KeyProperties.KEY_ALGORITHM_RSA, APP_SECURITY_TYPE
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

    override fun getKeyInfo(alias: String) {
        val privateKey: PrivateKey =
            (keyStore.getEntry(alias, null) as KeyStore.PrivateKeyEntry).privateKey
        //public key
        val cert = keyStore.getCertificate(alias)
        val publicKey = cert.publicKey
        // private key
        val publicKeyBytes: ByteArray = Base64.encode(publicKey.encoded, Base64.DEFAULT)
        val pubKeyString = String(publicKeyBytes)
        Log.d("KEY STORE", "------------>${pubKeyString} --- $")
        Log.d("KEY STORE", "------------>${privateKey} --- $")
    }

    override fun encryptString(text: String, alias: String): String {
        val publicKey = keyStore.getCertificate(alias).publicKey
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val cipherText = cipher.doFinal(text.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(cipherText, Base64.DEFAULT)
    }

    override fun deCryptString(cipherText: String, alias: String): ByteArray? {
        val privateKeyEntry = keyStore.getEntry(alias, null) as KeyStore.PrivateKeyEntry
        val privateKey = privateKeyEntry.privateKey
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        val decryptText =
            return cipher.doFinal(Base64.decode(cipherText, Base64.DEFAULT))
    }

}