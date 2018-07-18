package com.vyakunin.testapp.presentation.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vyakunin.testapp.R
import com.vyakunin.testapp.activity.MainRouter
import com.vyakunin.testapp.data.MovieEntity
import com.vyakunin.testapp.ext.inflate
import org.koin.android.ext.android.get

class MainFragment : MvpAppCompatFragment(), MainView {
    companion object {
        const val TAG = "MainFragment"
    }

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = get()

    private val router by lazy { activity as MainRouter }

    private val mainAdapter = MainAdapter {
        presenter.onItemClicked(it)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            container?.inflate(R.layout.fragment_main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.col_count))
            itemAnimator = DefaultItemAnimator()
            adapter = mainAdapter
        }
        activity?.title = "Top 10 Movies"
    }

    override fun navigateToMovieDetails(id: Long) {
        router.navigateToDetails(id)
    }

    override fun showData(items: List<MovieEntity>) {
        mainAdapter.data = items
    }
}