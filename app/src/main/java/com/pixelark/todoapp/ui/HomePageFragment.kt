package com.pixelark.todoapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pixelark.todoapp.R
import com.pixelark.todoapp.data.ContentListAdapter
import com.pixelark.todoapp.data.enum.TodoPriority
import com.pixelark.todoapp.data.source.ContentDatabase
import com.pixelark.todoapp.databinding.DialogAddContentBinding
import com.pixelark.todoapp.databinding.FragmentHomePageBinding

class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var contentListAdapter: ContentListAdapter
    private var isInEditMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(layoutInflater)
        binding.fragmentHomePageRvRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        contentListAdapter = ContentListAdapter()
        //dummy()
        binding.fragmentHomePageRvRecyclerView.adapter = contentListAdapter
        binding.fab.setOnClickListener {
            showAddDialog()
        }
        binding.fragmentHomePageBtnEdit.setOnClickListener {
            if (!isInEditMode) {
                binding.fragmentHomePageBtnDelete.isVisible = true
                isInEditMode = true
                binding.fragmentHomePageBtnEdit.text = getString(R.string.text_done)
            } else {
                binding.fragmentHomePageBtnDelete.isVisible = false
                isInEditMode = false
                binding.fragmentHomePageBtnEdit.text = getString(R.string.text_edit)
            }
            contentListAdapter.setEditMode(isInEditMode)
        }

        binding.fragmentHomePageBtnDelete.setOnClickListener {
            contentListAdapter.deleteSelected()
        }
        return binding.root
    }

    private fun dummy() {
        for (i in 0..10) {
            val title = "data$i"
            val description = "desc$i"
            val newItem = ContentDatabase.addContent(title, description)
            contentListAdapter.addItem(newItem)
        }
    }

    private fun showAddDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = DialogAddContentBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()

        with(dialogBinding) {
            var selectedPriority = TodoPriority.LOW
            initGenderRadioGroup(dialogBinding) { priority ->
                selectedPriority = priority
            }
            dialogAddContentBtnSave.setOnClickListener {
                val title = dialogAddContentEtTitle.text.toString()
                val description = dialogAddContentEtDescription.text.toString()
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    val newItem = ContentDatabase.addContent(title, description, selectedPriority)
                    contentListAdapter.addItem(newItem)
                    dialog.dismiss()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_info),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            //var selectedColor: Int = R.color.back_white
        }
        dialog.show()
    }

    private fun initGenderRadioGroup(
        dialogBinding: DialogAddContentBinding,
        onPrioritySelected: (TodoPriority) -> Unit
    ) {
        val priorityList = requireActivity().resources.getStringArray(R.array.priority_val)

        dialogBinding.dialogAddContentRbLow.text = priorityList[0]
        dialogBinding.dialogAddContentRbMedium.text = priorityList[1]
        dialogBinding.dialogAddContentRbHigh.text = priorityList[2]

        dialogBinding.dialogAddContentRgRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.dialogAddContent_rbLow -> onPrioritySelected(TodoPriority.LOW)

                R.id.dialogAddContent_rbMedium -> onPrioritySelected(TodoPriority.MEDIUM)

                R.id.dialogAddContent_rbHigh -> onPrioritySelected(TodoPriority.HIGH)
            }
        }
    }
}