package com.dapic.mobile.repairing.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dapic.mobile.repairing.FormViewModel
import com.dapic.mobile.repairing.Utils
import com.dapic.mobile.repairing.ui.component.CallTypeSection
import com.dapic.mobile.repairing.ui.component.CompanyLogo
import com.dapic.mobile.repairing.ui.component.CurrentStatusSection
import com.dapic.mobile.repairing.ui.component.DateSelectionSection
import com.dapic.mobile.repairing.ui.component.EndUserNameSection
import com.dapic.mobile.repairing.ui.component.FormSubmitButton
import com.dapic.mobile.repairing.ui.component.OemNameSection
import com.dapic.mobile.repairing.ui.component.PanelCodeSection
import com.dapic.mobile.repairing.ui.component.RemarkSection
import com.dapic.mobile.repairing.ui.component.ServiceRequestNumberSection
import com.dapic.mobile.repairing.ui.component.WorkAllocationSection
import com.dapic.mobile.repairing.ui.utils.FormSectionUtils

@Composable
fun UnifiedFormScreen(
    viewModel: FormViewModel = hiltViewModel(),
    onSubmit: () -> Unit
) {
    val formState by viewModel.formState.collectAsState()

    var titles by remember { mutableStateOf<List<String>>(emptyList()) }
    LaunchedEffect(key1 = Unit) {
        titles = viewModel.loadHeadersList()
    }

    fun isValidClickable(): Boolean {
        // Basic required fields that are always needed
        val hasDate = formState.date.isNotEmpty() || (formState.isDateRange && formState.startDate.isNotEmpty() && formState.endDate.isNotEmpty())
        val hasWorkAllocationType = formState.workAllocationType.isNotEmpty()
        
        // If basic requirements are not met, return false
        if (!hasDate || !hasWorkAllocationType) {
            return false
        }
        
        // Work allocation sub-options validation
        if (formState.workAllocationType in listOf("Office", "Site Visit", "WFH")) {
            if (formState.workAllocation.isEmpty()) {
                return false
            }
        }
        
        // Conditional field validation based on work allocation type
        when (formState.workAllocationType) {
            "Office" -> {
                // For Office work, check if specific sub-options require additional fields
                when (formState.workAllocation) {
                    "Other Development", "Program Development" -> {
                        // These require current status
                        if (FormSectionUtils.shouldShowCurrentStatus(formState) && formState.currentStatus.isEmpty()) {
                            return false
                        }
                    }
                    "None" -> {
                        // Office with "None" - no additional fields required
                        return true
                    }
                    else -> {
                        // Other office options might need call type, OEM, end user
                        if (FormSectionUtils.shouldShowCallType(formState) && formState.callType.isEmpty()) {
                            return false
                        }
                        if (FormSectionUtils.shouldShowOemName(formState) && formState.oemName.isEmpty()) {
                            return false
                        }
                        if (FormSectionUtils.shouldShowEndUserName(formState) && formState.endUserName.isEmpty()) {
                            return false
                        }
                    }
                }
            }
            
            "Site Visit" -> {
                when (formState.workAllocation) {
                    "Sales Call" -> {
                        // Sales call requires OEM and End User
                        if (formState.oemName.isEmpty()) return false
                        if (formState.endUserName.isEmpty()) return false
                    }
                    "Exhibition" -> {
                        // Exhibition - no additional fields required
                        return true
                    }
                    else -> {
                        // Other site visit options require call type, OEM, end user, panel code, service request
                        if (FormSectionUtils.shouldShowCallType(formState) && formState.callType.isEmpty()) {
                            return false
                        }
                        if (FormSectionUtils.shouldShowOemName(formState) && formState.oemName.isEmpty()) {
                            return false
                        }
                        if (FormSectionUtils.shouldShowEndUserName(formState) && formState.endUserName.isEmpty()) {
                            return false
                        }
                        if (FormSectionUtils.shouldShowPanelCode(formState) && formState.panelCode.isEmpty()) {
                            return false
                        }
                        if (FormSectionUtils.shouldShowServiceRequestNumber(formState) && formState.serviceRequestNumber.isEmpty()) {
                            return false
                        }
                    }
                }
            }
            
            "WFH" -> {
                when (formState.workAllocation) {
                    "None" -> {
                        // WFH with "None" - no additional fields required
                        return true
                    }
                    else -> {
                        // Other WFH options might need call type, OEM, end user
                        if (FormSectionUtils.shouldShowCallType(formState) && formState.callType.isEmpty()) {
                            return false
                        }
                        if (FormSectionUtils.shouldShowOemName(formState) && formState.oemName.isEmpty()) {
                            return false
                        }
                        if (FormSectionUtils.shouldShowEndUserName(formState) && formState.endUserName.isEmpty()) {
                            return false
                        }
                    }
                }
            }
            
            "AB", "PH", "Travelling", "Exhibition" -> {
                // These work allocation types don't require additional fields
                return true
            }
        }
        
        // If we reach here, all required fields are filled
        return true
    }

    // Data for dropdowns and dynamic fields
    val workAllocationCategory =
        listOf("Office", "Site Visit", "WFH", "AB", "PH", "Travelling", "Exhibition")
    val workAllocationValues = when (formState.workAllocationType) {
        "Office" -> Utils.OFFICE_TYPE
        "Site Visit" -> Utils.SITE_VISIT_TYPE
        "WFH" -> Utils.WFH_TYPE
        else -> emptyList()
    }
    val oemDetails = viewModel.loadOEM()
    val currentStatusOptions = viewModel.loadCurrentStatus()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(121, 193, 255)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 24.dp), // Added vertical padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp) // Slightly increased spacing
        ) {
            CompanyLogo() // Assuming you have this composable defined

            // --- Form Sections ---

            DateSelectionSection(formState = formState, viewModel = viewModel)

            WorkAllocationSection(
                formState = formState,
                viewModel = viewModel,
                titles = titles,
                workAllocationCategory = workAllocationCategory,
                values = workAllocationValues
            )

            if (FormSectionUtils.shouldShowCallType(formState)) {
                CallTypeSection(formState = formState, viewModel = viewModel, titles = titles)
            }

            if (FormSectionUtils.shouldShowOemName(formState)) {
                OemNameSection(
                    formState = formState,
                    viewModel = viewModel,
                    titles = titles,
                    oemDetails = oemDetails
                )
            }

            if (FormSectionUtils.shouldShowEndUserName(formState)) {
                EndUserNameSection(formState = formState, viewModel = viewModel, titles = titles)
            }

            if (FormSectionUtils.shouldShowPanelCode(formState)) {
                PanelCodeSection(formState = formState, viewModel = viewModel, titles = titles)
            }

            if (FormSectionUtils.shouldShowServiceRequestNumber(formState)) {
                ServiceRequestNumberSection(
                    formState = formState,
                    viewModel = viewModel,
                    titles = titles
                )
            }

            if (FormSectionUtils.shouldShowCurrentStatus(formState)) {
                CurrentStatusSection(
                    formState = formState,
                    viewModel = viewModel,
                    titles = titles,
                    currentStatus = currentStatusOptions
                )
            }

            RemarkSection(formState = formState, viewModel = viewModel, titles = titles)

            Spacer(modifier = Modifier.height(8.dp))

            FormSubmitButton(
                isClickable = isValidClickable(),
                apiCall = {
                    viewModel.submitForm()
                    true
                },
                onComplete = onSubmit
            )
        }
    }
}