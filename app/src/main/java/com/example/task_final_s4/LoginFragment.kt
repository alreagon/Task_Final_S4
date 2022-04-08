package com.example.task_final_s4

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.task_final_s4.databinding.FragmentLoginBinding


//tested
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity()?.let { requireActivity().getWindow().setStatusBarColor(it.getColor(R.color.pinkSplash)) }
        }
        //action for registering new account
        binding.loginBelumPunyaAkun.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }
        //action for login authorization
        binding.loginButtonLogin.setOnClickListener {
            if(loginAuth()){
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    //Authorization function for login action
    private fun loginAuth() : Boolean {
        if(binding.loginInputEmail.text!!.isNotEmpty() && binding.loginInputPassword.text!!.isNotEmpty()){
            //get data email dan password in shared preference
            val sharedPreferences = requireContext().getSharedPreferences("DATAUSER", Context.MODE_PRIVATE)
            val dataEmail = sharedPreferences.getString("EMAIL", "")
            val dataPassword = sharedPreferences.getString("PASSWORD", "")

            //get input data user by user
            val inputanEmail = binding.loginInputEmail.text.toString()
            val inputanPassword = binding.loginInputPassword.text.toString()

            //checking
            return if(inputanEmail != dataEmail || inputanPassword != dataPassword){
                Toast.makeText(requireContext(), "email/password salah", Toast.LENGTH_SHORT).show()
                false
            }else{
                Toast.makeText(requireContext(), "Selamat datang!", Toast.LENGTH_SHORT).show()
                true
            }

        }else{
            Toast.makeText(requireContext(), "email dan password harus diisi", Toast.LENGTH_SHORT).show()
            return false
        }
    }
}