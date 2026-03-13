
import android.util.Base64
import org.json.JSONObject

fun String.isTokenExpired(): Boolean {
    return try {
        val parts = this.split(".")
        if (parts.size != 3) return true // invalid token

        val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
        val json = JSONObject(payload)
        val exp = json.getLong("exp")

        val now = System.currentTimeMillis() / 1000 // current time in seconds
        now >= exp
    } catch (e: Exception) {
        e.printStackTrace()
        true // treat as expired if anything goes wrong
    }
}