package mahan.topmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mahan.topmovies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Binding Object
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}