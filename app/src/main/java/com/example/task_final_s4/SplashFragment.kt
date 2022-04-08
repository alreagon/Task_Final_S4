package com.example.task_final_s4

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

//tested
class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity()?.let { requireActivity().getWindow().setStatusBarColor(it.getColor(R.color.pinkSplash)) }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            //check if data user is available
            val getShared = requireContext().getSharedPreferences("DATAUSER", Context.MODE_PRIVATE)
            if(getShared.contains("EMAIL") && getShared.contains("EMAIL") &&
                getShared.contains("PASSWORD") && getShared.contains("KONFIRMASIPASSWORD")){
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment)
            }else{
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }, 2000)
    }
}