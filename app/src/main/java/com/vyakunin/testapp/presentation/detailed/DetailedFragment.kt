package com.vyakunin.testapp.presentation.detailed

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vyakunin.testapp.R
import com.vyakunin.testapp.ext.inflate
import org.koin.android.ext.android.get

class DetailedFragment : MvpAppCompatFragment(), DetailedView {
    companion object {
        const val TAG = "DetailedFragment"
        const val ARG_SELECTED_POSITION = "selectedPos"
    }

    @InjectPresenter
    lateinit var presenter: DetailedPresenter

    @ProvidePresenter
    fun providePresenter(): DetailedPresenter {
        val selectedPos = arguments?.getInt(ARG_SELECTED_POSITION, 0) ?: 0
        return get(parameters = { mapOf(ARG_SELECTED_POSITION to selectedPos) })
    }

    private lateinit var viewPager: ViewPager

    private val detailedAdapter by lazy { DetailedAdapter(activity!!.supportFragmentManager) }

    private val pageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            presenter.onPageSelected(position)
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
        override fun onPageScrollStateChanged(state: Int) = Unit
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            container?.inflate(R.layout.fragment_detailed)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.items_view_pager)
        viewPager.apply {
            adapter = detailedAdapter
            addOnPageChangeListener(pageChangeListener)
        }
    }

    override fun showMovieIds(movieIds: List<Long>, selectedPos: Int) {
        detailedAdapter.items = movieIds
        detailedAdapter.notifyDataSetChanged()
        viewPager.setCurrentItem(selectedPos, false)
    }
}