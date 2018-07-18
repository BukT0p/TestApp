package com.vyakunin.testapp.presentation.details

import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.squareup.picasso.Picasso
import com.vyakunin.testapp.R
import com.vyakunin.testapp.ext.inflate
import com.vyakunin.testapp.ext.toast
import org.koin.android.ext.android.get

class DetailsFragment : MvpAppCompatFragment(), DetailsView {
    companion object {
        const val TAG = "DetailsFragment"
        const val ARG_ID = "id"
    }

    @InjectPresenter
    lateinit var presenter: DetailsPresenter

    @ProvidePresenter
    fun providePresenter(): DetailsPresenter {
        val movieId: Long = arguments?.getLong(ARG_ID, 0L) ?: 0L
        return get(parameters = { mapOf(ARG_ID to movieId) })
    }

    private lateinit var posterView: ImageView
    private lateinit var titleView: TextView
    private lateinit var infoView: TextView
    private lateinit var descriptionView: TextView
    private var statusMessage: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            container?.inflate(R.layout.fragment_details)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        posterView = view.findViewById(R.id.poster_image)
        titleView = view.findViewById(R.id.title)
        infoView = view.findViewById(R.id.additional_info)
        view.findViewById<Button>(R.id.buy_tickets).setOnClickListener { presenter.onBookClicked() }
        descriptionView = view.findViewById(R.id.description)
    }

    override fun showHeader(rank: Int, posterUrl: String?, name: String) {
        activity?.title = "Ranked #$rank"
        titleView.text = name
        if (posterUrl != null) Picasso.get().load(posterUrl).into(posterView) else posterView.visibility = View.GONE
    }

    override fun showDetails(description: String?, duration: String?, director: String?, actors: String?, genres: String?) {
        descriptionView.visibility = if (description == null) View.GONE else View.VISIBLE
        descriptionView.text = description

        infoView.visibility = if (duration == null) View.GONE else View.VISIBLE
        infoView.text = buildRichString(duration, director, actors, genres)
    }


    override fun showProgress(show: Boolean) {
        if (show) {
            statusMessage = Snackbar.make(view!!, "Details Fetching in Progress", Snackbar.LENGTH_INDEFINITE)
            statusMessage?.show()
        } else {
            statusMessage?.dismiss()
        }
    }

    override fun navigateToTicketsBooking() {
        toast("Booking is not implemented")
    }

    override fun showNetworkError() {
        toast(R.string.network_unavailable)
    }

    private fun buildRichString(duration: String?, director: String?, actors: String?, genres: String?): CharSequence? {
        val ssb = SpannableStringBuilder()
        addBoldTitleAndInfo("Duration", duration, ssb)
        addBoldTitleAndInfo("Director", director, ssb)
        addBoldTitleAndInfo("Actors", actors, ssb)
        addBoldTitleAndInfo("Genres", genres, ssb)
        return ssb
    }

    private fun addBoldTitleAndInfo(title: String, info: String?, ssb: SpannableStringBuilder) {
        if (info == null) return
        val start = ssb.length
        ssb.append(title).append(": ").append(info).append("\n")
        ssb.setSpan(StyleSpan(Typeface.BOLD), start, start + title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}