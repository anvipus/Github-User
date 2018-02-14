package co.anvipus.githubuser.Fragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.anvipus.githubuser.Activity.HomeActivity;
import co.anvipus.githubuser.Adapter.UsersListAdapter;
import co.anvipus.githubuser.Model.Users;
import co.anvipus.githubuser.Presenter.ListUserPresenterImpl;
import co.anvipus.githubuser.Presenter.UserDetailPresenter;
import co.anvipus.githubuser.Presenter.UserDetailPresenterImpl;
import co.anvipus.githubuser.R;
import co.anvipus.githubuser.Utility.CustomItemClickListener;
import co.anvipus.githubuser.View.UserDetailView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Anvipus on 13/02/18.
 */

public class UserDetailFragment extends Fragment implements UserDetailView {
    private View view;
    private LinearLayout layoutContent,layoutNoData,layoutLoading;
    private TextView tvName,tvLocation,tvEmail,tvBio;
    private CircleImageView ivPhoto;
    private UserDetailPresenter presenter;

    public UserDetailFragment() {
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
        view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        presenter = new UserDetailPresenterImpl(this);
        presenter.loadUI();
        return view;
    }

    @Override
    public void initiateUI(){
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("User Detail Github");
        layoutContent = view.findViewById(R.id.layoutContent);
        layoutNoData = view.findViewById(R.id.layoutNoData);
        layoutLoading = view.findViewById(R.id.layoutLoading);
        tvName = view.findViewById(R.id.tvText2);
        tvLocation = view.findViewById(R.id.tvText4);
        tvEmail = view.findViewById(R.id.tvText6);
        tvBio = view.findViewById(R.id.tvText8);
        ivPhoto = view.findViewById(R.id.ivPhoto);
        presenter.getUserDetail(getActivity(),((HomeActivity)getActivity()).selectedUser.getLogin());
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
    public void loadData(Users user){

        try{
            if(user.getName()!=null){
                tvName.setText(user.getName());
            }
            if(user.getLocation()!=null){
                tvLocation.setText(user.getLocation());
            }
            if(user.getEmail()!=null){
                tvEmail.setText(user.getEmail());
            }
            if(user.getBio()!=null){
                tvBio.setText(user.getBio());
            }
            if(user.getAvatar_url()!=null){
                Picasso.with(getActivity())
                        .load(user.getAvatar_url())
                        .resize(200, 200)
                        .centerCrop()
                        .into(ivPhoto);
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
