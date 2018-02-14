package co.anvipus.githubuser.Adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import co.anvipus.githubuser.Model.Users
import co.anvipus.githubuser.Utility.CustomItemClickListener
import co.anvipus.githubuser.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by Anvipus on 13/02/18.
 */
class UsersListAdapter (private val itemList: MutableList<Users>, private val mContext: Context, private var listener: CustomItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private val selectedItems: Users? = null
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
                    R.layout.users_list_item, parent, false)
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
            holder.textView1.text = singleData.login
            Picasso.with(mContext)
                    .load(singleData.avatar_url)
                    .resize(200, 200)
                    .centerCrop()
                    .into(holder.ivProfile)
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
        var ivProfile: CircleImageView
        var items: Users? = null

        init {
            textView1 = v.findViewById(R.id.tvText1)
            ivProfile = v.findViewById(R.id.ivProfile)
        }
    }

    class LoadingViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var progressBar: ProgressBar

        init {
            progressBar = v.findViewById<ProgressBar>(R.id.progressBar1)
        }
    }
}



