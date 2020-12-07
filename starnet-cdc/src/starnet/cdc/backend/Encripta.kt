package starnet.cdc.backend

import sun.misc.BASE64Encoder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class Encripta {
    fun encriptar(senha:String): String {
        return try {
            val digest = MessageDigest.getInstance("MD5")
            digest.update(senha.toByteArray())
            val encoder = BASE64Encoder()
            encoder.encode(digest.digest())
        } catch (ns: NoSuchAlgorithmException) {
            ns.printStackTrace()
            senha
        }
    }
}