package pe.com.dreads.wowk.utils

import java.security.NoSuchAlgorithmException

/**
 * Created by Dreads on 19/11/2017.
 */
fun md5(s: String): String {
    try {
        val digest = java.security.MessageDigest.getInstance("MD5")
        digest.update(s.toByteArray())
        val hash = (Strings.hexEncode(digest.digest())).toString()

        return hash
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}