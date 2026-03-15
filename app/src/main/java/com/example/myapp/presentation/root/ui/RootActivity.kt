package com.example.myapp.presentation.root.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapp.R
import com.example.myapp.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding

    private val navController: NavController by lazy {
        Log.d("RootActivity", "🔍 Lazy init navController")
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("RootActivity", "🔍 onCreate start")

        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Проверка Toolbar
        Log.d("RootActivity", "🔍 Toolbar found: ${binding.toolbar != null}")

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        // Проверка supportActionBar
        Log.d("RootActivity", "🔍 supportActionBar is null? ${supportActionBar == null}")

        // Проверка NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        Log.d("RootActivity", "🔍 NavHostFragment found: ${navHostFragment != null}")
        Log.d("RootActivity", "🔍 NavHostFragment type: ${navHostFragment?.javaClass?.simpleName}")

        appBarConfiguration = AppBarConfiguration.Builder(
            setOf(R.id.mainFragment)
        ).build()

        Log.d("RootActivity", "🔍 Before setupActionBarWithNavController")

        setupActionBarWithNavController(navController, appBarConfiguration)

        Log.d("RootActivity", "🔍 After setupActionBarWithNavController")

        // Добавляем слушатель смены фрагментов
        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.d("RootActivity", "🔍 Destination changed to: ${destination.label} (id: ${destination.id})")
            Log.d("RootActivity", "🔍 Is top level? ${appBarConfiguration.topLevelDestinations.contains(destination.id)}")
            Log.d("RootActivity", "🔍 Should show up? ${!appBarConfiguration.topLevelDestinations.contains(destination.id)}")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d("RootActivity", "🔍 onSupportNavigateUp called")

        val result = navController.navigateUp(appBarConfiguration)
        Log.d("RootActivity", "🔍 navigateUp result: $result")

        return result || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        Log.d("RootActivity", "🔍 onOptionsItemSelected: ${item.itemId}")

        if (item.itemId == android.R.id.home) {
            Log.d("RootActivity", "🔍 Home button clicked!")
            onSupportNavigateUp()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}