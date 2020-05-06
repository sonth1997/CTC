package com.example.ctc.api

import com.example.ctc.base.ApiResponse
import com.example.ctc.group.add.model.AddGroupRequest
import com.example.ctc.group.model.Group
import com.example.ctc.login.model.LoginRequest
import com.example.ctc.login.model.Name
import com.example.ctc.logs.model.Logs
import com.example.ctc.spend.add.model.SpendDetailRequest
import com.example.ctc.spend.add.model.Image
import com.example.ctc.spend.model.SpendDetail
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface RetrofitService{

    @POST("api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<ApiResponse<Name>>

    @GET("/api/group")
    fun group(): Call<ApiResponse<ArrayList<Group>>>

    @POST("/api/group")
    fun addGroup(@Body addGroupRequest: AddGroupRequest):Call<ApiResponse<ArrayList<Group>>>

    @PUT("/api/group/{id}")
    fun updateGroup(@Path("id")id:String,@Body addGroupRequest: AddGroupRequest):Call<ApiResponse<ArrayList<Group>>>

    @DELETE("/api/group/{id}")
    fun deleteGroup(@Path("id")id:String):Call<ApiResponse<ArrayList<Group>>>

    //spend
    @GET("/api/transaction/{group_id}")
    fun spend(@Path("group_id")group_id:String):Call<ApiResponse<ArrayList<SpendDetail>>>

    @POST("/api/transaction/{group_id}")
    fun addSpend(@Path("group_id") group_id:String,@Body spendDetailRequest: SpendDetailRequest):Call<ApiResponse<Any>>

    @Multipart
    @POST("/api/uploadFiles?folder=xxx")
    fun upLoadImage(@Part body : MultipartBody.Part) : Call<ApiResponse<Image>>

    @PUT("/api/transaction/{id}")
    fun updateSpend(@Path("id") id:String,@Body spendDetailRequest: SpendDetailRequest):Call<ApiResponse<SpendDetail>>

    @DELETE("/api/transaction/{id}")
    fun deleteSpend(@Path("id") id:String):Call<ApiResponse<SpendDetail>>

    //logs
    @GET("/api/activity")
    fun logs(): Call<ApiResponse<ArrayList<Logs>>>

}


