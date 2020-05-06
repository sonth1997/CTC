package com.example.ctc.spend.detail.view

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ctc.R
import com.example.ctc.api.RetrofitApi
import com.example.ctc.base.utils.ImageUtil
import com.example.ctc.base.utils.PermissionGrantUtils
import com.example.ctc.base.utils.goBack
import com.example.ctc.group.model.Group
import com.example.ctc.group.view.GroupFragment
import com.example.ctc.spend.add.model.SpendDetailRequest
import com.example.ctc.spend.add.view.AddSpendFragment
import com.example.ctc.spend.model.SpendDetail
import com.example.ctc.utils.extension.loadImages
import com.raywenderlich.android.validatetor.ValidateTor
import kotlinx.android.synthetic.main.fragment_add_spend.*
import kotlinx.android.synthetic.main.fragment_edit_spend.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class UpdateSpendFragment : Fragment() {

    private lateinit var validateTor: ValidateTor
    val api = RetrofitApi()
    private var spend: SpendDetail? = null
    private var uri: Uri? = null
    private var url = ""

    companion object{

        const val KEY_UPDATE_SPEND = "KEY_UPDATE_SPEND"
        const val REQUEST_CODE_CHOOSE_IMAGE_EDIT = 1505
        const val KEY_GRANT_STORAGE_PERMISSION_EDIT = 1105

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_spend, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateTor = ValidateTor()
        onClick()
        iniViews()
        url = spend?.image.toString()
    }

    private fun onClick() {
        tvSaveEditSpend.setOnClickListener {
           validator()

        }
        imgBackEditSpend.setOnClickListener {
            goBack()
        }
        tvTodayEditSpend.setOnClickListener {
            dateOfFrom()
        }
        imgEditSpend.setOnClickListener {
            initImage()
        }
    }
    private fun validator () {
        validateTor.apply {
            when {
                edtJobEdit.text.toString().isEmpty() -> {
                    edtJobEdit.error = ""
                    showAlertDialogJob()
                }
                edtMoneyEdit.text.toString().isEmpty() -> {
                    edtMoneyEdit.error = ""
                    showAlertDialogMoney()
                }
                tvTodayEditSpend.text.toString().isEmpty() -> {
                    tvTodayEditSpend.error = ""
                    showAlertDialogToday()
                }
                else -> {
                    doUpload()
                    updateSpend(imgUrl = url)
                }
            }
        }
    }
    private fun iniViews() {
        arguments?.let { it ->
            if (!it.isEmpty)
                spend = it.getParcelable(KEY_UPDATE_SPEND)
            spend?.let {
                edtJobEdit.setText(it.title)
                edtMoneyEdit.setText(it.amount.toString())
                tvTodayEditSpend.text = it.date_transaction
                edtNoteEditSpend.setText(it.note)
                imgEditSpend.loadImages(it.image)
            }

        }
    }
    fun doUpload() {
        val file = File(url)
        val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("files[]", file.name, reqFile)
        api.upLoadImage("", body,success = { _, response ->
            if (response.isSuccess){
                Log.e("success", "ok")

            } else {
                Log.e("loi code", response.message.toString())
            }
            if (response.data?.objects?.size == 0) return@upLoadImage
        }, error = { _, err ->
            Log.e("log err", err.message.toString())
        })
    }
    private fun updateSpend(imgUrl: String) {
        spend?.title = edtJobEdit.text.toString()
        spend?.amount = edtMoneyEdit.text.toString().toInt()
        spend?.date_transaction = tvTodayEditSpend.text.toString()
        spend?.note = edtNoteEditSpend.text.toString()
        spend?.image = imgUrl
        val nameEditSpend = edtJobEdit.text.toString()
        val moneyEditSpend = edtMoneyEdit.text.toString().toInt()
        val dateEditSpend = tvTodayEditSpend.text.toString()
        val noteEdit = edtNoteEditSpend.text.toString()
        val itemSpendRequest = SpendDetailRequest(nameEditSpend, moneyEditSpend, dateEditSpend, noteEdit, imgUrl)
        api.updateSpend("", spend?.transaction_id.toString(), itemSpendRequest, success = { _, response ->
            if (response.isSuccess) {
                Log.e("success", "ok")
                goBack()
            } else {
                Log.e("loi code", response.message.toString())

            }
        }, error = { _, err ->
            Log.e("log err", err.message.toString())
        })
    }
    private fun initImage() {
        if (PermissionGrantUtils.checkSelfPermission(context,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_CHOOSE_IMAGE_EDIT)
        } else {
            PermissionGrantUtils.verify(this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    KEY_GRANT_STORAGE_PERMISSION_EDIT
            )
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            KEY_GRANT_STORAGE_PERMISSION_EDIT -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "image/*"
                    startActivityForResult(intent, REQUEST_CODE_CHOOSE_IMAGE_EDIT)

                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        when (requestCode) {
            REQUEST_CODE_CHOOSE_IMAGE_EDIT -> {
                data?.data?.let { uri ->
                    url = getRealPath(uri)
                    this.uri = uri
                    context?.let {
                        ImageUtil.loadImageSpend(it, uri, imageView = imgEditSpend)
                    }

                }
            }
        }
    }

    private fun getRealPath(uri: Uri): String{
        var result = ""
        val doc = DocumentsContract.getDocumentId(uri)
        val id = doc.split(":")[1]
        val sel = MediaStore.Images.Media._ID + "=?"
        val cursor = context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media.DATA), sel, arrayOf(id), null)
        cursor?.let {
            it.moveToFirst()
            val index = cursor.getColumnIndex(arrayOf(MediaStore.Images.Media.DATA)[0])
            result = cursor.getString(index)
            cursor.close()
        }
        return result
    }
    private fun dateOfFrom() {
        val calendar: Calendar = Calendar.getInstance()
        val setDateListener: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                tvTodayEditSpend.text = getReadDate(calendar)
            }
        context?.let {val timeDialog = DatePickerDialog(it, setDateListener, calendar.get(
            Calendar.YEAR), calendar.get(
            Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            timeDialog.datePicker.maxDate = System.currentTimeMillis()
            timeDialog.show()
        }
    }
    private fun getReadDate(calendar: Calendar): String {
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return df.format(calendar.time)
    }
    private fun showAlertDialogJob() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("You did not enter a group name")
        builder.setCancelable(false)
        builder.setPositiveButton(
            "ok"
        ) { _, _ -> }
        val alertDialog = builder.create()
        alertDialog.show()
    }
    private fun showAlertDialogMoney() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("You have not entered the amount")
        builder.setCancelable(false)
        builder.setPositiveButton(
            "ok"
        ) { _, _ -> }
        val alertDialog = builder.create()
        alertDialog.show()
    }
    private fun showAlertDialogToday() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("You have not entered a date")
        builder.setCancelable(false)
        builder.setPositiveButton(
            "ok"
        ) { _, _ -> }
        val alertDialog = builder.create()
        alertDialog.show()
    }

}
