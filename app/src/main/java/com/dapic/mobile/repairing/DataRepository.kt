package com.dapic.mobile.repairing

import android.content.Context
import com.dapic.mobile.repairing.domain.EmployeeDetails
import com.dapic.mobile.repairing.ui.OEM_NAME_SCREEN
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

open class DataRepository @Inject constructor(context: Context) {

    private val apiService = RetrofitClient.apiService

    private val sharedPreferences =
        context.getSharedPreferences("DapicElectricsPrefs", Context.MODE_PRIVATE)

    open suspend fun fetchSheetData() {
        try {
            val response = apiService.getSheetData()
            if (response.isSuccessful && response.body() != null) {
                val responseBody = response.body()!!
                if (responseBody.updated) {
                    withContext(Dispatchers.IO) {
                        saveHeaderListLocally(responseBody.headersDetails ?: emptyList())
                        saveEmployeeDetailsLocally(responseBody.details ?: emptyList())
//                        saveCallTypesLocally(responseBody.callType ?: emptyList())
                        saveOEMNamesLocally(responseBody.thirdDetails ?: emptyList())
                        saveCurrentStatusLocally(responseBody.eightDetails ?: emptyList())
                    }
                }
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e // Rethrow the exception to handle in ViewModel
        }
    }

    // Save updated data locally
    fun getEmployeeDetails(): List<EmployeeDetails> {
        // Fetch from local storage or API
        val data = sharedPreferences.getString(Utils.EMPLOYEE_DETAILS,null) ?: return emptyList()
        val type = object : TypeToken<List<EmployeeDetails>>() {}.type
        return Gson().fromJson(data, type)
    }

    private fun saveEmployeeDetailsLocally(details: List<EmployeeDetails>) {
        sharedPreferences.edit().apply {
            putString(Utils.EMPLOYEE_DETAILS, Gson().toJson(details))
            apply()
        }
    }

    fun getHeaders(): List<String> {
        // Fetch from local storage or API
        val data = sharedPreferences.getString(Utils.HEADERS, null) ?: return emptyList()
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(data, type)
    }

    private fun saveHeaderListLocally(headers: List<String>) {
        sharedPreferences.edit().apply {
            putString(Utils.HEADERS, Gson().toJson(headers))
            apply()
        }
    }


    fun getOEMNames(): List<String> {
        // Fetch from local storage or API
        return sharedPreferences.getStringSet(OEM_NAME_SCREEN, emptySet())?.toList() ?: emptyList()
    }

    private fun saveOEMNamesLocally(oem: List<String>) {
        sharedPreferences.edit().apply {
            putStringSet(OEM_NAME_SCREEN, oem.toSet())
            apply()
        }
    }

    fun getCallTypes(): List<String> {
        // Fetch from local storage or API
        return sharedPreferences.getStringSet(Utils.CALL_TYPE, emptySet())?.toList() ?: emptyList()
    }

    private fun saveCallTypesLocally(callTypes: List<String>) {
        sharedPreferences.edit().apply {
            putStringSet(Utils.CALL_TYPE, callTypes.toSet())
            apply()
        }
    }

    fun getCurrentStatus(): List<String> {
        // Fetch from local storage or API
        return sharedPreferences.getStringSet(Utils.CURRENT_STATUS, emptySet())?.toList() ?: emptyList()
    }

    private fun saveCurrentStatusLocally(currentStatus: List<String>) {
        sharedPreferences.edit().apply {
            putStringSet(Utils.CURRENT_STATUS, currentStatus.toSet())
            apply()
        }
    }

    suspend fun submitToGoogleSheets(formData: Map<String, String>): Boolean {
        return try {
            val response = apiService.submitFormData(formData)
            if (response.isSuccessful) {
                response.body()?.status == "success"
            } else {
                false // Handle failure scenario
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}