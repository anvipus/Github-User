package co.anvipus.githubuser.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import co.anvipus.githubuser.Model.Repo
import co.anvipus.githubuser.Model.Users
import co.anvipus.githubuser.R
import co.anvipus.githubuser.Utility.CustomItemClickListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by Anvipus on 14/02/18.
 */
class RepoListAdapter (private val itemList: MutableList<Repo>, private val mContext: Context, private var listener: CustomItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private val selectedItems: Repo? = null
    private var isLoading: Boolean = false
    private val visibleThreshold = 5
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var displaySize = 1

    fun setDisplayCount(numberOfEntries: Int) {
        displaySize = numberOfEntries
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList!![position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder? {

        if (viewType == VIEW_TYPE_ITEM) {
            val v = LayoutInflater.from(parent.context).inflate(
                    R.layout.repo_list_item, parent, false)
            val vh = ItemViewHolder(v, mContext)
            v.setOnClickListener { v -> listener.onItemClick(v, vh.position) }
            return vh
        } else if (viewType == VIEW_TYPE_LOADING) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_loading_item, parent, false)
            return LoadingViewHolder(view)
        }

        return null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val pos = position
            val singleData = itemList[position]
            holder.textView1.text = "Name: "+singleData.name
            holder.textView2.text = "Description: "+singleData.description
            holder.textView3.text = "Brance Url: "+singleData.branches_url
            holder.textView4.text = "Default Branch: "+singleData.default_branch
            holder.items = singleData
        }else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }


    }

    override fun getItemCount(): Int {
        if (displaySize > itemList!!.size)
            return itemList?.size ?: 0
        else
            return displaySize
    }

    fun clear() {
        itemList!!.clear()
        notifyDataSetChanged()
    }

    fun setLoaded() {
        isLoading = false
    }


    class ItemViewHolder(v: View, mContext: Context) : RecyclerView.ViewHolder(v) {
        var textView1: TextView
        var textView2: TextView
        var textView3: TextView
        var textView4: TextView
        var items: Repo? = null

        init {
            textView1 = v.findViewById(R.id.tvText1)
            textView2 = v.findViewById(R.id.tvText2)
            textView3 = v.findViewById(R.id.tvText3)
            textView4 = v.findViewById(R.id.tvText4)
        }
    }

    class LoadingViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var progressBar: ProgressBar

        init {
            progressBar = v.findViewById<ProgressBar>(R.id.progressBar1)
        }
    }
}



