package mahan.topmovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import mahan.topmovies.R
import mahan.topmovies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Binding Object
    private lateinit var binding: ActivityMainBinding

    // NavController
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            navController = findNavController(R.id.navHost)
            bottomNav.setupWithNavController(navController)

            // with this code we can determine in which fragment bottomNavigation should be visible
            navController.addOnDestinationChangedListener{ _, destination, _ ->
                if (destination.id == R.id.splashFragment) {
                    bottomNav.visibility = View.GONE
                } else {
                    bottomNav.visibility = View.VISIBLE
                }
            }
        }
    }

    // Handling Up Button with NavController
    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }
}