package com.dapic.mobile.repairing

object Utils {
    const val HEADERS = "headers"
    const val EMPLOYEE_DETAILS = "employeeDetails"
    const val EMPLOYEE_NAME = "employeeName"
    const val DATE = "date"
    const val WORK_ALLOCATION = "fiveColumn"
    const val CALL_TYPE = "sixColumn"
    const val OEM_NAME = "sevenColumn"
    const val END_USER_NAME = "eightColumn"
    const val PANEL_CODE = "nineColumn"
    const val SERVICE_REQUEST_NUMBER = "tenColumn"
    const val CURRENT_STATUS = "elevenColumn"
    const val TA_BILL = "twelveColumn"
    const val REMARK_WORK_DESCRIPTION = "thirteenColumn"
    const val SO_NO = "fourteenColumn"
    const val SOURCE_APP = "sourceApp"
    const val FORM_SUBMISSION = "form_submission"

    val OFFICE_TYPE = listOf(
        "None",
        "Program Development",
        "Panel Testing",
        "Online Support",
        "Other Development",
    )

    val SITE_VISIT_TYPE = listOf(
        "Service Call",
        "OEM Installation Call",
        "EndUser Installation Call",
        "Sales Call",
        "Panel Testing",
    )

    val WFH_TYPE = listOf(
        "None",
        "Program Development",
        "Online Support",
    )

    /*val CALL_TYPE_OPTION = listOf(
        "FOC",
        "Chargeble",
    )*/

    /*val CURRENT_STATUS_OPTION = listOf(
        "In Progress",
        "Idle",
        "Hold",
        "Complete",
        "Other",
    )*/

//    enum class WORK_ALLOCATION_CATEGORY(value: String) {
//        WFH("WFH"),
//        SITE_VISIT("Site Visit"),
//        OFFICE("Office");
//    }
}