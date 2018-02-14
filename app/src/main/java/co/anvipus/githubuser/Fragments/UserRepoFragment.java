package co.anvipus.githubuser.Fragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scottyab.aescrypt.AESCrypt;
import com.squareup.picasso.Picasso;

import java.util.List;

import co.anvipus.githubuser.Activity.HomeActivity;
import co.anvipus.githubuser.Adapter.RepoListAdapter;
import co.anvipus.githubuser.Adapter.UsersListAdapter;
import co.anvipus.githubuser.Model.Repo;
import co.anvipus.githubuser.Model.Users;
import co.anvipus.githubuser.Presenter.ListUserPresenter;
import co.anvipus.githubuser.Presenter.ListUserPresenterImpl;
import co.anvipus.githubuser.Presenter.UserDetailPresenter;
import co.anvipus.githubuser.Presenter.UserDetailPresenterImpl;
import co.anvipus.githubuser.Presenter.UserRepoPresenter;
import co.anvipus.githubuser.Presenter.UserRepoPresenterImpl;
import co.anvipus.githubuser.R;
import co.anvipus.githubuser.Utility.CustomItemClickListener;
import co.anvipus.githubuser.Utility.Helpers;
import co.anvipus.githubuser.Utility.Network.GetListUsersResponse;
import co.anvipus.githubuser.View.ListUserView;
import co.anvipus.githubuser.View.UserRepoView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Anvipus on 13/02/18.
 */

public class UserRepoFragment extends Fragment implements UserRepoView {
    private View view;
    private UserRepoPresenter presenter;
    private RecyclerView recyclerView;
    private LinearLayout layoutContent,layoutNoData,layoutLoading;
    private LinearLayoutManager linearLayoutManager;
    private RepoListAdapter listAdapter;
    private ProgressDialog mProgressDialog;

    public UserRepoFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_repo, container, false);
        presenter = new UserRepoPresenterImpl(this);
        presenter.loadUI();
        return view;
    }

    @Override
    public void initiateUI(){
        layoutContent = view.findViewById(R.id.layoutContent);
        layoutNoData = view.findViewById(R.id.layoutNoData);
        layoutLoading = view.findViewById(R.id.layoutLoading);
        recyclerView = view.findViewById(R.id.rvContent);
        mProgressDialog = new ProgressDialog(getActivity());
        presenter.getUserRepo(getActivity(),((HomeActivity)getActivity()).selectedUser.getLogin());

    }

    @Override
    public void showProgress() {
        layoutLoading.setVisibility(View.VISIBLE);
        layoutNoData.setVisibility(View.GONE);
        layoutContent.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        layoutLoading.setVisibility(View.GONE);
        layoutNoData.setVisibility(View.GONE);
        layoutContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadData(final List<Repo> listRepo){

        try{
            if(listRepo.size()>0){
                mProgressDialog.dismiss();
                layoutLoading.setVisibility(View.GONE);
                layoutNoData.setVisibility(View.GONE);
                layoutContent.setVisibility(View.VISIBLE);
                recyclerView.setHasFixedSize(true);
                linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
                listAdapter = new RepoListAdapter(listRepo,getActivity(), new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                    }
                });
                recyclerView.setAdapter(listAdapter);
                listAdapter.setDisplayCount(5);
            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showDialogNetworkAvailable() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Network Not Available")
                .setTitle(getString(R.string.app_name))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void showMessageDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setTitle(getString(R.string.app_name))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
