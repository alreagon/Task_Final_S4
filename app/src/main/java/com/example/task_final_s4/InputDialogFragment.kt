package com.example.task_final_s4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.task_final_s4.databinding.FragmentInputDialogBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@DelicateCoroutinesApi
class InputDialogFragment : DialogFragment() {
    private var _binding: FragmentInputDialogBinding? = null
    private val binding get() = _binding!!
    private var dbCatatan: CatatanDatabase? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInputDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbCatatan = CatatanDatabase.getInstance(requireContext())

        //action for button "tambah data"
        binding.tambahButtonInput.setOnClickListener {
            GlobalScope.async {
                //get judul and catatan value
                val judul = binding.tambahInputJudul.text.toString()
                val catatan = binding.tambahInputCatatan.text.toString()
                //command for room database
                val command = dbCatatan?.catatanDao()?.insertCatatan(Catatan(null, judul, catatan))
                activity?.runOnUiThread {
                    if (command != 0.toLong()) {
                        Toast.makeText(requireContext(), "Sukses", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            dismiss()
        }

    }

    override fun onDetach() {
        super.onDetach()
        activity?.recreate()
    }
}