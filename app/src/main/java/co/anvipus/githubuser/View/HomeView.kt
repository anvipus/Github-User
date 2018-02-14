package co.anvipus.githubuser.View

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by Anvipus on 13/02/18.
 */
interface HomeView {
    fun initiateUI()

    fun setListener()

    fun setFragment(_class: Class<*>)

    fun setFragment(_class: Class<*>,bundle: Bundle)

    fun replaceFragment2(layoutId: Int, fragment: Fragment, classname: String)

    fun replaceFragment(layoutId: Int, fragment: Fragment, classname: String)

    fun replaceFragment(layoutId: Int, fragment: Fragment, enterAnim: Int, exitAnim: Int)

    fun replaceFragment(layoutId: Int, FragmentClass: Class<*>)

    fun replaceFragment(layoutId: Int, FragmentClass: Class<*>, _bundle: Bundle)

    fun replaceFragment(layoutId: Int, FragmentClass: Class<*>, enterAnim: Int, exitAnim: Int)

    fun getActiveFragment(layoutId: Int): Fragment
}