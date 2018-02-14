package co.anvipus.githubuser.Utility

import android.content.Context
import android.net.ConnectivityManager
import com.scottyab.aescrypt.AESCrypt

/**
 * Created by Anvipus on 13/02/18.
 */
object Helpers {
    fun isNetworkAvailable(context: Context?): Boolean {
        try{
            if(context != null){
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnectedOrConnecting
            }else{
                return false
            }
        }catch (e: Exception){
            e.printStackTrace()
            return false
        }

    }

    fun getWebService(): WebService {

        return ApiUtils.webService
    }

    fun getStringPreferences(mContext: Context, encryptedMsg: String?): String? {
        var messageAfterDecrypt = ""
        if (encryptedMsg == null) {
            return null
        } else {
            try {
                if (encryptedMsg == null) {
                    return null
                } else {
                    messageAfterDecrypt = AESCrypt.decrypt("password", encryptedMsg)
                }
            } catch (e: Exception) {
                // Log error
            }

        }

        return messageAfterDecrypt
    }
}