package com.dapic.mobile.repairing

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dapic.mobile.repairing.domain.EmployeeDetails
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    application: Application,
    private val repository: DataRepository
):AndroidViewModel(application){
    private val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(application)
    private val _formState = MutableStateFlow(FormState())
    val formState: StateFlow<FormState> = _formState

    // Update Employee Code
    fun updateEmployeeName(code: String) {
        _formState.value = _formState.value.copy(employeeName = code)
    }

    // Update Date
    fun updateDate(date: String) {
        _formState.value = _formState.value.copy(date = date)
    }

    // Update Work Allocation Type
    fun updateWorkAllocationType(type: String) {
        _formState.value = _formState.value.copy(
            workAllocationType = type,
        )
    }

    fun setDateRange(start: String, end: String) {
        _formState.value = _formState.value.copy(
            isDateRange = true,
            startDate = start,
            endDate = end,
            date = "" // clear single date
        )
    }

    fun setSingleDate(date: String) {
        _formState.value = _formState.value.copy(
            isDateRange = false,
            startDate = "",
            endDate = "",
            date = date
        )
    }

    // Update Work Allocation
    fun updateWorkAllocation(allocation: String) {
        _formState.value = _formState.value.copy(workAllocation = allocation)
    }

    // Update Call Type
    fun updateCallType(callType: String) {
        _formState.value = _formState.value.copy(callType = callType)
    }

    // Update OEM Name
    fun updateOemName(oemName: String) {
        _formState.value = _formState.value.copy(oemName = oemName)
    }

    // Update End User Name
    fun updateEndUserName(endUserName: String) {
        _formState.value = _formState.value.copy(endUserName = endUserName)
    }

    // Update Panel Code
    fun updatePanelCode(panelCode: String) {
        _formState.value = _formState.value.copy(panelCode = panelCode)
    }

    // Update So No
    fun updateSoNo(soNo: String) {
        _formState.value = _formState.value.copy(soNo = soNo)
    }

    // Update Service Request Number
    fun updateServiceRequestNumber(serviceRequestNumber: String) {
        _formState.value = _formState.value.copy(serviceRequestNumber = serviceRequestNumber)
    }

    // Update Current Status
    fun updateCurrentStatus(currentStatus: String) {
        _formState.value = _formState.value.copy(currentStatus = currentStatus)
    }

    // Update Remark
    fun updateRemark(remark: String) {
        _formState.value = _formState.value.copy(remark = remark)
    }

    // Update TA Bill
    fun updateTABill(taBill: String) {
        _formState.value = _formState.value.copy(taBill = taBill)
    }

    // Reset Values
    fun clearValues() {
        _formState.value = FormState()
    }

    fun loadEmployeeDetails(): List<EmployeeDetails> {
        var codes: List<EmployeeDetails> = listOf()
        viewModelScope.launch {
            codes = repository.getEmployeeDetails() // Fetch from local storage or remote API
        }
        return codes
    }

    fun loadHeadersList(): List<String> {
        var headers: List<String> = listOf()
        viewModelScope.launch {
            headers = repository.getHeaders() // Fetch from local storage or remote API
        }
        return headers
    }

    fun loadCallTypes(): List<String> {
        var types: List<String> = listOf()
        viewModelScope.launch {
            types = repository.getCallTypes() // Fetch from local storage or remote API
        }
        return types
    }

    fun loadOEM(): List<String> {
        var names: List<String> = listOf()
        viewModelScope.launch {
            names = repository.getOEMNames() // Fetch from local storage or remote API
        }
        return names
    }

    fun loadCurrentStatus(): List<String> {
        var status: List<String> = listOf()
        viewModelScope.launch {
            status = repository.getCurrentStatus() // Fetch from local storage or remote API
        }
        return status
    }

    suspend fun submitForm(): Boolean {
        val formData = mapOf(
            Utils.EMPLOYEE_NAME to formState.value.employeeName,
            Utils.DATE to formState.value.date,
            Utils.WORK_ALLOCATION to buildString {
                append(formState.value.workAllocationType)
                if (formState.value.workAllocation.isNotEmpty()) {
                    append(": ${formState.value.workAllocation}")
                }
            },
            Utils.TA_BILL to formState.value.taBill,
            Utils.OEM_NAME to (formState.value.oemName),
            Utils.CALL_TYPE to (formState.value.callType),
            Utils.CURRENT_STATUS to (formState.value.currentStatus),
            Utils.END_USER_NAME to (formState.value.endUserName),
            Utils.PANEL_CODE to (formState.value.panelCode),
            Utils.SERVICE_REQUEST_NUMBER to (formState.value.serviceRequestNumber),
            Utils.REMARK_WORK_DESCRIPTION to (formState.value.remark),
            Utils.SO_NO to (formState.value.soNo),
            Utils.SOURCE_APP to formState.value.sourceApp,
        )
        logFormSubmissionEvent(formState.value)
        return repository.submitToGoogleSheets(formData)
    }

    private fun logFormSubmissionEvent(formState: FormState) {
        val bundle = Bundle().apply {
            putString(Utils.EMPLOYEE_NAME, formState.employeeName)
            putString(Utils.DATE, formState.date)
            putString(
                Utils.WORK_ALLOCATION, "${formState.workAllocationType} - ${formState.workAllocation}"
            )
            putString(Utils.CALL_TYPE, formState.callType)
            putString(Utils.OEM_NAME, formState.oemName)
            putString(Utils.END_USER_NAME, formState.endUserName)
            putString(Utils.PANEL_CODE, formState.panelCode)
            putString(Utils.SO_NO, formState.soNo)
            putString(Utils.SERVICE_REQUEST_NUMBER, formState.serviceRequestNumber)
            putString(Utils.CURRENT_STATUS, formState.currentStatus)
            putString(Utils.REMARK_WORK_DESCRIPTION, formState.remark)
            putString(Utils.TA_BILL, formState.taBill)
            putString(Utils.SOURCE_APP, formState.sourceApp)
        }

        firebaseAnalytics.logEvent(Utils.FORM_SUBMISSION, bundle)
    }
}