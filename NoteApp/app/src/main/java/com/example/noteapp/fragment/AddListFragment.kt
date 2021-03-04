package com.example.noteapp.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.AddListFragmentBinding
import com.example.noteapp.model.User
import com.example.noteapp.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class AddListFragment : Fragment() {
    lateinit var binding: AddListFragmentBinding
    lateinit var viewModel: UserViewModel
    lateinit var mView:View
    lateinit var time:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_list_fragment, container, false)
        viewModel=ViewModelProvider(this).get(UserViewModel::class.java)
        mView=binding.root


        /**
         * text
         */
        val systemTime=System.currentTimeMillis()
        time=convertLongToDateString(systemTime)


        //button click
        binding.fabCalculate.setOnClickListener {
            calculateAppIntent()
        }
        return binding.root
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun calculateAppIntent(){
        val items = ArrayList<HashMap<String, Any>>()
        val p: PackageManager = requireContext().packageManager//activity
        val packs = p.getInstalledPackages(0)
        for (pi in packs) {
            if (pi.packageName.toString().toLowerCase(Locale.ROOT).contains("calcul")) {
                val map = HashMap<String, Any>()
                map["appName"] = pi.applicationInfo.loadLabel(p)
                map["packageName"] = pi.packageName
                items.add(map)
            }
        }
        if (items.size >= 1) {
            val packageName = items[0]["packageName"] as String?
            val i = p.getLaunchIntentForPackage(packageName!!)
            i?.let { startActivity(it) }//it
        } else {
            // Application not found
            Toast.makeText(context,"application not found", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convertLongToDateString(systemTime: Long): String {
        return SimpleDateFormat(" MMMM dd, h:mm a")
            .format(systemTime).toString()
    }

    private fun save(view: View) {
        val title = binding.etNoteTitle.editText?.text.toString().trim()
        val description = binding.etNoteBody.text.toString().trim()

        if (title.isNotEmpty() && description.isNotEmpty()) {
            val user = User(title = title, description = description, time = time)
            viewModel.insert(user)

            Toast.makeText(activity, "Record inserted", Toast.LENGTH_SHORT).show()

            val action = AddListFragmentDirections.actionAddListFragmentToListFragment()
            view.findNavController().navigate(action)

        } else {
            Toast.makeText(activity, "please enter title or notes", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.save_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                save(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}