package com.example.myapplication1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.myapplication1.R
import com.example.myapplication1.fragment.CustomWidgetsFragment
import com.example.myapplication1.fragment.EightFragment
import com.example.myapplication1.fragment.ElevenFragment
import com.example.myapplication1.fragment.FiveFragment
import com.example.myapplication1.fragment.FourFragment
import com.example.myapplication1.fragment.MainFragment
import com.example.myapplication1.fragment.NinethFragment
import com.example.myapplication1.fragment.SevenFragment
import com.example.myapplication1.fragment.SixFragment
import com.example.myapplication1.fragment.ThirdFragment
import com.example.myapplication1.fragment.TwoFragment
import com.google.android.material.navigation.NavigationView

class NewActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        drawerLayout = findViewById(R.id.my_drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.dataApi -> {
                    val fragment1 = MainFragment()
                    loadFragment(fragment1)
                }

                R.id.dataRecycler -> {
                    val fragment2 = TwoFragment()
                    loadFragment(fragment2)
                }

                R.id.selectImg -> {
                    val fragment3 = ThirdFragment()
                    loadFragment(fragment3)
                }

                R.id.listData -> {
                    val fragment4 = FourFragment()
                    loadFragment(fragment4)
                }

                R.id.sharedPrf -> {
                    val fragment5 = FiveFragment()
                    loadFragment(fragment5)
                }

                R.id.roomDb -> {
                    val fragment6 = SixFragment()
                    loadFragment(fragment6)
                }

                R.id.fragData -> {
                    val fragment7 = SevenFragment()
                    loadFragment(fragment7)
                }

                R.id.location -> {
                    val fragment8 = EightFragment()
                    loadFragment(fragment8)
                }

                R.id.googleLogin -> {
                    val fragment9 = NinethFragment()
                    loadFragment(fragment9)
                }

                R.id.convertJson -> {
                    val fragment10 = ElevenFragment()
                    loadFragment(fragment10)
                }

                R.id.customWidget -> {
                    val customWidget = CustomWidgetsFragment()
                    loadFragment(customWidget)
                }
            }

            drawerLayout.closeDrawers()
            true
        }

        val initialFragment = MainFragment()
        loadFragment(initialFragment)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, fragment)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}