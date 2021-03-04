package com.example.noteapp.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Html
import android.view.*
import android.view.Gravity.apply
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.UpdateListFragmentBinding
import com.example.noteapp.model.User
import com.example.noteapp.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class UpdateListFrgament : Fragment() {
    lateinit var binding: UpdateListFragmentBinding
    lateinit var viewModel: UserViewModel
    lateinit var currentUser: User
    lateinit var time: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.update_list_fragment, container, false)
        //val application= requireNotNull(this.activity).application
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val args = UpdateListFrgamentArgs.fromBundle(requireArguments())
        currentUser = args.user
        binding.etNoteTitleUpdate.setText(currentUser.title)
        binding.etNoteBodyUpdate.setText(currentUser.description)

        binding.fabShare.setOnClickListener {
            val sendIntent:Intent=Intent().apply{
                action=Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT,"Note")
                putExtra(Intent.EXTRA_TEXT,"${currentUser.title} :\n ${currentUser.description}")
                type="text/plane"
            }
            val shareIntent=Intent.createChooser(sendIntent,"choose one")
            startActivity(shareIntent)
        }

        val systemTime = System.currentTimeMillis()
        time = convertLongToDateString(systemTime)

        binding.fabDone.setOnClickListener {
            val title = binding.etNoteTitleUpdate.text.toString().trim()
            val description = binding.etNoteBodyUpdate.text.toString().trim()
            if (title.isNotEmpty() && description.isNotEmpty()) {
                val user = User(
                    userId = currentUser.userId,
                    title = title,
                    description = description,
                    time = time
                )
                viewModel.update(user)
                val action = UpdateListFrgamentDirections.actionUpdateListFrgamentToListFragment()
                findNavController().navigate(action)
            }
        }

        binding.fabCalculate2.setOnClickListener {
            calculateAppIntent()
        }
        return binding.root
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun calculateAppIntent() {
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
            Toast.makeText(context, "application not found", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convertLongToDateString(systemTime: Long): String {
        return SimpleDateFormat("MMMM dd, h:mm a")
            .format(systemTime).toString()
    }

    private fun deleteData() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Data")
            setMessage("Are you sure you wan to permanently delete this data?")
            setPositiveButton("DELETE") { _, _ ->
                viewModel.delete(currentUser)
                val action = UpdateListFrgamentDirections.actionUpdateListFrgamentToListFragment()
                view?.findNavController()?.navigate(action)
            }
            setNegativeButton("CANCEL", null)
        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteData()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}