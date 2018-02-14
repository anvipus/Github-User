package co.anvipus.githubuser.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Anvipus on 14/02/18.
 */
class Repo : Serializable {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("full_name")
    @Expose
    var full_name: String? = null

    @SerializedName("owner")
    @Expose
    var owner: Users? = null

    @SerializedName("html_url")
    @Expose
    var html_url: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("branches_url")
    @Expose
    var branches_url: String? = null

    @SerializedName("default_branch")
    @Expose
    var default_branch: String? = null
}