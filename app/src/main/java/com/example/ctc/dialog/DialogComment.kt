package com.example.ctc.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.ctc.R
import kotlinx.android.synthetic.main.dialog_comment.*

class DialogComment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_comment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
    }

    private fun onClick() {
        tvCancelDialog.setOnClickListener {
            dialog?.dismiss()
        }
        tvOkDialog.setOnClickListener {
            dialog?.dismiss()
        }
    }
}