package com.lovejiaming.timemovieinkotlin.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.lovejiaming.timemovieinkotlin.R
import com.lovejiaming.timemovieinkotlin.chAllDisplayImage
import com.lovejiaming.timemovieinkotlin.chAllstartActivity
import com.lovejiaming.timemovieinkotlin.networkbusiness.NetWorkRealCallMtime
import com.lovejiaming.timemovieinkotlin.networkbusiness.WeeklyMostFocusItem
import com.lovejiaming.timemovieinkotlin.views.activity.MovieDetailActivity
import com.lovejiaming.timemovieinkotlin.views.ownerdraw.MtimeViewPagerTransform
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_rank_list.*

/**
 * A simple [Fragment] subclass.
 * Use the [RankListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RankListFragment : Fragment() {
    //
    lateinit var m_listWeeklyFocus: MutableList<WeeklyMostFocusItem>
    lateinit var m_listWeeklyExpect: MutableList<WeeklyMostFocusItem>
    lateinit var m_listChHighestRating: MutableList<WeeklyMostFocusItem>
    //
    val m_listWeeklyFocusCover: MutableList<ImageView> by lazy {
        MutableList(m_listWeeklyFocus.size, { ImageView(activity) })
    }
    val m_listWeeklyExpectCover: MutableList <ImageView> by lazy {
        MutableList(m_listWeeklyExpect.size, { ImageView(activity) })
    }
    val m_listChHighestRatingCover: MutableList<ImageView> by lazy {
        MutableList(10, { ImageView(activity) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_rank_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestWeeklyFocus()
        requestWeeklyMostExpect()
        requestChHighestRating()
    }

    fun requestChHighestRating() {
        NetWorkRealCallMtime.newInstance().getRankListService()
                .requestTopList(2066)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    title_highestrating_ch.text = it.topList.summary
                    m_listChHighestRating = it.movies
                    initChHighestRatingViews()
                }, { t -> Log.i("neterror", t.toString()) })
    }

    fun initChHighestRatingViews() {
        m_listChHighestRatingCover.forEachIndexed { index, view ->
            view.chAllDisplayImage(activity, m_listChHighestRating[index].posterUrl)
            view.setOnClickListener {
                activity.chAllstartActivity<MovieDetailActivity>(mapOf("movieid" to m_listChHighestRating[index].id!!.toString(), "moviename" to m_listChHighestRating[index].name!!))
            }
        }
        //
        viewpager_highestrating_ch.apply {
            pageMargin = 5
            offscreenPageLimit = 10
            setPageTransformer(false, MtimeViewPagerTransform())
        }
        highestrating_container_ch.setOnTouchListener { view, motionEvent ->
            viewpager_highestrating_ch.dispatchTouchEvent(motionEvent)
        }
        //
        viewpager_highestrating_ch.adapter = object : PagerAdapter() {
            override fun isViewFromObject(view: View?, view1: Any?): Boolean = view == view1

            override fun getCount(): Int = 10

            override fun instantiateItem(container: ViewGroup?, position: Int): Any {
                container?.addView(m_listChHighestRatingCover[position])
                return m_listChHighestRatingCover[position]
            }

            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
                container?.removeView(m_listChHighestRatingCover[position])
            }
        }
    }

    fun requestWeeklyMostExpect() {
        NetWorkRealCallMtime.newInstance().getRankListService()
                .requestTopList(2069)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    title_weeklyexpect.text = it.topList.summary
                    m_listWeeklyExpect = it.movies
                    initWeeklyExpectViews()
                }, { t -> Log.i("neterror", t.toString()) })
    }

    fun initWeeklyExpectViews() {
        m_listWeeklyExpectCover.forEachIndexed { index, imageView ->
            imageView.chAllDisplayImage(activity, m_listWeeklyExpect[index].posterUrl)
            imageView.setOnClickListener {
                activity.chAllstartActivity<MovieDetailActivity>(mapOf("movieid" to m_listWeeklyExpect[index].id!!.toString(), "moviename" to m_listWeeklyExpect[index].name!!))
            }
        }
        //
        viewpager_weeklyexpect.adapter = object : PagerAdapter() {
            override fun isViewFromObject(view: View?, view1: Any?): Boolean = view == view1
            override fun getCount(): Int = m_listWeeklyExpect.size
            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
                container?.removeView(m_listWeeklyExpectCover[position])
            }

            override fun instantiateItem(container: ViewGroup?, position: Int): Any {
                container?.addView(m_listWeeklyExpectCover[position])
                return m_listWeeklyExpectCover[position]
            }
        }
        viewpager_weeklyexpect.apply {
            offscreenPageLimit = m_listWeeklyExpectCover.size
            pageMargin = 5
            setPageTransformer(false, MtimeViewPagerTransform())
        }
        weeklyexpect_container.setOnTouchListener { _, motionEvent ->
            viewpager_weeklyexpect.dispatchTouchEvent(motionEvent)
        }
    }

    fun requestWeeklyFocus() {
        NetWorkRealCallMtime.newInstance().getRankListService()
                .requestTopList(2067)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    m_listWeeklyFocus = it.movies
                    title_weeklyfocus.text = it.topList.summary
                    initWeeklyFocusViews()
                }, { t -> Log.i("neterror", t.toString()) })
    }

    fun initWeeklyFocusViews() {
        m_listWeeklyFocusCover.forEachIndexed { index, view ->
            view.chAllDisplayImage(activity, m_listWeeklyFocus[index].posterUrl)
            view.setOnClickListener {
                activity.chAllstartActivity<MovieDetailActivity>(mapOf("movieid" to m_listWeeklyExpect[index].id!!.toString(), "moviename" to m_listWeeklyFocus[index].name!!))
            }
        }
        //
        viewpager_weeklyfocus.adapter = object : PagerAdapter() {
            override fun isViewFromObject(view1: View?, view2: Any?) = view1 == view2

            override fun getCount(): Int = m_listWeeklyFocus.size

            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
                container?.removeView(m_listWeeklyFocusCover[position])
            }

            override fun instantiateItem(container: ViewGroup?, position: Int): Any {
                container?.addView(m_listWeeklyFocusCover[position])
                return m_listWeeklyFocusCover[position]
            }
        }
        //
        viewpager_weeklyfocus.apply {
            offscreenPageLimit = m_listWeeklyFocus.size
            pageMargin = 5
            setPageTransformer(false, MtimeViewPagerTransform())
        }
        weeklyfocus_container.setOnTouchListener { _, motionEvent ->
            viewpager_weeklyfocus.dispatchTouchEvent(motionEvent)
        }
    }

    companion object {
        fun newInstance(): RankListFragment = RankListFragment()
    }
}
