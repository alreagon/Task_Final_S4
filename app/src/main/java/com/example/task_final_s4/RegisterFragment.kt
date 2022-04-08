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
import com.example.task_final_s4.databinding.FragmentRegisterBinding

//tested
class RegisterFragment : Fragment() {
    
    private var _binding :FragmentRegisterBinding?=null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity()?.let { requireActivity().getWindow().setStatusBarColor(it.getColor(R.color.pinkSplash)) }
        }
        //action for button register
        binding.registerButtonDaftar.setOnClickListener {
            //checking all field, all field required
            if(binding.registerInputUsername.text!!.isNotEmpty() &&
                binding.registerInputEmail.text!!.isNotEmpty() &&
                binding.registerInputPassword.text!!.isNotEmpty() &&
                binding.registerInputKonfirmasiPassword.text!!.isNotEmpty()){
                //check the similarity between the password field and confirm password
                if(binding.registerInputPassword.text.toString() != binding.registerInputKonfirmasiPassword.text.toString()){
                    Toast.makeText(requireContext(), "Password dan konfirmasi password harus sama", Toast.LENGTH_SHORT).show()
                }else{
                    //if similar, then input user data to shared preference
                    inputUserData()
                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment)
                    Toast.makeText(requireContext(), "Data tersimpan", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Semua data belum terisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //function for input data in shared preference
    private fun inputUserData(){
        val dataUsername = binding.registerInputUsername.text.toString()
        val dataEmail = binding.registerInputEmail.text.toString()
        val datapassword = binding.registerInputPassword.text.toString()
        val dataKonfirmasiPassword = binding.registerInputKonfirmasiPassword.text.toString()

        val sharedPreference = requireContext().getSharedPreferences("DATAUSER", Context.MODE_PRIVATE)
        val spf = sharedPreference.edit()
        spf.putString("USERNAME", dataUsername)
        spf.putString("EMAIL", dataEmail)
        spf.putString("PASSWORD", datapassword)
        spf.putString("KONFIRMASIPASSWORD", dataKonfirmasiPassword)
        spf.apply()
    }
}