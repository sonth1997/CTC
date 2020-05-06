package com.example.ctc.spend.add.view

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
import androidx.fragment.app.Fragment
import com.example.ctc.R
import com.example.ctc.api.RetrofitApi
import com.example.ctc.base.utils.ImageUtil
import com.example.ctc.base.utils.PermissionGrantUtils
import com.example.ctc.base.utils.goBack
import com.example.ctc.group.model.Group
import com.example.ctc.spend.add.model.SpendDetailRequest
import com.example.ctc.spend.model.SpendDetail
import com.raywenderlich.android.validatetor.ValidateTor
import kotlinx.android.synthetic.main.fragment_add_spend.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION")
class AddSpendFragment : Fragment() {
        private lateinit var validateTor: ValidateTor
        var api = RetrofitApi()
        private var group: Group? = null
        private var addSpends = arrayListOf<SpendDetail>()
        private var spend : SpendDetail ?= null
        private var url = "https://img.icons8.com/ultraviolet/40/000000/check.png"
        private var uri: Uri? = null

        companion object {
            const val KEY_ADD_SPEND = "KEY_ADD_SPEND"
            const val REQUEST_CODE_CHOOSE_IMAGE = 1506
            const val KEY_GRANT_STORAGE_PERMISSION = 1106
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            group = arguments?.getParcelable(KEY_ADD_SPEND)
        }
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_add_spend,container,false)
        }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            validateTor = ValidateTor()
    //        iniViews()
            onClick()

        }
         fun onClick() {
            imgBackAddSpend.setOnClickListener {
               goBack()
            }
            tvSaveAddSpend.setOnClickListener {
                validatePasswordField()
            }
             tvTodayAddSpend.setOnClickListener {
                dateOfFrom()
            }
            imgAddSpend.setOnClickListener {
                initImage()
            }
        }

    private fun validatePasswordField() {
        validateTor.apply {
            when{
                edtJob.text.toString().isEmpty() -> {
                edtJob.error = ""
                    showAlertDialogJob()
                }
                edtMoney.text.toString().isEmpty() -> {
                    edtMoney.error = ""
                    showAlertDialogMoney()
                }
                tvTodayAddSpend.text.toString().isEmpty() ->{
                    tvTodayAddSpend.error =""
                    showAlertDialogToday()
            }else -> {
                doUpload()
                addSpend(imgUrl = url)
                }
            }
        }
    }
        private fun dateOfFrom() {
            val calendar: Calendar = Calendar.getInstance()
            val setDateListener: DatePickerDialog.OnDateSetListener =
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, day)
                    tvTodayAddSpend.text = getReadDate(calendar)
                }
            context?.let {val timeDialog = DatePickerDialog(it, setDateListener, calendar.get(Calendar.YEAR), calendar.get(
                Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                timeDialog.datePicker.maxDate = System.currentTimeMillis()
                timeDialog.show()
            }
        }
        private fun getReadDate(calendar: Calendar): String {
            val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return df.format(calendar.time)
        }


        fun doUpload() {
            val file = File(url)
            val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            var filePath = file.absolutePath
            val image = filePath.split(("\\.").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            filePath = image[0] + System.currentTimeMillis() + "." + image[1]
            val body = MultipartBody.Part.createFormData("files[]", filePath, reqFile)
            api.upLoadImage("", body,success = { _, response ->
                if (response.isSuccess){
                    Log.e("success", "ok")
                    goBack()
                } else {
                    Log.e("loi code", response.message.toString())
                }
                if (response.data?.objects?.size == 0) return@upLoadImage
            }, error = { _, err ->
                Log.e("log err", err.message.toString())
            })
        }

        private fun addSpend(imgUrl: String){
            val nameSpend = edtJob?.text?.toString()
            val moneySpend = edtMoney?.text?.toString()?.toInt()
            val dateSpend = tvTodayAddSpend?.text?.toString()
            val note = edtNoteAddSpend?.text?.toString()
            val itemSpendRequest = SpendDetailRequest(nameSpend,moneySpend,dateSpend,note,imgUrl)
            api.addSpend("",group?.group_id.toString(),itemSpendRequest,success = { _, response ->
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
                startActivityForResult(intent, REQUEST_CODE_CHOOSE_IMAGE)
            } else {
                PermissionGrantUtils.verify(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    KEY_GRANT_STORAGE_PERMISSION)
            }
        }
        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when (requestCode) {
                KEY_GRANT_STORAGE_PERMISSION -> {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        val intent = Intent(Intent.ACTION_GET_CONTENT)
                        intent.type = "image/*"
                        startActivityForResult(intent, REQUEST_CODE_CHOOSE_IMAGE)

                    }
                }
            }
        }
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (resultCode != Activity.RESULT_OK) return
            when (requestCode) {
                REQUEST_CODE_CHOOSE_IMAGE -> {
                    data?.data?.let { uri ->
                        url = getRealPath(uri)
                        this.uri = uri
                        context?.let {
                            ImageUtil.loadImageSpend(it, uri, imageView = imgAddSpend)
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
            val cursor = context?.contentResolver?.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Images.Media.DATA), sel, arrayOf(id), null)
            cursor?.let {
                it.moveToFirst()
                val index = cursor.getColumnIndex(arrayOf(MediaStore.Images.Media.DATA)[0])
                result = cursor.getString(index)
                cursor.close()
            }
            return result
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