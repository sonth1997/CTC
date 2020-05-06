package com.example.ctc.api

import com.example.ctc.BuildConfig
import com.example.ctc.Config
import com.example.ctc.base.Api
import com.example.ctc.base.ApiError
import com.example.ctc.base.ApiResponse
import com.example.ctc.base.ApiSuccess
import com.example.ctc.group.add.model.AddGroupRequest
import com.example.ctc.group.model.Group
import com.example.ctc.login.model.LoginRequest
import com.example.ctc.login.model.Name
import com.example.ctc.logs.model.Logs
import com.example.ctc.pref.AppPrefs
import com.example.ctc.spend.add.model.SpendDetailRequest
import com.example.ctc.spend.add.model.Image
import com.example.ctc.spend.model.SpendDetail
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author HungHN on 3/15/2019.
 */

class RetrofitApi : Api {

    private var retrofitService: RetrofitService

    init {
        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor())
                .addInterceptor { chain ->
                    val ongoing = chain.request().newBuilder()
                    ongoing.header("Content-Type", "application/json")
                    ongoing.header("Accept", "application/json")
                    ongoing.header("Content-Type", "multipart/form-data")
                    val tokenType = AppPrefs.getInstance().name?.access_token
                    if (!tokenType.isNullOrEmpty()) {
                        ongoing.addHeader("Authorization", "Bearer $tokenType")
                    }
                    chain.proceed(ongoing.build())
                }
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(Config.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        retrofitService = retrofit.create(RetrofitService::class.java)
    }

    private fun httpLoggingInterceptor(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }
    override fun login(request: String, loginRequest: LoginRequest, success: ApiSuccess<ApiResponse<Name>>, error: ApiError) {
        val call = retrofitService.login(loginRequest)
        call.enqueue(RetrofitCallback<ApiResponse<Name>> (request, error, success))
    }
    override fun group(request: String,error: ApiError, success: ApiSuccess<ApiResponse<ArrayList<Group>>>) {
        val call = retrofitService.group()
        call.enqueue(RetrofitCallback<ApiResponse<ArrayList<Group>>>(request, error,success))
    }

    override fun addGroup(request: String, addGroupRequest: AddGroupRequest, error: ApiError, success: ApiSuccess<ApiResponse<ArrayList<Group>>>) {
        val call = retrofitService.addGroup(addGroupRequest)
        call.enqueue(RetrofitCallback<ApiResponse<ArrayList<Group>>>(request, error,success))
    }
    override fun updateGroup(request: String, id:String , addGroupRequest: AddGroupRequest, error: ApiError, success: ApiSuccess<ApiResponse<ArrayList<Group>>>) {
        val call = retrofitService.updateGroup(id,addGroupRequest)
        call.enqueue(RetrofitCallback<ApiResponse<ArrayList<Group>>>(request, error,success))
    }
    override fun deleteGroup(request: String, id:String ,error: ApiError ,success: ApiSuccess<ApiResponse<ArrayList<Group>>>) {
        val call = retrofitService.deleteGroup(id)
        call.enqueue(RetrofitCallback<ApiResponse<ArrayList<Group>>>(request, error,success))
    }
    override fun spend(request: String, group_id:String ,error: ApiError, success: ApiSuccess<ApiResponse<ArrayList<SpendDetail>>>) {
        val call = retrofitService.spend(group_id)
        call.enqueue(RetrofitCallback<ApiResponse<ArrayList<SpendDetail>>>(request, error,success))
    }

    override fun addSpend(request: String, group_id:String, spendDetailRequest: SpendDetailRequest, error: ApiError, success: ApiSuccess<ApiResponse<Any>>) {
        val call = retrofitService.addSpend(group_id,spendDetailRequest)
        call.enqueue(RetrofitCallback<ApiResponse<Any>>(request, error,success))
    }
    override fun updateSpend(request: String, id:String, spendDetailRequest: SpendDetailRequest, error: ApiError, success: ApiSuccess<ApiResponse<SpendDetail>>) {
        val call = retrofitService.updateSpend(id,spendDetailRequest)
        call.enqueue(RetrofitCallback<ApiResponse<SpendDetail>>(request, error,success))
    }
    override fun deleteSpend(request: String,id:String, error: ApiError, success: ApiSuccess<ApiResponse<SpendDetail>>) {
        val call = retrofitService.deleteSpend(id)
        call.enqueue(RetrofitCallback<ApiResponse<SpendDetail>>(request, error,success))
    }

    override fun upLoadImage(request: String, body : MultipartBody.Part,error: ApiError, success: ApiSuccess<ApiResponse<Image>>) {
        val call = retrofitService.upLoadImage(body)
        call.enqueue(RetrofitCallback<ApiResponse<Image>>(request, error,success))
    }

//    override fun upLoadImage(request: String, uploadRequest: UploadRequest, error: ApiError, success: ApiSuccess<ApiResponse<Image>>) {
//        val call = retrofitService.upLoadImage(uploadRequest)
//        call.enqueue(RetrofitCallback<ApiResponse<Image>>(request, error,success))
//    }
    override fun logs(request: String, error: ApiError, success: ApiSuccess<ApiResponse<ArrayList<Logs>>>) {
        val call = retrofitService.logs()
        call.enqueue(RetrofitCallback<ApiResponse<ArrayList<Logs>>>(request, error,success))
    }
}
