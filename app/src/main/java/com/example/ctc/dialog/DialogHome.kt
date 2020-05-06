package com.example.ctc.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.ctc.R
import kotlinx.android.synthetic.main.dialog_home.*
import java.text.SimpleDateFormat
import java.util.*

class DialogHome : DialogFragment(){


    interface OnInputSelected {
        fun sendInput(input: String?)
    }

    private var mOnInputSelected: OnInputSelected? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       onClick()
    }

    private fun onClick() {
        edtFrom.setOnClickListener {
            dateOfFrom()
        }
        edtCome.setOnClickListener {
            dateOfCome()
        }
        tvCancel.setOnClickListener {
            dialog?.dismiss()
        }
        tvTime.setOnClickListener {
            val input = edtCome.text.toString() + " - " + edtFrom.text.toString()
            when {
                edtCome.text.toString().isEmpty() -> {
                    showAlertDialog()
                    edtCome.error = ""
                }
                edtFrom.text.toString().isEmpty() -> {
                    showAlertDialog()
                    edtFrom.error = ""
                }
                input != "" -> {
                    mOnInputSelected?.sendInput(input)
                }
            }
            dialog?.dismiss()
        }
    }
    private fun dateOfFrom() {
        val calendar: Calendar = Calendar.getInstance()
        val setDateListener: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                edtFrom.setText(getReadDate(calendar))

            }
        context?.let {val timeDialog = DatePickerDialog(it, setDateListener, calendar.get(Calendar.YEAR), calendar.get(
            Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
//            timeDialog.datePicker.maxDate = System.currentTimeMillis()
                timeDialog.show()
        }
    }
    private fun getReadDate(calendar: Calendar): String {
        val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return df.format(calendar.time)
    }
    private fun dateOfCome() {
        val calendar: Calendar = Calendar.getInstance()
        val setDateListener: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                edtCome.setText(getReadDate(calendar))

            }
        context?.let {val timeDialog = DatePickerDialog(it, setDateListener, calendar.get(Calendar.YEAR), calendar.get(
            Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
//            timeDialog.datePicker.maxDate = System.currentTimeMillis()
            timeDialog.show()
        }
    }
    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Hãy chọn thời gian")
        builder.setCancelable(false)
        builder.setPositiveButton(
            "ok"
        ) { _, _ -> }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mOnInputSelected = targetFragment as OnInputSelected?
        } catch (e: ClassCastException) {
        }
    }
}