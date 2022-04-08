package com.example.task_final_s4

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.task_final_s4.databinding.ItemAdapterCatatanBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


@DelicateCoroutinesApi
class CatatanAdapter(private val listCatatan : List<Catatan>) : RecyclerView.Adapter<CatatanAdapter.ViewHolder>() {
    private var dbCatatan : CatatanDatabase? = null
    //Define ViewHolder Class
    class ViewHolder(val binding: ItemAdapterCatatanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemAdapterCatatanBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvJudul.text = listCatatan[position].judul
        holder.binding.tvCatatan.text = listCatatan[position].catatan

        //Delete data
        holder.binding.buttonDelete.setOnClickListener {
            dbCatatan = CatatanDatabase.getInstance(it.context)

            //create custom dialog for delete process
            val customDialogDelete = LayoutInflater.from(it.context)
                .inflate(R.layout.custom_layout_dialog_hapus_data, null, false)
            val hapusDataDialog = AlertDialog.Builder(it.context)
                .setView(customDialogDelete)
                .create()

            val btnDelete = customDialogDelete.findViewById<Button>(R.id.hapus_dialog_button_cancel)
            val btnHapus = customDialogDelete.findViewById<Button>(R.id.hapus_dialog_button_hapus)
            //cancel delete action
            btnDelete.setOnClickListener {
                hapusDataDialog.dismiss()
            }
            //delete action button
            btnHapus.setOnClickListener {
                GlobalScope.async {

                    //command for room database
                    val command = dbCatatan?.catatanDao()?.deleteDataCatatan(listCatatan[position])

                    //check if delete process worked or not
                    (customDialogDelete.context as MainActivity).runOnUiThread{
                        if(command != 0){
                            Toast.makeText(customDialogDelete.context, "Catatan ${listCatatan[position].judul} berhasil dihapus", Toast.LENGTH_SHORT).show()
                            //recreate activity after delete process
                            (customDialogDelete.context as MainActivity).recreate()
                        }else{
                            Toast.makeText(customDialogDelete.context, "Catatan ${listCatatan[position].judul} gagal dihapus", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            hapusDataDialog.show()
        }

        //edit data
        holder.binding.buttonEdit.setOnClickListener {
            dbCatatan = CatatanDatabase.getInstance(it.context)

            //create dialog for edit action
            val customDialogEdit = LayoutInflater.from(it.context)
                .inflate(R.layout.custom_layout_dialog_edit_data, null, false)
            val editDataDialog = AlertDialog.Builder(it.context)
                .setView(customDialogEdit)
                .create()

            val btnUpdate = customDialogEdit.findViewById<Button>(R.id.edit_button_update_data)
            val btnJudul = customDialogEdit.findViewById<EditText>(R.id.edit_input_judul)
            val btnCatatan = customDialogEdit.findViewById<EditText>(R.id.edit_input_catatan)
            //edit action button
            btnUpdate.setOnClickListener {
                //get new data
                val newJudul = btnJudul.text.toString()
                val newCatatan = btnCatatan.text.toString()

                //re-initialize data of listCatatan that in current position
                listCatatan[position].judul = newJudul
                listCatatan[position].catatan = newCatatan

                GlobalScope.async {
                    //command for room database
                    val command = dbCatatan?.catatanDao()?.updateDataCatatan(listCatatan[position])
                    //check if edit process worked or not
                    (customDialogEdit.context as MainActivity).runOnUiThread{
                        if(command != 0){
                            Toast.makeText(it.context, "Catatan berhasil diupdate", Toast.LENGTH_SHORT).show()
                            //recreate activity
                            (customDialogEdit.context as MainActivity).recreate()
                        }else{
                            Toast.makeText(it.context, "Catatan gagal diupdate", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            editDataDialog.show()
        }


    }

    override fun getItemCount(): Int {
        return listCatatan.size
    }
}