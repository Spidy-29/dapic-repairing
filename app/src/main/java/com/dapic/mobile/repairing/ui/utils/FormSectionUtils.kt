package com.dapic.mobile.repairing.ui.utils

import com.dapic.mobile.repairing.FormState

/**
 * Utility functions for determining which form sections should be displayed
 * based on work allocation type and other conditions
 */
object FormSectionUtils {

    /**
     * Determines if the OEM Name section should be shown
     *//*
    fun shouldShowOemName(formState: FormState): Boolean {
        return (formState.workAllocationType == "Site Visit" && formState.workAllocation == "Sales Call") ||
            (formState.workAllocationType.isNotEmpty() &&
             !(formState.workAllocationType == "Office" && formState.workAllocation in listOf("Other Development", "Program Development")) &&
             !(formState.workAllocationType == "Office" && formState.workAllocation == "None") &&
             !(formState.workAllocationType == "WFH" && formState.workAllocation == "None") &&
             !(formState.workAllocationType == "Site Visit" && formState.workAllocation == "Exhibition") &&
             formState.workAllocationType !in listOf("AB", "PH", "Travelling", "Exhibition"))
    }

    */
    /**
     * Determines if the Call Type section should be shown
     *//*
    fun shouldShowCallType(formState: FormState): Boolean {
        return formState.workAllocationType.isNotEmpty() &&
                !(formState.workAllocationType == "Office" && formState.workAllocation in listOf("Other Development", "Program Development")) &&
                !(formState.workAllocationType == "Site Visit" && formState.workAllocation == "Sales Call") &&
                !(formState.workAllocationType == "Office" && formState.workAllocation == "None") &&
                !(formState.workAllocationType == "WFH" && formState.workAllocation == "None") &&
                !(formState.workAllocationType == "Site Visit" && formState.workAllocation == "Exhibition") &&
                formState.workAllocationType !in listOf("AB", "PH", "Travelling", "Exhibition")
    }

    */
    /**
     * Determines if the End User Name section should be shown
     *//*
    fun shouldShowEndUserName(formState: FormState): Boolean {
        return (formState.workAllocationType == "Site Visit" && formState.workAllocation == "Sales Call") ||
            (formState.workAllocationType.isNotEmpty() &&
             !(formState.workAllocationType == "Office" && formState.workAllocation in listOf("Other Development", "Program Development")) &&
             !(formState.workAllocationType == "Office" && formState.workAllocation == "None") &&
             !(formState.workAllocationType == "WFH" && formState.workAllocation == "None") &&
             !(formState.workAllocationType == "Site Visit" && formState.workAllocation == "Exhibition") &&
             formState.workAllocationType !in listOf("AB", "PH", "Travelling", "Exhibition"))
    }

    */
    /**
     * Determines if the Panel Code section should be shown
     *//*
    fun shouldShowPanelCode(formState: FormState): Boolean {
        return formState.workAllocationType.isNotEmpty() &&
            !(formState.workAllocationType == "Site Visit" && formState.workAllocation == "Sales Call") &&
            !(formState.workAllocationType == "Office" && formState.workAllocation in listOf("Other Development", "Program Development")) &&
            !(formState.workAllocationType == "Office" && formState.workAllocation == "None") &&
            !(formState.workAllocationType == "WFH" && formState.workAllocation == "None") &&
            !(formState.workAllocationType == "Site Visit" && formState.workAllocation == "Exhibition") &&
            formState.workAllocationType !in listOf("AB", "PH", "Travelling", "Exhibition")
    }

    */
    /**
     * Determines if the Service Request Number section should be shown
     *//*
    fun shouldShowServiceRequestNumber(formState: FormState): Boolean {
        return formState.workAllocationType.isNotEmpty() &&
            !(formState.workAllocationType == "Site Visit" && formState.workAllocation == "Sales Call") &&
            !(formState.workAllocationType == "Office" && formState.workAllocation in listOf("Other Development", "Program Development")) &&
            !(formState.workAllocationType == "Office" && formState.workAllocation == "None") &&
            !(formState.workAllocationType == "WFH" && formState.workAllocation == "None") &&
            !(formState.workAllocationType == "Site Visit" && formState.workAllocation == "Exhibition") &&
            formState.workAllocationType !in listOf("AB", "PH", "Travelling", "Exhibition")
    }

    */
    /**
     * Determines if the Current Status section should be shown
     *//*
    fun shouldShowCurrentStatus(formState: FormState): Boolean {
        return formState.workAllocationType == "Office" && formState.workAllocation in listOf("Other Development", "Program Development")
    }

    */
    /**
     * Determines if the Work Allocation sub-options should be shown
     *//*
    fun shouldShowWorkAllocationSubOptions(formState: FormState): Boolean {
        return formState.workAllocationType in listOf("Office", "Site Visit", "WFH")
    }

    */
    /**
     * Determines if the "Other" text field should be shown for OEM Name
     *//*
    fun shouldShowOemOtherField(formState: FormState): Boolean {
        return formState.oemName == "Other"
    }*/
    // Shows for "Office" or "Site Visit"
    fun shouldShowCallType(formState: FormState): Boolean {
        return formState.workAllocationType == "Office" || formState.workAllocationType == "Site Visit"
    }

    // Shows for "Office" or "Site Visit"
    fun shouldShowOemName(formState: FormState): Boolean {
        return formState.workAllocationType == "Office" || formState.workAllocationType == "Site Visit"
    }

    // Shows for "Office" or "Site Visit"
    fun shouldShowEndUserName(formState: FormState): Boolean {
        return formState.workAllocationType == "Office" || formState.workAllocationType == "Site Visit"
    }

    // According to your flowchart, this section only appears for "Site Visit"
    fun shouldShowPanelCode(formState: FormState): Boolean {
        return formState.workAllocationType == "Site Visit"
    }

    // Only for "Site Visit"
    fun shouldShowServiceRequestNumber(formState: FormState): Boolean {
        return formState.workAllocationType == "Site Visit"
    }

    // Shows for "Office" or "Site Visit"
    fun shouldShowCurrentStatus(formState: FormState): Boolean {
        return formState.workAllocationType == "Office" || formState.workAllocationType == "Site Visit"
    }

    // TA Bill seems to be part of the flow but wasn't in your UnifiedFormScreen.
    // Let's assume it only shows for "Site Visit" or "Travelling".
    fun shouldShowTaBill(formState: FormState): Boolean {
        return formState.workAllocationType == "Site Visit" || formState.workAllocationType == "Travelling"
    }
}

