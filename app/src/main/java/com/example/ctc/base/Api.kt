package com.example.ctc.base

import com.example.ctc.group.model.Group
import com.example.ctc.login.model.LoginRequest
import com.example.ctc.login.model.Name
import com.example.ctc.base.api.errors.BaseError
import com.example.ctc.group.add.model.AddGroupRequest
import com.example.ctc.logs.model.Logs
import com.example.ctc.spend.add.model.SpendDetailRequest
import com.example.ctc.spend.add.model.Image
import com.example.ctc.spend.model.SpendDetail
import okhttp3.MultipartBody


internal typealias ApiError = (requestId: String, e: BaseError) -> Unit

internal typealias ApiSuccess<T> = (requestId: String, T) -> Unit

interface Api {

    fun login(request: String, loginRequest: LoginRequest, success: ApiSuccess<ApiResponse<Name>>, error: ApiError)

    fun group(request: String, error: ApiError, success: ApiSuccess<ApiResponse<ArrayList<Group>>>)

    fun addGroup(request: String, addGroupRequest: AddGroupRequest, error: ApiError, success: ApiSuccess<ApiResponse<ArrayList<Group>>>)

    fun updateGroup(request: String,id:String, addGroupRequest: AddGroupRequest, error: ApiError, success: ApiSuccess<ApiResponse<ArrayList<Group>>>)

    fun deleteGroup(request: String, id:String ,error: ApiError, success: ApiSuccess<ApiResponse<ArrayList<Group>>>)

    fun spend(request: String, group_id:String,error: ApiError, success: ApiSuccess<ApiResponse<ArrayList<SpendDetail>>>)

    fun addSpend(request: String, group_id:String, spendDetailRequest: SpendDetailRequest, error: ApiError, success: ApiSuccess<ApiResponse<Any>>)

    fun updateSpend(request: String, id:String, spendDetailRequest: SpendDetailRequest, error: ApiError, success: ApiSuccess<ApiResponse<SpendDetail>>)

    fun deleteSpend(request: String, id:String, error: ApiError, success: ApiSuccess<ApiResponse<SpendDetail>>)

    fun upLoadImage(request: String, body :MultipartBody.Part, error: ApiError, success: ApiSuccess<ApiResponse<Image>>)

    fun logs(request: String, error: ApiError, success: ApiSuccess<ApiResponse<ArrayList<Logs>>>)
}