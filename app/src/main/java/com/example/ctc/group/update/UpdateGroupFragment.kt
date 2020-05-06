package com.example.ctc.group.update

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ctc.R
import com.example.ctc.api.RetrofitApi
import com.example.ctc.base.utils.goBack
import com.example.ctc.group.add.model.AddGroupRequest
import com.example.ctc.group.model.Group
import com.example.ctc.spend.view.SpendFragment
import com.example.ctc.utils.extension.loadImages
import com.raywenderlich.android.validatetor.ValidateTor
import kotlinx.android.synthetic.main.fragment_add_group.*
import kotlinx.android.synthetic.main.fragment_edit_group.*


class UpdateGroupFragment : Fragment() {

    private lateinit var validateTor: ValidateTor
    var api = RetrofitApi()
    private var group: Group? = null
    private var deleteGroup = arrayListOf<Group>()
    private var updateGroup = arrayListOf<Group>()
    private var idSpend: Group? = null

    companion object {
        const val KEY_UPDATE = "key.update"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idSpend = arguments?.getParcelable(KEY_UPDATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateTor = ValidateTor()
        onClick()
        iniView()
    }

    private fun onClick() {
        tvSaveEditGroup.setOnClickListener {
            validate()
        }
        btnDeleteGroup.setOnClickListener {
            val dialog: AlertDialog = AlertDialog.Builder(context)
                .setTitle("Xóa nhóm")
                .setMessage("Bạn có chắc chắn muốn xóa nhóm này không? Điều này sẽ xóa nhóm này cho tất cả người dùng tham gia, không chỉ bản thân bạn")
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .show()
            val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                dialog.dismiss()
                deleteGroup(idSpend?.group_id.toString())

            }
            val negativeButton: Button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setOnClickListener {
                dialog.dismiss()
            }

        }
        imgBackEditGroup.setOnClickListener {
            findNavController().navigate(R.id.action_updateGroupFragment_to_spendFragment2,
                Bundle().apply {
                    putParcelable(SpendFragment.KEY_ID, group)
                })
        }
    }
    fun validate() {
        validateTor.apply {
            when {
                edtEditGroup.text.toString().isEmpty() -> {
                    edtEditGroup.error = ""
                    showAlertDialog()
                }
                else -> {
                    updateGroup(idSpend?.group_id.toString())
                }
            }
        }
    }
    private fun iniView() {
        arguments?.let { it ->
            if (!it.isEmpty)
                group = it.getParcelable(KEY_UPDATE)
            group?.let {
                edtEditGroup.setText(it.name)
                imgEditGroup.loadImages(it.image)
            }
        }
    }

    private fun deleteGroup(id : String) {
        api.deleteGroup("",id,success = { _, response ->
            if (response.isSuccess) {
                deleteGroup = response.data ?: arrayListOf()
                    findNavController().navigate(R.id.action_updateGroupFragment_to_nav_group)
            } else {
                Log.e("loi code", response.message.toString())
            }
        }, error = { _, err ->
            Log.e("log err", err.message.toString())
        })
    }

    private fun updateGroup(imgUrl : String) {
        group?.name = edtEditGroup.text.toString()
        val nameGroupEdit = edtEditGroup.text.toString()
        val itemRequest = AddGroupRequest(nameGroupEdit,imgUrl)
        api.updateGroup("", group?.group_id.toString(),itemRequest,success = { _, response ->
            if (response.isSuccess) {
                updateGroup = response.data ?: arrayListOf()
                    goBack()
            } else {
                Log.e("loi code", response.message.toString())
            }
        }, error = { _, err ->
            Log.e("log err", err.message.toString())
        })
    }


    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("You haven't entered a name for your group yet!")
        builder.setCancelable(false)
        builder.setPositiveButton(
            "ok"
        ) { _, _ -> }
        val alertDialog = builder.create()
        alertDialog.show()
    }
//    private fun returnWithName() {
//        val name = edtEditGroup.text.toString()
//        (targetFragment as? SpendFragment)?.setName(name)
//    }

}
