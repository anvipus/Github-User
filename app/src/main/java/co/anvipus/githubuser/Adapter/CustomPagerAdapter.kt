package co.anvipus.githubuser.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import co.anvipus.githubuser.Fragments.UserDetailFragment
import co.anvipus.githubuser.Fragments.UserRepoFragment

/**
 * Created by Anvipus on 13/02/18.
 */
class CustomPagerAdapter (fm: FragmentManager, internal var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {
    var currentFragment: Fragment? = null


    fun getCurrentFragments(): Fragment {
        return currentFragment!!
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        if (currentFragment !== `object`) {
            currentFragment = `object` as Fragment
        }
        super.setPrimaryItem(container, position, `object`)
    }

    override fun getItem(position: Int): Fragment? {

        when (position) {
            0 -> {
                val tab1 = UserDetailFragment()
                return tab1
            }
            1 -> {
                val tab2 = UserRepoFragment()
                return tab2
            }
            else -> return null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }


}

