package locle.android.com.myapplication

import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import locle.android.com.myapplication.databinding.ActivityMainBinding
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey

class MainActivity : AppCompatActivity() {

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    private lateinit var keyStore: KeyStore
    private lateinit var keyPair: KeyPair
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initKeyStore()
    }

    // Create  Object
    private fun initKeyStore() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)// load null for  first
        } catch (e: Exception) {
            Log.e("ERROR", "${e.message}")
        }
    }

    // create Key alias it will create  public key and private key whe use RSA for encrypt data
    @RequiresApi(Build.VERSION_CODES.M)
    private fun createKey(alias: String) {
        try {
            if (!keyStore.containsAlias(alias)) {
                val keyPairGenerator =
                    KeyPairGenerator.getInstance(
                        KeyProperties.KEY_ALGORITHM_RSA,
                        "AndroidKeyStore"
                    )// return key tor have type "AndroidKeyStore"
                /*----------*/
                val paramSpec = KeyGenParameterSpec
                    .Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1).build()
                /*----------*/
                keyPairGenerator.initialize(paramSpec)
                keyPair = keyPairGenerator.genKeyPair()
            } else Toast.makeText(this, "Alias exist!!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.d("ERROR", e.message.toString())
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

    external fun stringFromJNI(): String

}