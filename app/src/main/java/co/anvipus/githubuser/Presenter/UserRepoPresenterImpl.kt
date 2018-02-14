package co.anvipus.githubuser.Presenter

import android.content.Context
import co.anvipus.githubuser.Model.Repo
import co.anvipus.githubuser.Model.Users
import co.anvipus.githubuser.Utility.Helpers
import co.anvipus.githubuser.Utility.Network.GetListUsersResponse
import co.anvipus.githubuser.View.ListUserView
import co.anvipus.githubuser.View.UserRepoView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Anvipus on 14/02/18.
 */
class UserRepoPresenterImpl (private val view: UserRepoView) : UserRepoPresenter {
    override fun loadUI() {
        view.initiateUI()
    }

    override fun getUserRepo(mContext: Context, username: String){
        if (Helpers.isNetworkAvailable(mContext)) {
            view.showProgress()
            Helpers.getWebService().getUserRepo(username).enqueue(object : Callback<List<Repo>> {
                override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                    try {
                        view.hideProgress()
                        if (response.isSuccessful) {
                            view.loadData(response!!.body()!!)

                        } else {

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
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