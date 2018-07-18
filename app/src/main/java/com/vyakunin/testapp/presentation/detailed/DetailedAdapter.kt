package com.vyakunin.testapp.presentation.detailed

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import com.vyakunin.testapp.ext.withArguments
import com.vyakunin.testapp.presentation.details.DetailsFragment
import kotlin.properties.Delegates

class DetailedAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    var items: List<Long> by Delegates.observable(arrayListOf()) { _, old, new ->
        readingFragments = arrayOfNulls(new.size)
    }

    private var readingFragments: Array<DetailsFragment?> = arrayOfNulls(count)

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Fragment {
        val itemId = items[position]
        val readingFragment = DetailsFragment().withArguments(DetailsFragment.ARG_ID to itemId)
        readingFragments[position] = readingFragment
        return readingFragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        readingFragments[position] = null
        super.destroyItem(container, position, obj)
    }
}