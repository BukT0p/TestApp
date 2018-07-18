package com.vyakunin.testapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vyakunin.testapp.R
import com.vyakunin.testapp.ext.withArguments
import com.vyakunin.testapp.presentation.details.DetailsFragment
import com.vyakunin.testapp.presentation.main.MainFragment

class MainActivity : AppCompatActivity(), MainRouter {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) navigateToMain()
    }

    override fun navigateToMain() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment(), MainFragment.TAG)
                .commit()
    }

    override fun navigateToDetails(id: Long) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailsFragment().withArguments(DetailsFragment.ARG_ID to id), DetailsFragment.TAG)
                .addToBackStack(DetailsFragment.TAG)
                .commit()
    }
}
