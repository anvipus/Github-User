package co.anvipus.githubuser.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import java.util.concurrent.ConcurrentHashMap;

import co.anvipus.githubuser.Fragments.ListUserFragment;
import co.anvipus.githubuser.Fragments.UserDetailParentFragment;
import co.anvipus.githubuser.Model.Users;
import co.anvipus.githubuser.Presenter.HomePresenter;
import co.anvipus.githubuser.Presenter.HomePresenterImpl;
import co.anvipus.githubuser.R;
import co.anvipus.githubuser.View.HomeView;

/**
 * Created by Anvipus on 13/02/18.
 */

public class HomeActivity extends AppCompatActivity implements HomeView {
    private HomePresenter presenter;
    public Toolbar toolbar;
    private ConcurrentHashMap<Integer, Fragment> mActiveFragments;
    public SearchView searchView;
    public Users selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter = new HomePresenterImpl(this);
        presenter.loadUI();
    }

    @Override
    public void initiateUI(){
        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        this.mActiveFragments = new ConcurrentHashMap();
        setFragment(ListUserFragment.class);
    }

    @Override
    public void setListener(){

    }

    @Override
    public void setFragment(Class _class) {
        replaceFragment(R.id.main_fragment_container, _class);
    }

    @Override
    public void setFragment(Class _class,Bundle bundle) {
        replaceFragment(R.id.main_fragment_container, _class, bundle);
    }

    @Override
    public void replaceFragment2(int layoutId, Fragment fragment, String classname) {
        this.mActiveFragments.put(Integer.valueOf(layoutId), fragment);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(layoutId, fragment,classname).commit();
        String tes = classname;
    }

    @Override
    public void replaceFragment(int layoutId, Fragment fragment,String classname) {
        this.mActiveFragments.put(Integer.valueOf(layoutId), fragment);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(layoutId, fragment,classname).commit();
    }


    @Override
    public void replaceFragment(int layoutId, Fragment fragment, int enterAnim, int exitAnim) {
        this.mActiveFragments.put(Integer.valueOf(layoutId), fragment);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(enterAnim, exitAnim).replace(layoutId, fragment).commit();
    }

    @Override
    public void replaceFragment(int layoutId, Class FragmentClass) {
        try {
            Fragment e = (Fragment)FragmentClass.newInstance();
            this.replaceFragment2(layoutId, e,FragmentClass.getName());
        } catch (InstantiationException var4) {
            var4.printStackTrace();
        } catch (IllegalAccessException var5) {
            var5.printStackTrace();
        }

    }

    @Override
    public void replaceFragment(int layoutId, Class FragmentClass,Bundle _bundle) {
        try {
            Fragment e = (Fragment)FragmentClass.newInstance();
            e.setArguments(_bundle);
            this.replaceFragment(layoutId, e,FragmentClass.getName());
        } catch (InstantiationException var4) {
            var4.printStackTrace();
        } catch (IllegalAccessException var5) {
            var5.printStackTrace();
        }

    }

    @Override
    public void replaceFragment(int layoutId, Class FragmentClass, int enterAnim, int exitAnim) {
        try {
            Fragment e = (Fragment)FragmentClass.newInstance();
            this.replaceFragment(layoutId, e, enterAnim, exitAnim);
        } catch (InstantiationException var6) {
            var6.printStackTrace();
        } catch (IllegalAccessException var7) {
            var7.printStackTrace();
        }

    }

    @Override
    public Fragment getActiveFragment(int layoutId) {
        return (Fragment)this.mActiveFragments.get(Integer.valueOf(layoutId));
    }

    public void shouldDisplayHomeUp(Boolean status){
        getSupportActionBar().setDisplayHomeAsUpEnabled(status);
        getSupportActionBar().setDisplayShowHomeEnabled(status);
    }
}
