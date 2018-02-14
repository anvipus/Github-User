package co.anvipus.githubuser.Presenter

import android.content.Context

/**
 * Created by Anvipus on 13/02/18.
 */
interface ListUserPresenter {
    fun loadUI()

    fun getUserList(mContext: Context,isShowProgress: Boolean)

    fun getUserListByUsername(mContext: Context,isShowProgress: Boolean,username: String)
}