package com.example.ctc.group.add.view

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
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
import androidx.navigation.fragment.findNavController
import com.example.ctc.R
import com.example.ctc.api.RetrofitApi
import com.example.ctc.base.utils.ImageUtil
import com.example.ctc.base.utils.PermissionGrantUtils
import com.example.ctc.base.utils.goBack
import com.example.ctc.group.add.model.AddGroupRequest
import com.example.ctc.spend.add.view.AddSpendFragment
import com.raywenderlich.android.validatetor.ValidateTor
import kotlinx.android.synthetic.main.fragment_add_group.*
import kotlinx.android.synthetic.main.fragment_add_spend.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class AddGroupFragment : Fragment() {

    var api = RetrofitApi()
    private  lateinit var validateTor : ValidateTor
    private var url = ""
    private var uri: Uri? = null

    companion object {
        const val KEY_ADD_SPEND = "KEY_ADD_SPEND"
        const val REQUEST_CODE_CHOOSE_IMAGE_ADD_GROUP = 1506
        const val KEY_GRANT_STORAGE_PERMISSION_ADD_GROUP = 1106
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateTor = ValidateTor()
        onClick()
    }

    private fun onClick() {
        tvSaveAddGroup.setOnClickListener {
            validate()
        }
        imgAddGroup.setOnClickListener {
            initImage()
        }
        imgBackAddGroup.setOnClickListener {
            findNavController().navigate(R.id.action_addGroupFragment_to_nav_group)
        }
    }
    private fun validate() {
        validateTor.apply {
            when{
                edtGroup.text.toString().isEmpty() -> {
                    edtGroup.error = ""
                    showAlertDialog()
                }
                else -> {
                    doUpload()
                    addGroup(imgUrl = url)
            }
            }
        }
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
    private fun addGroup(imgUrl : String) {
       val nameGroup = edtGroup.text.toString()
        val itemRequest = AddGroupRequest(nameGroup,imgUrl)
        api.addGroup("",itemRequest,success = { _, response ->
            if (response.isSuccess) {
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
            startActivityForResult(intent, REQUEST_CODE_CHOOSE_IMAGE_ADD_GROUP)
        } else {
            PermissionGrantUtils.verify(this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE),
                KEY_GRANT_STORAGE_PERMISSION_ADD_GROUP
            )
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            KEY_GRANT_STORAGE_PERMISSION_ADD_GROUP-> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "image/*"
                    startActivityForResult(intent, REQUEST_CODE_CHOOSE_IMAGE_ADD_GROUP )

                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        when (requestCode) {
            REQUEST_CODE_CHOOSE_IMAGE_ADD_GROUP -> {
                data?.data?.let { uri ->
                    url = getRealPath(uri)
                    this.uri = uri
                    context?.let {
                        ImageUtil.loadImageSpend(it, uri, imageView = imgAddGroup)
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

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("You did not enter a group name")
        builder.setCancelable(false)
        builder.setPositiveButton(
            "ok"
        ) { _, _ -> }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}