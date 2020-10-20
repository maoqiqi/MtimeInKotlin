package com.lovejiaming.timemovieinkotlin.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.lovejiaming.timemovieinkotlin.R
import com.lovejiaming.timemovieinkotlin.adapter.FindFunnyTrailersAdapter
import com.lovejiaming.timemovieinkotlin.chAllAsyncToMainThread
import com.lovejiaming.timemovieinkotlin.networkbusiness.NetWorkRealCallMtime
import com.lovejiaming.timemovieinkotlin.views.activity.SimpleItemDecorationVer
import kotlinx.android.synthetic.main.fragment_find_funny_trailers.*

class FindFunny_TrailersFragment : Fragment() {

    val mAdapter: FindFunnyTrailersAdapter by lazy {
        FindFunnyTrailersAdapter(this.activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_find_funny_trailers, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_all_trailer.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recycler_all_trailer.addItemDecoration(SimpleItemDecorationVer())
        recycler_all_trailer.adapter = mAdapter
        swipe_refresh_funnytrailer.isRefreshing = true
    }

    //setUserVisibleHint(boolean isVisibleToUser)在Fragment OnCreateView()方法之前调用的
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        recycler_all_trailer?.let {
            recycler_all_trailer.visibility = View.GONE
            recycler_all_trailer.scrollToPosition(0)
            swipe_refresh_funnytrailer.isRefreshing = true
        }

        if (isVisibleToUser) {
            NetWorkRealCallMtime.newInstance().getFindFunnyService()
                    .requestFunnyTrailerList()
                    .chAllAsyncToMainThread()
                    .subscribe {
                        mAdapter.insertAllTrailers(it)
                        recycler_all_trailer.visibility = View.VISIBLE
                        swipe_refresh_funnytrailer.isRefreshing = false
                    }
        } else {
            onStop()
        }
    }

    override fun onStop() {
        super.onStop()
        Glide.get(activity).clearMemory()
    }

    companion object {
        fun newInstance(): FindFunny_TrailersFragment = FindFunny_TrailersFragment()
    }
}
