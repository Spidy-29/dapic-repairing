package com.dapic.mobile.repairing

import java.util.Calendar

data class FormState(
    var employeeName: String = "",
    var date: String = "${Calendar.getInstance().get(Calendar.MONTH) + 1}-${
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    }-${
        Calendar.getInstance().get(Calendar.YEAR)
    }",
    var workAllocationType: String = "",
    var workAllocation: String = "",
    var callType: String = "",
    var oemName: String = "",
    var endUserName: String = "", // Add fields as needed
    var panelCode: String = "", // Add fields as needed
    var soNo: String = "", // Add fields as needed
    var serviceRequestNumber: String = "", // Add fields as needed
    var currentStatus: String = "", // Add fields as needed
    var remark: String = "", // Add fields as needed
    var taBill: String = "", // Add fields as needed

    val startDate: String = "",
    val endDate: String = "",
    val isDateRange: Boolean = false,
    val remarkPerDate: Map<String, String> = emptyMap(), // For storing remarks per date
    val sourceApp:String = "Dapic"
)