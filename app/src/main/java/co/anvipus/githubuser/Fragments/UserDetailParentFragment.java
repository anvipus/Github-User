package co.anvipus.githubuser.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import co.anvipus.githubuser.Activity.HomeActivity;
import co.anvipus.githubuser.Adapter.CustomPagerAdapter;
import co.anvipus.githubuser.Presenter.UserDetailParentPresenter;
import co.anvipus.githubuser.Presenter.UserDetailParentPresenterImpl;
import co.anvipus.githubuser.R;
import co.anvipus.githubuser.View.UserDetailParentView;

/**
 * Created by Anvipus on 13/02/18.
 */

public class UserDetailParentFragment extends Fragment implements UserDetailParentView {
    private View view;
    private UserDetailParentPresenter presenter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CustomPagerAdapter adapter;
    private Fragment activeFragment;

    public UserDetailParentFragment() {
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
        view = inflater.inflate(R.layout.fragment_user_detail_parent, container, false);
        presenter = new  UserDetailParentPresenterImpl(this);
        presenter.loadUI();
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                ((HomeActivity)getActivity()).setFragment(ListUserFragment.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void initiateUI(){
        ((HomeActivity)getActivity()).toolbar.bringToFront();
        ((HomeActivity)getActivity()).searchView.setVisibility(View.GONE);
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("User Detail Github");
        setTabLayout();
        setViewPager();
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    ((HomeActivity)getActivity()).setFragment(ListUserFragment.class);
                    return true;
                }
                return false;
            }
        });
    }

    public void setTabLayout(){
        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("User Detail"));
        tabLayout.addTab(tabLayout.newTab().setText("User Repo"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.yellow2));
    }

    public void setViewPager(){

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        adapter = new CustomPagerAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        activeFragment = adapter.getCurrentFragment();
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
