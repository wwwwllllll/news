package com.wuruoye.news.model.util

import android.util.Base64
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.MGF1ParameterSpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 20:30.
 * @Description :
 */
object SecretUtil {
    private val PUBLIC_RSA_KEY =
                    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDE+n0Mx/S7y9p5tln9G2LlXvFi\n" +
                    "iAfA8JSqowkMVuBY2Jg0nOoTYU0c6eULvRCZ0qBNwtybey6MFsfti/XPGUdaVVMg\n" +
                    "cl6zJFdz//6J+L0DxJWmwMUGmFwCAVrw3Sl+kqtTOTyIm5DcMD8BrKbzC/xJz7eI\n" +
                    "0P/qQyCCnZwjW6D1sQIDAQAB\n"

    fun getPublicSecret(content: String): String{
        val buffer = Base64.decode(PUBLIC_RSA_KEY, Base64.DEFAULT)
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(buffer)
        val key = keyFactory.generatePublic(keySpec) as PublicKey

        val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding",
                "BC")
        cipher.init(Cipher.ENCRYPT_MODE, key, OAEPParameterSpec("SHA-256",
                "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT))
        val data = cipher.doFinal(content.toByteArray())
        return Base64.encodeToString(data, Base64.DEFAULT)
    }
}