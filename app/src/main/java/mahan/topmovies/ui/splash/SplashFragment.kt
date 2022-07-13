package mahan.topmovies.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import mahan.topmovies.R
import mahan.topmovies.databinding.FragmentSplashBinding
import mahan.topmovies.utils.StoreUserData
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    // Binding object
    private lateinit var binding: FragmentSplashBinding

    @Inject
    lateinit var storeUserData: StoreUserData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Apply delay for showing splash content
        lifecycle.coroutineScope.launchWhenCreated {
            delay(2000)
            storeUserData.getUserToken().collect{
                if (it.isEmpty()) {
                    // User has not registered yet
                    findNavController().navigate(R.id.actionSplashToRegister)
                } else {
                    // User has registered before
                    findNavController().navigate(R.id.actionToHome)
                }
            }
        }
    }
}