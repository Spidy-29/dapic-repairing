package com.dapic.mobile.repairing.data

import com.dapic.mobile.repairing.domain.EmployeeDetails
import com.google.gson.annotations.SerializedName

data class SheetDataResponse(
    @SerializedName("updated") val updated: Boolean,
    @SerializedName("details") val details: List<EmployeeDetails>?,
    @SerializedName("headersDetails") val headersDetails: List<String>?,
    @SerializedName("thirdDetails") val thirdDetails: List<String>?,
    @SerializedName("eightDetails") val eightDetails: List<String>?,
)