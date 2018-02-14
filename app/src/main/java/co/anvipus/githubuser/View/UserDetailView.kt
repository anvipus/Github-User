package co.anvipus.githubuser.View

import co.anvipus.githubuser.Model.Users

/**
 * Created by Anvipus on 13/02/18.
 */
interface UserDetailView {
    fun initiateUI()

    fun showProgress()

    fun hideProgress()

    fun showDialogNetworkAvailable()

    fun showMessageDialog(message: String)

    fun loadData(user: Users)
}