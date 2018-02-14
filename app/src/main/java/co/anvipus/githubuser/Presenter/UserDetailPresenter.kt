package co.anvipus.githubuser.Presenter

import android.content.Context

/**
 * Created by Anvipus on 14/02/18.
 */
interface UserDetailPresenter {
    fun loadUI()

    fun getUserDetail(mContext: Context, username: String)
}