package com.vyakunin.testapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vyakunin.testapp.R
import com.vyakunin.testapp.ext.withArguments
import com.vyakunin.testapp.presentation.detailed.DetailedFragment
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

    override fun navigateToDetails(pos: Int) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailedFragment().withArguments(DetailedFragment.ARG_SELECTED_POSITION to pos), DetailedFragment.TAG)
                .addToBackStack(DetailedFragment.TAG)
                .commit()
    }
}
