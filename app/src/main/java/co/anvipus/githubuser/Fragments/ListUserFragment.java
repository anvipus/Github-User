package co.anvipus.githubuser.Fragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.google.gson.Gson;
import com.scottyab.aescrypt.AESCrypt;

import java.util.ArrayList;
import java.util.List;

import co.anvipus.githubuser.Activity.HomeActivity;
import co.anvipus.githubuser.Adapter.UsersListAdapter;
import co.anvipus.githubuser.Model.Users;
import co.anvipus.githubuser.Presenter.ListUserPresenter;
import co.anvipus.githubuser.Presenter.ListUserPresenterImpl;
import co.anvipus.githubuser.R;
import co.anvipus.githubuser.Utility.CustomItemClickListener;
import co.anvipus.githubuser.Utility.Helpers;
import co.anvipus.githubuser.Utility.Network.GetListUsersResponse;
import co.anvipus.githubuser.View.ListUserView;

/**
 * Created by Anvipus on 13/02/18.
 */

public class ListUserFragment extends Fragment implements ListUserView {
    private View view;
    private ListUserPresenter presenter;
    private RecyclerView recyclerView;
    private LinearLayout layoutContent,layoutNoData,layoutLoading;
    private LinearLayoutManager linearLayoutManager;
    private UsersListAdapter listAdapter;
    private ProgressDialog mProgressDialog;
    private String _query="";
    private SharedPreferences preferences;
    private SwipeRefreshLayout swipeLayout;

    public ListUserFragment() {
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
        view = inflater.inflate(R.layout.fragment_list_user, container, false);
        presenter = new ListUserPresenterImpl(this);
        presenter.loadUI();
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        ((HomeActivity)getActivity()).searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        ((HomeActivity)getActivity()).searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        ((HomeActivity)getActivity()).searchView.setIconifiedByDefault(true);
        ((HomeActivity)getActivity()).searchView.setFocusable(false);
        ((HomeActivity)getActivity()).searchView.setIconified(false);
        ((HomeActivity)getActivity()).searchView.clearFocus();
        ((HomeActivity)getActivity()).searchView.onActionViewCollapsed();
        //searchView.requestFocusFromTouch();
        ((HomeActivity)getActivity()).searchView.setOnQueryTextListener(searchQueryListener);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private SearchView.OnQueryTextListener searchQueryListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            _query = query;
            mProgressDialog.setTitle("Searching");
            mProgressDialog.setMessage("Please Wait");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
            presenter.getUserListByUsername(getActivity(),false,_query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {

            return true;
        }

    };

    @Override
    public void initiateUI(){
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("List User Github");
        preferences = getActivity().getSharedPreferences("anvipus", 0);
        layoutContent = view.findViewById(R.id.layoutContent);
        layoutNoData = view.findViewById(R.id.layoutNoData);
        layoutLoading = view.findViewById(R.id.layoutLoading);
        recyclerView = view.findViewById(R.id.rvContent);
        swipeLayout = view.findViewById(R.id.swipe_refresh_layout);
        mProgressDialog = new ProgressDialog(getActivity());
        String json;
        Gson gson = new Gson();
        json = Helpers.INSTANCE.getStringPreferences(getActivity(),preferences.getString("listUser", null));
        if(json!=null){
            GetListUsersResponse response = gson.fromJson(json, GetListUsersResponse.class);
            loadData(response);
        }else{
            presenter.getUserList(getActivity(),true);
        }
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getUserList(getActivity(),false);
            }
        });
        swipeLayout.setColorSchemeResources( R.color.medium_blue,
                R.color.yellow,
                R.color.medium_blue,
                R.color.yellow);

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
    public void setRefreshing(Boolean param){
        swipeLayout.setRefreshing(param);
    }

    @Override
    public void loadData(final List<Users> listUsers){

        try{
            if(listUsers.size()>0){
                mProgressDialog.dismiss();
                layoutLoading.setVisibility(View.GONE);
                layoutNoData.setVisibility(View.GONE);
                layoutContent.setVisibility(View.VISIBLE);
                recyclerView.setHasFixedSize(true);
                linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
                listAdapter = new UsersListAdapter(listUsers,getActivity(), new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        ((HomeActivity)getActivity()).selectedUser = listUsers.get(position);
                        ((HomeActivity)getActivity()).setFragment(UserDetailParentFragment.class);
                    }
                });
                recyclerView.setAdapter(listAdapter);
                listAdapter.setDisplayCount(10);
            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadData(final GetListUsersResponse getListUsersResponse){

        try{
            Gson gson = new Gson();
            String json = gson.toJson(getListUsersResponse);
            preferences.edit().putString("listUser", AESCrypt.encrypt("password", json)).apply();
            if(getListUsersResponse.getItems().size()>0){
                mProgressDialog.dismiss();
                layoutLoading.setVisibility(View.GONE);
                layoutNoData.setVisibility(View.GONE);
                layoutContent.setVisibility(View.VISIBLE);
                recyclerView.setHasFixedSize(true);
                linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
                listAdapter = new UsersListAdapter(getListUsersResponse.getItems(),getActivity(), new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        ((HomeActivity)getActivity()).selectedUser = getListUsersResponse.getItems().get(position);
                        ((HomeActivity)getActivity()).setFragment(UserDetailParentFragment.class);
                    }
                });
                recyclerView.setAdapter(listAdapter);
                listAdapter.setDisplayCount(10);
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
