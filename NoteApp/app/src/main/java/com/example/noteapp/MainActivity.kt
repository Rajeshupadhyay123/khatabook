package com.example.noteapp

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import coil.load
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.viewmodel.ProfileViewModel
import java.security.AccessController.getContext


class MainActivity : AppCompatActivity(), MyDrawerController {
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var drawer: DrawerLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var mView:View

    lateinit var viewModel:ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mView= window.decorView.findViewById(android.R.id.content)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        drawer = binding.drawer

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.listFragment, R.id.transactionFragment),
            drawer
        )
        toolbar=binding.toolbar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(
            navController,
            appBarConfiguration
        )

        binding.bottomNav.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel=ViewModelProvider(this).get(ProfileViewModel::class.java)

       val navView=binding.navView.getHeaderView(0)
        val name=navView.findViewById<TextView>(R.id.profile_name)
        val email=navView.findViewById<TextView>(R.id.profile_email)
        val image=navView.findViewById<ImageView>(R.id.profile_image)

        image.setOnClickListener {
            Toast.makeText(baseContext, "image click", Toast.LENGTH_SHORT).show()
        }

        viewModel.profileTable.observe(this, Observer {
            it?.let {
                name.setText(it.name)
                email.setText(it.email)
                image.load(it.profilePhoto)
            }
        })

    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun setDrawer_Locker() {
        // code to locked drawer
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.setNavigationIcon(null)
    }

    override fun setDrawer_UnLocked() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }
}
