package co.anvipus.githubuser.Utility.Network

import co.anvipus.githubuser.Model.Users
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.ArrayList

/**
 * Created by Anvipus on 13/02/18.
 */
class GetListUsersResponse : Serializable {
    @SerializedName("total_count")
    @Expose
    var total_count: Int? = null

    @SerializedName("incomplete_results")
    @Expose
    var incomplete_results: Boolean? = null

    @SerializedName("items")
    @Expose
    var items: List<Users> = ArrayList()
}