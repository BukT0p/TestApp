package com.vyakunin.testapp.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.vyakunin.testapp.R
import com.vyakunin.testapp.ext.inflate

class DetailsFragment : MvpAppCompatFragment() {
    companion object {
        const val TAG = "DetailsFragment"
        const val ARG_ID = "id"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?=
            container?.inflate(R.layout.fragment_details)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.containsKey(ARG_ID) == true){
            activity?.title = "Details for ${arguments!!.getLong(ARG_ID)}"
        }
    }
}