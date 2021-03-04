package com.example.noteapp.fragment

import android.graphics.Canvas
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.R
import com.example.noteapp.databinding.ListFragmentBinding
import com.example.noteapp.fragment.adapter.UserAdapter
import com.example.noteapp.fragment.adapter.UserListener
import com.example.noteapp.model.User
import com.example.noteapp.viewmodel.UserViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class ListFragment : Fragment(),SearchView.OnQueryTextListener {
    lateinit var binding: ListFragmentBinding
    lateinit var viewModel: UserViewModel
    lateinit var userAdapter:UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        setUpRecyclerView()

        binding.fabAddNote.setOnClickListener {
            val action=ListFragmentDirections.actionListFragmentToAddListFragment()
            findNavController().navigate(action)
        }


        //this is for setting left
        val myCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position: Int = viewHolder.adapterPosition
                viewModel.delete(userAdapter.getPositionAt(position))
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                context?.let { ContextCompat.getColor(it, R.color.colorAccent) }?.let {
                    RecyclerViewSwipeDecorator.Builder(
                        c,
                        binding.recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                        .addSwipeLeftBackgroundColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.colorAccent
                            )
                        )
                        .addSwipeLeftActionIcon(R.drawable.ic_delete)
                        .addSwipeRightBackgroundColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.design_default_color_on_primary
                            )
                        )
                        .addSwipeRightActionIcon(R.drawable.ic_archive)
                        .addBackgroundColor(it)
                        .addActionIcon(R.drawable.ic_delete)
                        .create()
                        .decorate()
                }

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }
        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(binding.recyclerView)




        return binding.root
    }

    private fun setUpRecyclerView() {
        userAdapter= UserAdapter(UserListener { user->
            val action=ListFragmentDirections.actionListFragmentToUpdateListFrgament(user)
            findNavController().navigate(action)
        })

        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            adapter = userAdapter
        }

        activity?.let {
            viewModel.userTable.observe(viewLifecycleOwner, Observer {
                it?.let {
                    userAdapter.submitList(it)
                    updateUI(it)
                }
            })
        }
    }

    private fun updateUI(user:List<User>){
        if (user.isNotEmpty()) {
            binding.cardView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        } else {
            binding.cardView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)
        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as androidx.appcompat.widget.SearchView
        mMenuSearch.isSubmitButtonEnabled = false
        mMenuSearch.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        if (newText != null) {
            searchNote(newText)
        }
        return true
    }
    private fun searchNote(query: String?) {
        val searchQuery = "%$query%"
        viewModel.searchNote(searchQuery).observe(
            this, { list ->
                userAdapter.submitList(list)
            }
        )
    }

}