package co.anvipus.githubuser.Presenter

import android.os.Bundle
import android.support.v4.app.Fragment
import co.anvipus.githubuser.R
import co.anvipus.githubuser.View.HomeView

/**
 * Created by Anvipus on 13/02/18.
 */
class HomePresenterImpl (private val view: HomeView) : HomePresenter {
    override fun loadUI() {
        view.initiateUI()
        view.setListener()
    }
}