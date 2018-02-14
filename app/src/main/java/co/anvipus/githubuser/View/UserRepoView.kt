package co.anvipus.githubuser.View

import co.anvipus.githubuser.Model.Repo
import co.anvipus.githubuser.Model.Users

/**
 * Created by Anvipus on 14/02/18.
 */
interface UserRepoView {
    fun initiateUI()

    fun showProgress()

    fun hideProgress()

    fun showDialogNetworkAvailable()

    fun showMessageDialog(message: String)

    fun loadData(listRepo: List<Repo>)
}