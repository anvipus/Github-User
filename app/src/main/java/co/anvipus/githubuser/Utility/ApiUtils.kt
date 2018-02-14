package co.anvipus.githubuser.Utility

/**
 * Created by Anvipus on 13/02/18.
 */
object ApiUtils {
    val webService: WebService
        get() = RetrofitClient.getClient(Constants.url_api).create(WebService::class.java)
}