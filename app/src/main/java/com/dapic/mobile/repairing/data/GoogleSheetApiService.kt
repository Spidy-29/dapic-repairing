package com.dapic.mobile.repairing.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GoogleSheetApiService {
    @GET("exec") // Assuming the App Script URL ends with "/exec"
    suspend fun getSheetData(
    ): Response<SheetDataResponse>

    @POST("exec") // Assuming the App Script URL ends with /exec
    suspend fun submitFormData(@Body formData: Map<String, String>): Response<SubmitResponse>
}