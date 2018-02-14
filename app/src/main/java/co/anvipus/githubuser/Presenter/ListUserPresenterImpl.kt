package co.anvipus.githubuser.Presenter

import android.content.Context
import co.anvipus.githubuser.Model.Users
import co.anvipus.githubuser.Utility.Helpers
import co.anvipus.githubuser.Utility.Network.GetListUsersResponse
import co.anvipus.githubuser.View.ListUserView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Anvipus on 13/02/18.
 */
class ListUserPresenterImpl (private val view: ListUserView) : ListUserPresenter {
    override fun loadUI() {
        view.initiateUI()
    }

    override fun getUserList(mContext: Context,isShowProgress: Boolean){
        if (Helpers.isNetworkAvailable(mContext)) {
            if(isShowProgress){
                view.showProgress()
            }
                Helpers.getWebService().getListUsers().enqueue(object : Callback<List<Users>> {
                    override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                        try {
                            view.hideProgress()
                            view.setRefreshing(false)
                            if (response.isSuccessful) {
                                view.loadData(response.body()!!)

                            } else {

                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }

                    override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                        try{
                            view.hideProgress()
                            view.setRefreshing(false)
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

    override fun getUserListByUsername(mContext: Context,isShowProgress: Boolean,username: String){
        if (Helpers.isNetworkAvailable(mContext)) {
            if(isShowProgress){
                view.showProgress()
            }
            Helpers.getWebService().getListUsersByUsername(username).enqueue(object : Callback<GetListUsersResponse> {
                override fun onResponse(call: Call<GetListUsersResponse>, response: Response<GetListUsersResponse>) {
                    try {
                        view.hideProgress()
                        view.setRefreshing(false)
                        if (response.isSuccessful) {
                            view.loadData(response!!.body()!!)

                        } else {

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun onFailure(call: Call<GetListUsersResponse>, t: Throwable) {
                    try{
                        view.hideProgress()
                        view.setRefreshing(false)
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