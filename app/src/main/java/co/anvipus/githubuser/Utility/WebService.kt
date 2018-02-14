package co.anvipus.githubuser.Utility

import co.anvipus.githubuser.Model.Repo
import co.anvipus.githubuser.Model.Users
import co.anvipus.githubuser.Utility.Network.GetListUsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Anvipus on 13/02/18.
 */
interface WebService {

    @GET("users")
    fun getListUsers(): Call<List<Users>>

    @GET("search/users")
    fun getListUsersByUsername(@Query("q") q: String): Call<GetListUsersResponse>

    @GET("users/{username}")
    fun getSingleUser(@Path("username") username: String): Call<Users>

    @GET("users/{username}/repos")
    fun getUserRepo(@Path("username") username: String): Call<List<Repo>>
}