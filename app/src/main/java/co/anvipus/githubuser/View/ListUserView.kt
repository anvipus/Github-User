package co.anvipus.githubuser.View

import co.anvipus.githubuser.Model.Users
import co.anvipus.githubuser.Utility.Network.GetListUsersResponse

/**
 * Created by Anvipus on 13/02/18.
 */
interface ListUserView {
    fun initiateUI()

    fun showProgress()

    fun hideProgress()

    fun setRefreshing(param: Boolean?)

    fun showDialogNetworkAvailable()

    fun showMessageDialog(message: String)

    fun loadData(listUsers: List<Users>)

    fun loadData(getListUsersResponse: GetListUsersResponse)
}