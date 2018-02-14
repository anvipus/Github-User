package co.anvipus.githubuser.Presenter

import android.content.Context

/**
 * Created by Anvipus on 14/02/18.
 */
interface UserRepoPresenter {
    fun loadUI()

    fun getUserRepo(mContext: Context, userName: String)
}