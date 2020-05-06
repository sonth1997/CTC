package com.example.ctc.spend.detail.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ctc.R
import com.example.ctc.api.RetrofitApi
import com.example.ctc.base.utils.goBack
import com.example.ctc.dialog.DialogComment
import com.example.ctc.group.model.Group
import com.example.ctc.spend.add.model.SpendDetailRequest
import com.example.ctc.spend.detail.model.Split
import com.example.ctc.spend.model.SpendDetail
import com.example.ctc.spend.view.SpendFragment
import com.example.ctc.utils.extension.loadImages
import kotlinx.android.synthetic.main.fragment_add_spend.*
import kotlinx.android.synthetic.main.fragment_edit_spend.*
import kotlinx.android.synthetic.main.fragment_spend.*
import kotlinx.android.synthetic.main.fragment_spend_detail_after.*

class SpendDetailAfterFragment : Fragment() {

    val api = RetrofitApi()
    private var group: Group? = null
    private var spend: SpendDetail? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_spend_detail_after, container, false)
    }

    companion object {
        const val KEY_SPEND_AFTER = "KEY_SPEND_AFTER"
    }

    private lateinit var adapterSplit: SpendDetailAfterAdapter
    private lateinit var adapterNote: SpendDetailAfterAdapter
    private lateinit var adapterReceipt: SpendDetailAfterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterSplit = SpendDetailAfterAdapter(SpendDetailAfterAdapter.SPLIT)
        adapterNote = SpendDetailAfterAdapter(SpendDetailAfterAdapter.NOTE)
        adapterReceipt = SpendDetailAfterAdapter(SpendDetailAfterAdapter.RECEIPT)
        rcvNote.adapter = adapterNote
        rcvReceipt.adapter = adapterReceipt
        onClick()
        iniViews()
    }

    private fun iniViews() {
        arguments?.let { it ->
            if (!it.isEmpty)
                spend = it.getParcelable(KEY_SPEND_AFTER)
            spend?.let {
                tvTitleSpend.text = it.title
//                imgSpendDetail.loadImages(it.image)
                tvMoneySpendDetail.text = it.amount.toString()
                tvSpendDate.text = it.created_at
                tvSpendDateUpdate.text = it.updated_at
            }

        }
    }

    private fun onClick() {
        floatingSpendDetailAfter.setOnClickListener {
            findNavController().navigate(R.id.action_spendDetailAfterFragment_to_updateSpendFragment, Bundle().apply {
                putParcelable(UpdateSpendFragment.KEY_UPDATE_SPEND,spend)
            })
        }
        imdBackSplit.setOnClickListener {
            goBack()
        }
        btnAddComment.setOnClickListener {
//            val dialog = DialogComment()
//            dialog.setTargetFragment(this, 1)
//            dialog.show(fragmentManager!!, "b")
        }
        btnDeleteSpend.setOnClickListener {
            val dialog : AlertDialog = AlertDialog.Builder(context)
                .setTitle("Delete this expensive")
                .setMessage("This will completely remove this expensive for All people involved, not just you.")
                .setPositiveButton("Ok",null)
                .setNegativeButton("Cancel",null)
                .show()
            val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                dialog.dismiss()
                deleteSpend()

        }
            val negativeButton: Button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    private fun deleteSpend() {
        api.deleteSpend("", spend?.transaction_id.toString(), success = { _, response ->
            if (response.isSuccess) {
                Log.e("success", response.message.toString())
                goBack()
            } else {
                Log.e("loi code", response.message.toString())
            }
        }, error = { _, err ->
            Log.e("log err", err.message.toString())
        })
    }
}