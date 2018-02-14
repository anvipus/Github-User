package co.anvipus.githubuser.Presenter

import co.anvipus.githubuser.View.HomeView
import co.anvipus.githubuser.View.UserDetailParentView

/**
 * Created by Anvipus on 13/02/18.
 */
class UserDetailParentPresenterImpl (private val view: UserDetailParentView) : UserDetailParentPresenter {
    override fun loadUI() {
        view.initiateUI()
    }
}