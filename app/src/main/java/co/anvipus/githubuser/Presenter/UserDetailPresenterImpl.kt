package co.anvipus.githubuser.Presenter

import android.content.Context
import co.anvipus.githubuser.Model.Users
import co.anvipus.githubuser.Utility.Helpers
import co.anvipus.githubuser.Utility.Network.GetListUsersResponse
import co.anvipus.githubuser.View.ListUserView
import co.anvipus.githubuser.View.UserDetailView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Anvipus on 14/02/18.
 */
class UserDetailPresenterImpl (private val view: UserDetailView) : UserDetailPresenter {
    override fun loadUI() {
        view.initiateUI()
    }

    override fun getUserDetail(mContext: Context, username: String){
        if (Helpers.isNetworkAvailable(mContext)) {
            view.showProgress()
            Helpers.getWebService().getSingleUser(username).enqueue(object : Callback<Users> {
                override fun onResponse(call: Call<Users>, response: Response<Users>) {
                    try {
                        view.hideProgress()
                        if (response.isSuccessful) {
                            view.loadData(response.body()!!)

                        } else {

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun onFailure(call: Call<Users>, t: Throwable) {
                    try{
                        view.hideProgress()
                        view.showMessageDialog(t.message!!)
                    }catch (e: Exception){
                        e.printStackTrace()
                    }

                }
            })

        } else {

            view.showDialogNetworkAvailable()
        }
    }
}